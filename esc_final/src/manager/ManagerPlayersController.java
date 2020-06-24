package manager;

import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sql.dBConn;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class ManagerPlayersController implements Initializable {
	
	@FXML
	private Button playerInquiry;
	
	@FXML
	private TableView<LatestMatchTableRow> LMTable;
	@FXML
	private TableView<PickTableRow> pickTable;
	
	@FXML
	private TableColumn<LatestMatchTableRow, String> LMT_dateColumn;
	@FXML
	private TableColumn<LatestMatchTableRow, String> LMT_leagueColumn;
	@FXML
	private TableColumn<LatestMatchTableRow, String> LMT_champColumn;
	@FXML
	private TableColumn<LatestMatchTableRow, String> LMT_winloseColumn;
	@FXML
	private TableColumn<LatestMatchTableRow, String> LMT_kdaColumn;
	@FXML
	private TableColumn<LatestMatchTableRow, String> LMT_kRateColumn;
	
	@FXML
	private TableColumn<PickTableRow, String> PT_champion;
	@FXML
	private TableColumn<PickTableRow, String> PT_games;
	@FXML
	private TableColumn<PickTableRow, String> PT_win;
	@FXML
	private TableColumn<PickTableRow, String> PT_lose;
	@FXML
	private TableColumn<PickTableRow, String> PT_winRate;
	
	@FXML
	private Text lastChampion;
	
	@FXML
	private Text Opponent_C;
	
	@FXML
	private Text Opponent_ID;
	
	@FXML
	private Text lastStats;
	
	@FXML
	private Text vsorwith;
	
	private WebDriver driver;
	private WebElement webElement;
	
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:/kitri/selenium/chromedriver_win32/chromedriver.exe";
	
	//여기서부터 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~합친부분.
	
	String pick_pno =null;
	String pick_pnick = null;
	String pposition = null;
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String ID = "c##ora_esc";
	String PW = "1234";
	String query = null;

	Connection conn = null;
	Statement stat = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	@FXML
	public Text name;
	@FXML
	public Text birth;
	@FXML
	public Text gender;
	@FXML
	public Text nat;
	@FXML
	public Text pno;
	@FXML
	public Text pid;
	@FXML
	public Text when;
	@FXML
	public Text health;
	@FXML
	public Text sal;
	@FXML
	public Text line;
	@FXML
	public Text style;
	@FXML
	public Image img;
	@FXML
	public ImageView imgview;

	@FXML
	public TextField textF;

	@FXML
	ComboBox<String> p_namecombobox;

	ObservableList<String> playerlist = null;
	

	public void settext(String Name) {
		textF.setText(Name);
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		// TODO Auto-generated method stub

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			stat = conn.createStatement();
			rs = stat.executeQuery("SELECT P_NAME FROM \"C##ORA_ESC\".\"PLAYER\"");
			System.out.println("1");
			ObservableList<String> playerlist = FXCollections.observableArrayList();
			System.out.println("2");
			while (rs.next()) {
				playerlist.add(new String(rs.getString(1)));
				System.out.println("선수 목록 로딩");
			}
			p_namecombobox.setItems(playerlist);
			System.out.println("로딩 완료");
			rs.close();
			p_comboAction();

			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public void p_comboAction() {
		// 선택시 실행되는 구문.
		
		p_namecombobox.setOnAction(event -> {
			name.setText(p_namecombobox.getValue());
			try {
				deleteAction();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, ID, PW);
				stat = conn.createStatement();

				String code = "";
				String pgender = "";
				String pbirth = "";
				String phired = "";
				String psal = "";
				String pnat = "";
				String phealth = "";
				String pphoto = "";
				String pnickname = "";
				String pstyle = "";

				query = "SELECT P_NO, P_GENDER, P_BIRTH, P_HIRED, P_SAL, P_NAT, P_HEALTH, P_PHOTO, P_NICKNAME, P_POSITION, P_STYLE FROM \"C##ORA_ESC\".\"PLAYER\" WHERE P_NAME = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, p_namecombobox.getValue());
				rs = pstmt.executeQuery();
				while(rs.next()) {
					code = rs.getString(1);
					pgender = rs.getString(2);
					pbirth = rs.getString(3);
					phired = rs.getString(4);
					psal = rs.getString(5);
					pnat = rs.getString(6);
					phealth = rs.getString(7);
					pphoto = rs.getString(8);
					pnickname = rs.getString(9);
					pposition = rs.getString(10);
					pstyle = rs.getString(11);
				}
				rs.close();

				pno.setText(code);
				gender.setText(pgender);
				birth.setText(pbirth);
				when.setText(phired);
				sal.setText(psal);
				nat.setText(pnat);
				health.setText(phealth);
				Image image = null;
				image = new Image(pphoto);
				imgview.setImage(image);
				pid.setText(pnickname);
				line.setText(pposition);
				style.setText(pstyle);

				pick_pno = code;
				pick_pnick = pnickname;
				inquiryAction();
				
			} catch (Exception e) {

			}
		});

	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~여기까지 합친부분.
	
	public void deleteAction() {
		LMTable.getItems().removeAll(this.LMTable.getItems());
		pickTable.getItems().removeAll(this.pickTable.getItems());
	}
	
	public void inquiryAction() {
		//database에서 값 불러와서 채우기
		
		//code도 불러온 선수의 코드로 변경
		String code = pick_pno;
		String nickname = pick_pnick;
		String URL1 = "http://lol.inven.co.kr/dataninfo/proteam/progamer.php?code=" + code;
		String URL2 = "http://qwer.gg/players/" + nickname;
		latestMatchCrawler(URL1);
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("Headless");
		options.addArguments("disable-infobars");
		options.addArguments("disable-gpu");
		
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		driver = new ChromeDriver(options);
		
		picksCrawler(URL2);
		
	}
	
	public void latestMatchCrawler(String URL1)  {
		Thread t1 = new Thread(new Runnable() {
			String LMT_date = "0";
			String LMT_league = "0";
			String LMT_champ = "0";
			String LMT_winlose = "0";
			String LMT_kda = "0";
			String LMT_kRate = "0";
			@Override
			public void run() {
				Document doc = null;
				try {
					doc = Jsoup.connect(URL1).get();
					
					if(doc != null) {
						Elements element = doc.select("div.listTable");
						Iterator<Element> ie1 = element.select("th, td").iterator();
						String[] strArr = {"일자", "경기 정보", "소환사명", "챔피언", "소환주문", "승패", "K", "D", "A", "KDA", "킬관여율"};
						int count = 0;
					    while(ie1.hasNext()) {
							String temp = ie1.next().text();
							if(temp.equals("경기"))
								break;
							if(!Arrays.stream(strArr).anyMatch(temp::equals)) {
								
								if(count == 0) {
									LMT_date = temp;
									System.out.print(LMT_date + "\t");
								}
								else if (count == 1) {
									LMT_league = temp;
									System.out.print(LMT_league + "\t");
								}
								else if (count == 3) {
									LMT_champ = temp;
									System.out.print(LMT_champ + "\t");
								}
								else if (count == 5) {
									LMT_winlose = temp;
									System.out.print(LMT_winlose + "\t");
								}
								else if (count == 9) {
									LMT_kda = temp;
									System.out.print(LMT_kda + "\t");
								}
								else if (count == 10) {
									LMT_kRate = temp;
									System.out.print(LMT_kRate + "\t");
								}
								count++;
								if(count == 11) {
									
									ObservableList<LatestMatchTableRow> value = FXCollections.observableArrayList(
										new LatestMatchTableRow(new SimpleStringProperty(LMT_date), new SimpleStringProperty(LMT_league), new SimpleStringProperty(LMT_champ), new SimpleStringProperty(LMT_winlose), new SimpleStringProperty(LMT_kda), new SimpleStringProperty(LMT_kRate))
									);
									
									LMT_dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
									LMT_leagueColumn.setCellValueFactory(cellData -> cellData.getValue().leagueProperty());
									LMT_champColumn.setCellValueFactory(cellData -> cellData.getValue().champProperty());
									LMT_winloseColumn.setCellValueFactory(cellData -> cellData.getValue().winloseProperty());
									LMT_kdaColumn.setCellValueFactory(cellData -> cellData.getValue().kdaProperty());
									LMT_kRateColumn.setCellValueFactory(cellData -> cellData.getValue().kRateProperty());
									
									LMTable.getItems().add(new LatestMatchTableRow(new SimpleStringProperty(LMT_date), new SimpleStringProperty(LMT_league), new SimpleStringProperty(LMT_champ), new SimpleStringProperty(LMT_winlose), new SimpleStringProperty(LMT_kda), new SimpleStringProperty(LMT_kRate)));
									
									count = 0;
									System.out.println();
								}
									
							}
						}
					    
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t1.setDaemon(true);
		t1.start();
	}
	
	public void picksCrawler(String URL2) {
		Thread t2 = new Thread(new Runnable() {
			String p_champ = "0";
			String p_games = "0";
			String p_win = "0";
			String p_lose = "0";
			String p_winRate = "0";
			@Override
			public void run() {
				// TODO Auto-generated method stub
				driver.get(URL2);
				
				try {
					
					webElement = driver.findElement(By.tagName("tbody"));
					//챔피언 통계
					for(WebElement e : webElement.findElements(By.tagName("tr"))) {
						int num = 0;
						for(WebElement e1 : e.findElements(By.tagName("td"))) {
								String temp = e1.getText();
								
								if(temp.charAt(temp.length()-1)=='%') {
									p_winRate = temp;
									num = 0;
									PT_champion.setCellValueFactory(cellData -> cellData.getValue().p_championProperty());
									PT_games.setCellValueFactory(cellData -> cellData.getValue().p_gamesProperty());
									PT_win.setCellValueFactory(cellData -> cellData.getValue().p_winProperty());
									PT_lose.setCellValueFactory(cellData -> cellData.getValue().p_loseProperty());
									PT_winRate.setCellValueFactory(cellData -> cellData.getValue().p_winRateProperty());
									
									pickTable.getItems().add(new PickTableRow(new SimpleStringProperty(p_champ), new SimpleStringProperty(p_games), new SimpleStringProperty(p_win), new SimpleStringProperty(p_lose), new SimpleStringProperty(p_winRate)));
									
									
									break;
								}
								else if(num == 0) {
									System.out.print(temp + " ");
									p_champ = temp;
									num++;
								}
								else if(num == 1) {
									p_games = temp;
									num++;
								}
								else if(num == 2) {
									p_win = temp;
									num++;
								}
								else if(num == 3) {
									p_lose = temp;
									num++;
								}
									
						}
					}
					System.out.println();
					
					


					WebElement ee = driver.findElement(By.className("ChampionBuild__container"));
					if(pposition.equals("SUP") || pposition.equals("BOT"))
						vsorwith.setText("WITH");
					else
						vsorwith.setText("VS");
					lastChampion.setText(ee.findElement(By.tagName("img")).getAttribute("title"));
					Opponent_C.setText(ee.findElement(By.className("ChampionBuild__opponent")).findElement(By.className("ChampionBuild__player__name")).getText() );
					Opponent_ID.setText(ee.findElement(By.className("ChampionBuild__opponent")).findElement(By.tagName("img")).getAttribute("title"));
					lastStats.setText(ee.findElement(By.className("ChampionBuild__stats")).getText());;
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					driver.quit();
				}
				
			}
			
		});
		t2.setDaemon(true);
		t2.start();
	}
	
}
