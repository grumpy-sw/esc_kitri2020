package hr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HRRegister implements Initializable {
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String ID = "c##ora_esc";
	String PW = "1234";
	String query = null;



	Connection conn = null;
	Statement stat = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	@FXML
	private Text loginid;
	@FXML
	private Button register;
	@FXML
	private Button modifydelete;
	@FXML
	private Button logout;
	@FXML
	private Button okbtn;
	@FXML
	private BorderPane bp;
	@FXML
	private AnchorPane ap;
	@FXML
	private Button save;
	@FXML
	private TextField p_no;
	@FXML
	private TextField p_name;
	@FXML
	private TextField p_gender;
	@FXML
	private TextField p_birth;
	@FXML
	private TextField p_hired;
	@FXML
	private TextField p_sal;
	@FXML
	private TextField p_nat;
	@FXML
	private TextField p_health;
	@FXML
	private TextField p_photo;
	@FXML
	private TextField p_tid;
	@FXML
	private TextField p_nickname;
	@FXML
	private TextField p_position;	
	
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		
	}
	
	public void save() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			query = "INSERT INTO \"C##ORA_ESC\".\"PLAYER\" (P_NO, P_NAME, P_GENDER, P_BIRTH, P_HIRED, P_SAL, P_NAT, P_HEALTH, P_PHOTO, P_TID, P_NICKNAME, P_POSITION) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, p_no.getText());
			pstmt.setString(2, p_name.getText());
			pstmt.setString(3, p_gender.getText());
			pstmt.setString(4, p_birth.getText());
			pstmt.setString(5, p_hired.getText());
			pstmt.setString(6, p_sal.getText());
			pstmt.setString(7, p_nat.getText());
			pstmt.setString(8, p_health.getText());
			pstmt.setString(9, p_photo.getText());
			pstmt.setString(10, p_tid.getText());
			pstmt.setString(11, p_nickname.getText());
			pstmt.setString(12, p_position.getText());
			pstmt.executeUpdate();
			pstmt.close();

			query = "INSERT INTO \"C##ORA_ESC\".\"STAT\" (P_NO) VALUES (?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, p_no.getText());
			pstmt.executeUpdate();
			pstmt.close();
			
			query = "INSERT INTO \"C##ORA_ESC\".\"TRAINING\" (P_NO) VALUES (?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, p_no.getText());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			loadPage("HRRegister");
			} catch (Exception e) {
				if(e.getMessage().contains("C_P_POSITION")) {
					System.out.println("position error");
					waringposition();
				}
				else if (e.getMessage().contains("ORA-02290: Ã¼Å© Á¦¾àÁ¶°Ç(C##ORA_ESC.C_P_GENDER)ÀÌ À§¹èµÇ¾ú½À´Ï´Ù")){
					System.out.println("gender error");
					waringgender();
				}
				else {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			} 

	}
	public void waringgender() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("°æ°í");
		alert.setHeaderText("¼ºº° M/F·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		alert.show();
	}
	public void waringposition() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("°æ°í");
		alert.setHeaderText("Æ÷Áö¼Ç TOP/JUG/MID/BOT/SUP·Î ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		alert.show();
	}
	public void saveAction (MouseEvent e) {
		save();
		loadPage("HRRegister");
	}
	public void register (MouseEvent e) {
		loadPage("HRRegister");
	}
	
	public void modifydelete(MouseEvent e) {
		loadPage("HRModifyDelete");
	}
	
	public void loadPage(String page) {
		try {
			Parent newPage = FXMLLoader.load(getClass().getResource(page + ".fxml"));
			bp.setCenter(newPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logoutAction(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/firstPage.fxml"));	//Å¬ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¼Òµï¿½È­. ï¿½ï¿½ï¿½Ò½ï¿½ ï¿½Ú¿ï¿½ ï¿½ï¿½È¯.
		
		Parent mainPage;
		try {
			mainPage = loader.load();
			Scene sc = new Scene(mainPage);
			
			Stage stage = (Stage)logout.getScene().getWindow();
			stage.close();
			stage.setScene(sc);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
