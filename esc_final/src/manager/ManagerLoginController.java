package manager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sql.dBConn;

public class ManagerLoginController implements Initializable {
	@FXML
	private Text loginid;
	
	@FXML
	private Button playersBtn;
	
	@FXML
	private Button trainingBtn;
	
	@FXML
	private Button logoutBtn;
	
	@FXML
	private BorderPane bp;
	
	@FXML
	private AnchorPane ap;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void p_comboAction() {
		//선택시 실행되는 구문.
	}
	
	public void playersAction(MouseEvent e) {
		loadPage("ManagerPlayers");	//ManagerPlayers.fxml 불러오기
	}
	
	public void trainingAction(MouseEvent e) {
		loadPage("ManagerTraining"); //ManagerTraining.fxml 불러오기
	}
	
	public void logoutAction(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/firstPage.fxml"));	//회원 로그인 페이지로 돌아가기
		
		Parent mainPage;
		try {
			mainPage = loader.load();
			Scene sc = new Scene(mainPage);
			
			Stage stage = (Stage)logoutBtn.getScene().getWindow();
			stage.close();
			stage.setScene(sc);
			stage.show();
			//페이지를 불러와서 scene에 담고 stage에 scene을 띄워주는 과정
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public void loadPage(String page) {
		try {
			Parent newPage = FXMLLoader.load(getClass().getResource(page + ".fxml"));
			bp.setCenter(newPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setloginId(String loginId) {
		loginid.setText(loginId + "(감독) 반갑습니다!");
		//coachloginid.setText(loginId);
	}
}
