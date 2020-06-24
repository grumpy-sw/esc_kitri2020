package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import coach.CoachLoginController;
import hr.HRLoginController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import manager.ManagerLoginController;

public class FirstController implements Initializable {
	@FXML
	private TextField id;
	@FXML
	private PasswordField pwd;
	@FXML
	private Button membersBtn;
	@FXML
	private Button loginBtn;
	@FXML
	private Button exit;
	@FXML
	private Text user_id;
	
	/*
	private String loginId;
	private FirstController(String loginId) {
		// TODO Auto-generated constructor stub
		this.loginId = loginId;
	}
	*/

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		membersBtn.setOnAction(e -> membersAction(e));
		loginBtn.setOnAction(e -> loginAction(e));
	}

	public void membersAction(ActionEvent event) {
		try {
			Parent members = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
			StackPane root = (StackPane) membersBtn.getScene().getRoot();
			root.getChildren().add(members);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleBtnAction(ActionEvent e) {
		Platform.exit();
	}

	public void loginAction(ActionEvent event) {
		String uId = id.getText();
		String uPwd = pwd.getText();
		// MySQL Á¢¼Ó °æ·Î, ±âº» Æ÷Æ®´Â 3306, DB¸í
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		// MySQL °èÁ¤
		String dbId = "c##ora_esc";
		// MySQL °èÁ¤ ºñ¹Ð¹øÈ£
		String dbPw = "1234";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// µðºñ ¿¬°á
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			System.out.println("FirstControll ¿À¶óÅ¬ ·Î±×ÀÎ ¼º°ø");
			
			sql = "select P_ID, P_PW, P_DEPT from \"C##ORA_ESC\".\"ESC_MEMBERS\" where P_ID = ? and P_PW = ?";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, uId);
			pstmt.setString(2, uPwd);
			System.out.println("°èÁ¤ÀÔ·Â¿Ï·á.");
			rs = pstmt.executeQuery();			
			System.out.println("ÀÔ·Â¿Ï·á");

			if (rs.next()) {
				System.out.println("ºñ±³Áß");
				

				if (rs.getString("P_ID").equals(uId) && rs.getString("P_PW").equals(uPwd) && rs.getString("P_DEPT").equals("Coach")){
					System.out.println("ÄÚÄ¡ ·Î±×ÀÎ ¼º°ø");
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/coach/CoachMain.fxml"));	//Å¬ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¼Òµï¿½È­. ï¿½ï¿½ï¿½Ò½ï¿½ ï¿½Ú¿ï¿½ ï¿½ï¿½È¯.
					Parent mainPage = loader.load();
					CoachLoginController controller = loader.<CoachLoginController>getController();
					controller.setloginId(uId);
					Scene sc = new Scene(mainPage);
					
					Stage stage = (Stage)loginBtn.getScene().getWindow();
					stage.close();
					stage.setScene(sc);
					stage.show();
					
				} 
				else if (rs.getString("P_ID").equals(uId) && rs.getString("P_PW").equals(uPwd) && rs.getString("P_DEPT").equals("Manager")){
					System.out.println("¸Å´ÏÀú ·Î±×ÀÎ ¼º°ø");
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/ManagerMain.fxml"));	//Å¬ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¼Òµï¿½È­. ï¿½ï¿½ï¿½Ò½ï¿½ ï¿½Ú¿ï¿½ ï¿½ï¿½È¯.
					Parent mainPage = loader.load();
					ManagerLoginController controller = loader.<ManagerLoginController>getController();
					controller.setloginId(uId);
					Scene sc = new Scene(mainPage);
					
					Stage stage = (Stage)loginBtn.getScene().getWindow();
					stage.close();
					stage.setScene(sc);
					System.out.println("¾¤");
					stage.show();
					
				}
				else if (rs.getString("P_ID").equals(uId) && rs.getString("P_PW").equals(uPwd) && rs.getString("P_DEPT").equals("HR")){
					
					System.out.println("ÀÎ»ç°ú ·Î±×ÀÎ ¼º°ø");
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/hrLogin.fxml"));	//Å¬ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Þ¼Òµï¿½È­. ï¿½ï¿½ï¿½Ò½ï¿½ ï¿½Ú¿ï¿½ ï¿½ï¿½È¯.
					Parent mainPage = loader.load();
					HRLoginController controller = loader.<HRLoginController>getController();
					controller.setloginId(uId);
					Scene sc = new Scene(mainPage);
					
					Stage stage = (Stage)loginBtn.getScene().getWindow();
					stage.close();
					stage.setScene(sc);
					stage.show();
					
					
				}
				else if (rs.getString("P_ID").equals(uId) && rs.getString("P_PW").equals(uPwd)){
					System.out.println("Á÷¾÷¼±ÅÃ ¾ÈÇÑ °èÁ¤ ·Î±×ÀÎ ¼º°ø");
					Parent mainPage = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
					//ÄÁÆ®·Ñ·¯ »ý¼º. ID Àü´Þ.
					StackPane root = (StackPane) loginBtn.getScene().getRoot();
					root.getChildren().add(mainPage);
				}
				
			}
			else {
				waringgender();
				//alert ¾ÆÀÌµð³ª ºñ¹Ð¹øÈ£ È®ÀÎ
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

	}

	public void waringgender() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("°æ°í");
		alert.setHeaderText("¾ÆÀÌµð¿Í ºñ¹Ð¹øÈ£¸¦ È®ÀÎÇØÁÖ¼¼¿ä.");
		alert.show();
	}
	
}
