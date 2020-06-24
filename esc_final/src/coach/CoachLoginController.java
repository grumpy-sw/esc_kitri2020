package coach;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
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

public class CoachLoginController implements Initializable {
	String login_id = null;

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

	public void playersAction(MouseEvent e) {
		System.out.println("플레이어 클릭");
		loadPage("CoachPlayers");
	}

	public void trainingAction(MouseEvent e) {
		System.out.println("훈련 클릭");
		loadPage("CoachTraining");
	}

	public void logoutAction(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/firstPage.fxml"));

		Parent mainPage;
		try {
			mainPage = loader.load();
			Scene sc = new Scene(mainPage);

			Stage stage = (Stage) logoutBtn.getScene().getWindow();
			stage.close();
			stage.setScene(sc);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void loadPage(String page) {
		System.out.println("로드페이지");
		if (page.contentEquals("CoachPlayers")) {
			try {
				System.out.println("플레이어 메소드 접속");
				FXMLLoader newPage = new FXMLLoader(getClass().getResource("CoachPlayers.fxml"));
				System.out.println("p1");
				Parent mainPage2 = newPage.load();	
//				Parent newPage = FXMLLoader.load(getClass().getResource("CoachPlayersController.fxml"));
				System.out.println("p2");
				CoachPlayersController controller = newPage.<CoachPlayersController>getController();
				System.out.println("p3");
				controller.setloginId(login_id);
				System.out.println("p4");
				bp.setCenter(mainPage2);
				System.out.println("p5");
			} catch (IOException e) {
				System.out.println("예외");
				e.printStackTrace();
			}
		} else if (page.contentEquals("CoachTraining")) {
			try {
				System.out.println("훈련 메소드 접속");
				FXMLLoader newPage = new FXMLLoader(getClass().getResource("CoachTraining.fxml"));
				System.out.println("p1");
				Parent mainPage = newPage.load();
				System.out.println("p2");
				CoachTrainingController controller = newPage.<CoachTrainingController>getController();
				System.out.println("p3");
				controller.setloginId(login_id);
				System.out.println("p4");
				bp.setCenter(mainPage);
				System.out.println("p5");
			} catch (IOException e) {
				System.out.println("예외");
				e.printStackTrace();
			}
		}
	}

	public void setloginId(String loginId) {
		this.login_id = loginId;
		loginid.setText(loginId + "(코치) 반갑습니다!");
		// coachloginid.setText(loginId);
	}
}
