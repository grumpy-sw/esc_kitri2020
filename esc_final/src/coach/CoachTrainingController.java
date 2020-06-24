package coach;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import coach.TableRowDataModel;

public class CoachTrainingController implements Initializable {

	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String ID = "c##ora_esc";
	String PW = "1234";
	String query = null;

	Connection conn = null;
	Statement stat = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String practice_id = null;
	String login_id = null;

	String pick_name = null;
	@FXML
	private Text mostPick;
	
	@FXML
	public ComboBox<String> p_namecombobox;
	@FXML
	private Text coach_id;
	@FXML
	public TextArea feedback;
	@FXML
	public Button delete;
	@FXML
	public Button chartload;
	@FXML
	public ComboBox<String> combo;
	@FXML
	LineChart<String, Number> lineChart;

	@FXML
	private TableView<PracticeTableRow> PracticeTable;
	@FXML
	private TableColumn<PracticeTableRow, String> p_indexColumn;
	@FXML
	private TableColumn<PracticeTableRow, String> p_timestampColumn;
	@FXML
	private TableColumn<PracticeTableRow, String> p_typeColumn;
	@FXML
	private TableColumn<PracticeTableRow, String> p_championColumn;
	@FXML
	private TableColumn<PracticeTableRow, String> p_resultColumn;
	
	
	@FXML
	public TableView<TableRowDataModel> stattableview;
	@FXML
	private TableColumn<TableRowDataModel, Integer> power;
	@FXML
	private TableColumn<TableRowDataModel, Integer> survive;
	@FXML
	private TableColumn<TableRowDataModel, Integer> growth;
	@FXML
	private TableColumn<TableRowDataModel, Integer> sight;
	@FXML
	private TableColumn<TableRowDataModel, Integer> variable;
	@FXML
	private TableColumn<TableRowDataModel, Integer> mental;

	@FXML
	private TextField playStyle;
	
	public int get_power, get_survive, get_growth, get_sight, get_variable, get_mental;
	
	public int get_power2, get_survive2, get_growth2, get_sight2, get_variable2, get_mental2;
	
	HashMap<String, Integer> hsmap = new HashMap<>();
	String number = "";
	String tpt = "";
	@FXML
	private TextArea comment;
	@FXML
	private Button trainregister;

