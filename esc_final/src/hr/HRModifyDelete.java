package hr;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HRModifyDelete implements Initializable {
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String ID = "c##ora_esc";
	String PW = "1234";
	String query = null;
	String number ="";
	
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
	private BorderPane bp;
	@FXML
	private AnchorPane ap;
	@FXML
	public ComboBox<String> combo;
	@FXML
	private Button modify;
	@FXML
	private Button delete;
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			stat = conn.createStatement();
			rs = stat.executeQuery("SELECT P_NAME FROM \"C##ORA_ESC\".\"PLAYER\"");
			ObservableList<String> playerlist = FXCollections.observableArrayList();
			while(rs.next()) {
				playerlist.add(new String(rs.getString(1)));
			}
			combo.setItems(playerlist);
			rs.close();
			comboAction();
			stat.close();
			conn.close();
			} catch (Exception e) {
				e.printStackTrace();
		}
	
		
	}
	

	public void comboAction() {
		combo.setOnAction(event ->{
			p_name.setText(combo.getValue());
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, ID, PW);
				stat = conn.createStatement();
				
				query = "SELECT P_NO, P_GENDER, P_BIRTH, P_HIRED, P_SAL, P_NAT, P_HEALTH, P_PHOTO, P_TID, P_NICKNAME, P_POSITION FROM \"C##ORA_ESC\".\"PLAYER\" WHERE P_NAME = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, combo.getValue());
				rs = pstmt.executeQuery();
				while(rs.next()) {
					number = rs.getString(1);
					p_no.setText(rs.getString(1));
					p_gender.setText(rs.getString(2));
					p_birth.setText(rs.getString(3));
					p_hired.setText(rs.getString(4));
					p_sal.setText(rs.getString(5));
					p_nat.setText(rs.getString(6));
					p_health.setText(rs.getString(7));
					p_photo.setText(rs.getString(8));
					p_tid.setText(rs.getString(9));
					p_nickname.setText(rs.getString(10));
					p_position.setText(rs.getString(11));
				}
				
			} catch(Exception e) {
				
			}
		});
	}
	
	public void modifyplayer() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, ID, PW);
				query = "UPDATE \"C##ORA_ESC\".\"PLAYER\" SET P_NO = ?, P_NAME = ?, P_GENDER = ?, P_BIRTH = ?, P_HIRED = ?, P_SAL = ?, P_NAT = ?, P_HEALTH = ?, P_PHOTO = ?, P_TID = ?, P_NICKNAME = ?, P_POSITION = ? WHERE P_NAME = ?";
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
				pstmt.setString(13, combo.getValue());
				pstmt.executeUpdate();
				pstmt.close();
				
				loadPage("HRModifyDelete");
				
			} catch(Exception e){
				if(e.getMessage().contains("C_P_POSITION")) {
					System.out.println("position error");
					waringposition();
				}
				else if (e.getMessage().contains("C_P_GENDER")){
					System.out.println("gender error");
					waringgender();
				}
				else {
					System.out.println(e.getMessage());
				}
			}
	}
	
	public void deleteplayer() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, ID, PW);
				query = "DELETE FROM \"C##ORA_ESC\".\"PLAYER\" WHERE P_NO = ? ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, number);
				pstmt.executeUpdate();
				pstmt.close();
				
			} catch(Exception e) {
		}
	}
	
	public void modifyAction (MouseEvent e) {
		modifyplayer();
		loadPage("HRModifyDelete");
	}
	public void deleteAction (MouseEvent e) {
		deleteplayer();
		loadPage("HRModifyDelete");
	}
	public void register (MouseEvent e) {
		loadPage("HRRegister");
	}
	
	public void modifydelete(MouseEvent e) {
		loadPage("HRModifyDelete");
	}
	public void waringgender() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("���");
		alert.setHeaderText("���� M/F�� �Է����ּ���.");
		alert.show();
	}
	public void waringposition() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("���");
		alert.setHeaderText("������ TOP/JUG/MID/BOT/SUP�� �Է����ּ���.");
		alert.show();
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/firstPage.fxml"));	//Ŭ������ �޼ҵ�ȭ. ���ҽ� �ڿ� ��ȯ.
		
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
