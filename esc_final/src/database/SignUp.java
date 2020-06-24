package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.SignUpController;

public class SignUp {
	SignUpController mc = new SignUpController();
	
 public static void menu() {  
	 // MySQL ���� ���, �⺻ ��Ʈ�� 3306, DB�� 
	  String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	  // MySQL ����
	  String dbId = "c##ora_esc";
	  // MySQL ���� ��й�ȣ
	  String dbPw = "1234";	  
	  Connection conn = null;
	  PreparedStatement pstmt = null;
	  
	  String sql = "";
	  int num = 0;
	  new SignUpController();
	  String name = null;
	  String id = null;
	  String pwd = null;
	  
	  try {
	   Class.forName("oracle.jdbc.driver.OracleDriver");
	   // ��� ����
	   System.out.println("��񿬵� ����");
	   conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);	     
	     sql = "insert into esc_members values(?,?,?,?)";
	     pstmt = conn.prepareStatement(sql);
	     pstmt.setInt(1, num);
	     pstmt.setString(2, name);
	     pstmt.setString(3, id);
	     pstmt.setString(4, pwd);
	     pstmt.executeUpdate();	     
	  }
 catch (Exception e) {
	 System.out.println("����Ŭ ��������");
	   e.printStackTrace();
	  } finally{
	   if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
	   if(conn!=null) try{conn.close();}catch(SQLException ex){}
	  }
 }
}