	@FXML
	private ComboBox<String> tptcombo;
	ObservableList<String> list = FXCollections.observableArrayList("전투력", "생존", "성장", "시야", "변수", "심리");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tptcombo.setItems(list);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			stat = conn.createStatement();
			rs = stat.executeQuery("SELECT P_NAME FROM \"C##ORA_ESC\".\"PLAYER\"");
			ObservableList<String> playerlist = FXCollections.observableArrayList();
			while (rs.next()) {
				playerlist.add(new String(rs.getString(1)));
			}
			p_namecombobox.setItems(playerlist);
			combo.setItems(playerlist);
			rs.close();
			p_comboAction();	
			tptcomboAction();	//훈련해야하는 종류 선택

			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void p_comboAction() {
		// 선택시 실행되는 구문.
		
		p_namecombobox.setOnAction(event -> {
			
			PracticeTable.getItems().removeAll(this.PracticeTable.getItems());
			
			pick_name = (p_namecombobox.getValue());
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, ID, PW);
				stat = conn.createStatement();
				
				String tfeedback = "";
				String tcomment = "";
				String tmp = "";
				String pstyle = "";
				String sp = "";
				String ss = "";
				String sg = "";
				String sst = "";
				String sv = "";
				String sm = "";
				
				query = "SELECT P.P_TID, P.P_NO, T.T_FEEDBACK, T.T_COMMENT, T.T_PT, P.P_STYLE, S.S_POWER, S.S_SURVIVE, S.S_GROWTH, S.S_SIGHT, S.S_VARIABLE, S.S_MENTAL FROM PLAYER P INNER JOIN TRAINING T ON P.P_NO = T.P_NO INNER JOIN STAT S ON S.P_NO = P.P_NO WHERE P_NAME = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,  p_namecombobox.getValue());
				rs = pstmt.executeQuery();
				while(rs.next()) {
					practice_id = rs.getString(1);
					number = rs.getString(2);
					tfeedback = rs.getString(3);
					tcomment = rs.getString(4);
					tmp = rs.getString(5);
					pstyle = rs.getString(6);
					sp = rs.getString(7);
					ss = rs.getString(8);
					sg = rs.getString(9);
					sst = rs.getString(10);
					sv = rs.getString(11);
					sm = rs.getString(12);
				}
				rs.close();
				conn.close();
				
				feedback.setText(tfeedback);
				comment.setText(tcomment);
				tptcombo.setPromptText(tmp);
				playStyle.setText(pstyle);
				get_power = Integer.parseInt(sp);
				get_survive = Integer.parseInt(ss);
				get_growth = Integer.parseInt(sg);
				get_sight = Integer.parseInt(sst);
				get_survive = Integer.parseInt(sv);
				get_mental = Integer.parseInt(sm);
				
				inquiryAction();
				
				///~~~~~~~~~~~~~~~~~여기까지 수치 긁어오기
				
				ObservableList<TableRowDataModel> myList = FXCollections.observableArrayList(new TableRowDataModel(
						new SimpleIntegerProperty(get_power), new SimpleIntegerProperty(get_survive),
						new SimpleIntegerProperty(get_growth), new SimpleIntegerProperty(get_sight),
						new SimpleIntegerProperty(get_variable), new SimpleIntegerProperty(get_mental)));
				
				power.setCellValueFactory(cellData -> cellData.getValue().powerProperty().asObject());
				survive.setCellValueFactory(cellData -> cellData.getValue().surviveProperty().asObject());
				growth.setCellValueFactory(cellData -> cellData.getValue().growthProperty().asObject());
				sight.setCellValueFactory(cellData -> cellData.getValue().sightProperty().asObject());
				variable.setCellValueFactory(cellData -> cellData.getValue().variableProperty().asObject());
				mental.setCellValueFactory(cellData -> cellData.getValue().mentalProperty().asObject());
				
				stattableview.setItems(myList);
				stattableview.setEditable(true);

				//여기부터 수치 수정~~~~~~~~~~~~~
				
				power.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				power.setOnEditCommit(
			            new EventHandler<CellEditEvent<TableRowDataModel, Integer>>() {
			             public void handle(CellEditEvent<TableRowDataModel, Integer> t) {
			                ((TableRowDataModel)t.getTableView().getItems().get(t.getTablePosition().getRow())).setPower(t.getNewValue());
			             }
			            });
				survive.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				survive.setOnEditCommit(
			            new EventHandler<CellEditEvent<TableRowDataModel, Integer>>() {
			             public void handle(CellEditEvent<TableRowDataModel, Integer> t) {
			                ((TableRowDataModel)t.getTableView().getItems().get(t.getTablePosition().getRow())).setSurvive(t.getNewValue());
			             }
			            });
				growth.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				growth.setOnEditCommit(
			            new EventHandler<CellEditEvent<TableRowDataModel, Integer>>() {
			             public void handle(CellEditEvent<TableRowDataModel, Integer> t) {
			                ((TableRowDataModel)t.getTableView().getItems().get(t.getTablePosition().getRow())).setGrowth(t.getNewValue());
			             }
			            });
				sight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				sight.setOnEditCommit(
			            new EventHandler<CellEditEvent<TableRowDataModel, Integer>>() {
			             public void handle(CellEditEvent<TableRowDataModel, Integer> t) {
			                ((TableRowDataModel)t.getTableView().getItems().get(t.getTablePosition().getRow())).setSight(t.getNewValue());
			             }
			            });
				variable.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				variable.setOnEditCommit(
			            new EventHandler<CellEditEvent<TableRowDataModel, Integer>>() {
			             public void handle(CellEditEvent<TableRowDataModel, Integer> t) {
			                ((TableRowDataModel)t.getTableView().getItems().get(t.getTablePosition().getRow())).setVariable(t.getNewValue());
			             }
			            });
				mental.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				mental.setOnEditCommit(
			            new EventHandler<CellEditEvent<TableRowDataModel, Integer>>() {
			             public void handle(CellEditEvent<TableRowDataModel, Integer> t) {
			                ((TableRowDataModel)t.getTableView().getItems().get(t.getTablePosition().getRow())).setMental(t.getNewValue());
			             }
			            });
			      
				
			      stattableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowDataModel>() {
			       @Override
					public void changed(ObservableValue<? extends TableRowDataModel> observable,
							TableRowDataModel oldValue, TableRowDataModel newValue) {
						TableRowDataModel model = stattableview.getSelectionModel().getSelectedItem();
						if(model != null) {
							get_power=model.powerProperty().getValue();
				           get_survive=model.surviveProperty().getValue();
				           get_growth=model.growthProperty().getValue();
				           get_sight=model.sightProperty().getValue();
				           get_variable=model.variableProperty().getValue();
				           get_mental=model.mentalProperty().getValue();
						}
					}
			      });			
								
			} catch (Exception e) {
				System.out.println("뭔가 예외처리됨");
				e.printStackTrace();
			}
		});
	}

	public void inquiryAction() {
		// database에서 값 불러와서 채우기
		hsmap = new HashMap<>();
		// code도 불러온 선수의 코드로 변경
		String URL = "https://www.op.gg/summoner/userName=" + practice_id;
		System.out.println("ㅇㅎ" + URL);
		practiceTableCrawler(URL);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("Headless");
		options.addArguments("disable-infobars");
		options.addArguments("disable-gpu");

	}

	public void practiceTableCrawler(String URL) {
		Thread t3 = new Thread(new Runnable() {
			String p_index = "0";
			String p_timestamp = "0";
			String p_type = "0";
			String p_champion = "0";
			String p_result = "0";
			Document doc = null;
			int index = 1;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					doc = Jsoup.connect(URL).get();
					if (doc != null) {
						Elements element = doc.select("div.GameItemList");

						Iterator<Element> ie1 = element.select("div.ChampionImage > a").iterator(); // 챔피언
						Iterator<Element> ie2 = element.select("div.GameItemList > div > div").iterator(); // 승패
						Iterator<Element> ie3 = element.select("div.GameStats > div.GameType").iterator(); // 게임 타입
						Iterator<Element> ie4 = element.select("div.GameStats > div.TimeStamp > span").iterator(); // 타임스탬프

						while (ie2.hasNext()) {

							String result = "";

							if (ie2.next().className().equals("GameItem Win"))
								result = "승리";
							else
								result = "패배";

							String gametype = ie3.next().text();
							String timestamp = ie4.next().text();
							String temp = ie1.next().attr("href");

							String[] tempArr = temp.split("/");

							p_index = Integer.toString(index++);
							p_type = gametype;
							p_timestamp = timestamp;
							p_champion = tempArr[2];
							p_result = result;

							p_indexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
							p_timestampColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
							p_typeColumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());
							p_championColumn.setCellValueFactory(cellData -> cellData.getValue().championProperty());
							p_resultColumn.setCellValueFactory(cellData -> cellData.getValue().resultProperty());

							PracticeTable.getItems()
									.add(new PracticeTableRow(new SimpleStringProperty(p_index),
											new SimpleStringProperty(p_type), new SimpleStringProperty(p_timestamp),
											new SimpleStringProperty(p_champion), new SimpleStringProperty(p_result)));

							if (hsmap.isEmpty()) {
								hsmap.put(tempArr[2], 1);
							}

							else if (hsmap.get(tempArr[2]) == null) {
								hsmap.put(tempArr[2], 1);
							}

							else if (hsmap.get(tempArr[2]) != null) {
								hsmap.put(tempArr[2], hsmap.get(tempArr[2]) + 1);
							}
						}

						Map.Entry<String, Integer> maxEntry = null;
						for (Map.Entry<String, Integer> entry : hsmap.entrySet()) {
							if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >= 0) {
								maxEntry = entry;
							}
						}
						mostPick.setText(maxEntry.getKey() + "(" + maxEntry.getValue() + ")");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});
		t3.setDaemon(true);
		t3.start();
	}

	public void tptcomboAction() {
		tptcombo.setOnAction(event -> {
			tpt += tptcombo.getValue() + "/";
		});
	}

	public void trainregisterAction(MouseEvent e) {
		try {
			System.out.println(tpt);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			
			query = "UPDATE \"C##ORA_ESC\".\"TRAINING\" SET T_PT = ?, T_COMMENT = ?, T_FEEDBACK = ?, C_ID = ? WHERE P_NO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tpt);
			pstmt.setString(2, comment.getText());
			pstmt.setString(3, feedback.getText());
			pstmt.setString(4, coach_id.getText());
			pstmt.setString(5, number);
			pstmt.executeUpdate();
			pstmt.close();

			query = "UPDATE \"C##ORA_ESC\".\"PLAYER\" SET P_STYLE = ? WHERE P_NO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, playStyle.getText());
			pstmt.setString(2, number);
			pstmt.executeUpdate();
			pstmt.close();
			
			TableRowDataModel model = stattableview.getSelectionModel().getSelectedItem();
	           
			if(model != null) {
			get_power=model.powerProperty().getValue();
	           get_survive=model.surviveProperty().getValue();
	           get_growth=model.growthProperty().getValue();
	           get_sight=model.sightProperty().getValue();
	           get_variable=model.variableProperty().getValue();
	           get_mental=model.mentalProperty().getValue();
			}

			query = "UPDATE \"C##ORA_ESC\".\"STAT\" SET S_POWER = ?, S_SURVIVE = ?, S_GROWTH = ?, S_SIGHT = ?, S_VARIABLE = ?, S_MENTAL =? WHERE P_NO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, Integer.toString(get_power));
			pstmt.setString(2, Integer.toString(get_survive));
			pstmt.setString(3, Integer.toString(get_growth));
			pstmt.setString(4, Integer.toString(get_sight));
			pstmt.setString(5, Integer.toString(get_variable));
			pstmt.setString(6, Integer.toString(get_mental));
			pstmt.setString(7, number);
			pstmt.executeUpdate();
			System.out.println("111111111111111111111111");
			pstmt.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void deleteAction(MouseEvent e) {
		lineChart.getData().clear();
	}

	public void chartloadAction(MouseEvent e) {
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		lineChart.getData().remove(series);
		lineChart.setAnimated(false);
		try {
			
			series.setName(combo.getValue());
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			
			query = "SELECT S_POWER, S_SURVIVE, S_GROWTH, S_SIGHT, S_VARIABLE, S_MENTAL FROM \"C##ORA_ESC\".\"STAT\"  NATURAL JOIN \"C##ORA_ESC\".\"PLAYER\"  WHERE P_NO=P_NO AND P_NAME = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, combo.getValue());
			pstmt.executeUpdate();
			rs = pstmt.executeQuery();
			while (rs.next()) {				
				series.getData().add(new XYChart.Data<String, Number>("전투력", Integer.parseInt(rs.getString(1))));
				series.getData().add(new XYChart.Data<String, Number>("생존", Integer.parseInt(rs.getString(2))));
				series.getData().add(new XYChart.Data<String, Number>("성장", Integer.parseInt(rs.getString(3))));
				series.getData().add(new XYChart.Data<String, Number>("시야", Integer.parseInt(rs.getString(4))));
				series.getData().add(new XYChart.Data<String, Number>("변수", Integer.parseInt(rs.getString(5))));
				series.getData().add(new XYChart.Data<String, Number>("심리", Integer.parseInt(rs.getString(6))));
			}
			rs.close();
			
			lineChart.getData().add(series);

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e1) {

		}

	}

	public void setloginId(String loginId) {
		login_id = loginId;
		coach_id.setText(loginId);
		// coachloginid.setText(loginId);
	}
}

class TableRowDataModel {
	private IntegerProperty power;
	private IntegerProperty survive;
	private IntegerProperty growth;
	private IntegerProperty sight;
	private IntegerProperty variable;
	private IntegerProperty mental;

	public TableRowDataModel(IntegerProperty power, IntegerProperty survive, IntegerProperty growth,
			IntegerProperty sight, IntegerProperty variable, IntegerProperty mental) {
		this.power = power;
		this.survive = survive;
		this.growth = growth;
		this.sight = sight;
		this.variable = variable;
		this.mental = mental;
	}

	public IntegerProperty powerProperty() {
		return power;
	}

	public IntegerProperty surviveProperty() {
		return survive;
	}

	public IntegerProperty growthProperty() {
		return growth;
	}

	public IntegerProperty sightProperty() {
		return sight;
	}

	public IntegerProperty variableProperty() {
		return variable;
	}

	public IntegerProperty mentalProperty() {
		return mental;
	}

	
	public void setPower(int arg) {
	      power.set(arg);
	   }
	   
	   public void setSurvive(int arg) {
	      survive.set(arg);
	   }
	   
	   public void setGrowth(int arg) {
	      growth.set(arg);
	   }
	   
	   public void setSight(int arg) {
	      sight.set(arg);
	   }
	   
	   public void setVariable(int arg) {
	      variable.set(arg);
	   }
	   
	   public void setMental(int arg) {
	      mental.set(arg);
	   }
	
	
}