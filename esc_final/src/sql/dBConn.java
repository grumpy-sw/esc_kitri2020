package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dBConn {
	private static Connection dbConn;
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		if (dbConn == null) {

			String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
			String ID = "c##ora_esc";
			String PW = "1234";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			dbConn = DriverManager.getConnection(URL,ID,PW);
		}
		return dbConn;
	}
	
	public static void close() throws SQLException{
		if(dbConn != null) {
			if(!dbConn.isClosed()) {
				dbConn.close();
			}
		}
	}
}

/*
import java.sql.Connection;

public class jdbcTest{
	public static void main(String[] args) {
		try {
			Connection conn = DBConn.getConnection();
			System.out.println("오라클 연결 완료");
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}

*/