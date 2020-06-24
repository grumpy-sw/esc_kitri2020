package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class SignUpController implements Initializable {
	@FXML
	private AnchorPane login;
	@FXML
	private TextField name;
	@FXML
	private TextField id;
	@FXML
	private PasswordField pwd;
	@FXML
	private Button membersBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	public ComboBox<String> comboBox;

	ObservableList<String> list = FXCollections.observableArrayList("HR", "Coach", "Manager");
	String dept = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancelBtn.setOnAction(e -> cancelAction(e));
		membersBtn.setOnAction(e -> membersAction(e));
		comboBox.setItems(list);
	}

	public void comboChanged(ActionEvent event) {
		dept = (comboBox.getValue());
	}

	public void cancelAction(ActionEvent e) {
		StackPane root = (StackPane) cancelBtn.getScene().getRoot();
		root.getChildren().remove(login);
	}

	public void membersAction(ActionEvent event) {
		String uName = name.getText();
		String uId = id.getText();
		String uPwd = pwd.getText();
		// MySQL 접속 경로, 기본 포트는 3306, DB명
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		// MySQL 계정
		String dbId = "c##ora_esc";
		// MySQL 계정 비밀번호
		String dbPw = "1234";
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "";
		new SignUpController();
		String name = uName;
		String id = uId;
		String pwd = uPwd;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 디비 연결
			System.out.println("dB연결 성공");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			System.out.println("dB로그인 성공");

			sql = "insert into \"C##ORA_ESC\".\"ESC_MEMBERS\" values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dept);
			System.out.println("dept입력");
			pstmt.setString(2, name);
			System.out.println("name 입력");
			pstmt.setString(3, id);
			System.out.println("id입력");
			pstmt.setString(4, pwd);
			System.out.println("pw입력");
			pstmt.executeUpdate();
			System.out.println("업데이트 성공.");
			pstmt.close();
			
			if (dept.contentEquals("Coach")) {
				sql = "INSERT INTO \"C##ORA_ESC\".\"COACH\" (C_ID, C_NAME) VALUES (?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				System.out.println("코치테이블 id입력");
				pstmt.setString(2, name);
				System.out.println("코치테이블 이름 입력");
				pstmt.executeUpdate();
				System.out.println("코치테이블 업뎃 성공");
				pstmt.close();
				
			} else if (dept.contentEquals("Manager")) {
				sql = "INSERT INTO \"C##ORA_ESC\".\"MANAGER\" (M_ID, M_NAME) VALUES (?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				System.out.println("매니저테이블 id입력");
				pstmt.setString(2, name);
				System.out.println("매니저테이블 이름 입력");
				pstmt.executeUpdate();
				System.out.println("매니저테이블 업뎃 성공");
				pstmt.close();
			}
		}

		catch (Exception e) {
			waringgender();
			System.out.println("회원가입 예외발생.");
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
		StackPane root = (StackPane) cancelBtn.getScene().getRoot();
		root.getChildren().remove(login);
	}
	
	public void waringgender() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("경고");
		alert.setHeaderText("회원가입시 입력정보를 모두 채워주세요.");
		alert.show();
	}
}
