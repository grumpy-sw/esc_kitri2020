package hr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HRLoginController implements Initializable {
	@FXML
	private Text loginid;
	
	@FXML
	private Button register;
	
	@FXML
	private Button modifydelete;
	
	@FXML
	private Button delete;
	
	@FXML
	private Button logout;
	
	@FXML
	private BorderPane bp;
	
	@FXML
	private AnchorPane ap;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void register (MouseEvent e) {
		loadPage("HRRegister");
	}
	
	public void modifydelete(MouseEvent e) {
		loadPage("HRModifyDelete");
	}
	
	public void membersdelete(MouseEvent e) {
		loadPage("HRAllEscMembers");
	}
	
	public void logoutAction(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/firstPage.fxml"));	//�겢�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�뙣�냼�벝�삕�솕. �뜝�룞�삕�뜝���룞�삕 �뜝�뙓�슱�삕 �뜝�룞�삕�솚.
		
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
	
	public void loadPage(String page) {
		try {
			Parent newPage = FXMLLoader.load(getClass().getResource(page + ".fxml"));
			bp.setCenter(newPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setloginId(String loginId) {
		loginid.setText(loginId +"(인사과) 님 안녕하세요!");
		//coachloginid.setText(loginId);
	}
}
