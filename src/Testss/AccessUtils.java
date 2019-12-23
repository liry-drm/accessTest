package Testss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessUtils {
	private static Connection conn = null;
	private static PreparedStatement stat = null;
	private static ResultSet rs = null;

	// �������ݿ⣨���access���ݿ������jar����Access_JDBC30.jar��
	
	/**
	 * �������ݿ����� 
	 * @param dbPath ���ݿ��ļ�����·��
	 */
	public static void openConn(String dbPath) {
		try {
			String url = "jdbc:access:///"+dbPath;
			Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر����ݿ�����
	 */
	public static void closeConn() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * ��ѯ���� ��װlist
	 * @param dbPath
	 * @param sql	
	 * @param fields �ֶ�������
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> qryData(String dbPath,String sql,String[] fields) throws SQLException {
		openConn(dbPath);
		stat = conn.prepareStatement(sql);
		rs = stat.executeQuery();
		List<Map<String,Object>> list = new ArrayList<>();
		while (rs.next()) {
			Map<String,Object> map = new HashMap<String, Object>();
			for (String fild : fields) {
				map.put(fild, rs.getString(fild));
			}
			list.add(map);
		}
		closeConn();
		return list;
	}
	
	public static void main(String argv[]) {
		String sql="select * from User ";
		String dbPath="..\\test.accdb";
		String[] fields= {"sUser"};
		try {
			List<Map<String,Object>> list = qryData(dbPath,sql,fields);
			for (Map<String, Object> map : list) {
				for (String str : fields) {
					System.out.println(map.get(str));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
