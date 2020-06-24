package hr;

import javafx.fxml.Initializable;
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

import coach.PracticeTableRow;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

public class HRAllEscMembersController implements Initializable {

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
	String pick_id = null;
	int aa = 0;

	int count = 0;

	@FXML
	public ComboBox<String> p_namecombobox;
	@FXML
	public Button delete;

	// �Ʒ� ���̺� ��

	@FXML
	public TableView<TableRowDataModel> memberstableview;
	@FXML
	private TableColumn<TableRowDataModel, String> p_dept;
	@FXML
	private TableColumn<TableRowDataModel, String> p_name;
	@FXML
	private TableColumn<TableRowDataModel, String> p_id;

	public String get_p_dept = null;
	public String get_p_name = null;
	public String get_p_id = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			showmembers();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			stat = conn.createStatement();
			rs = stat.executeQuery("SELECT P_ID FROM \"C##ORA_ESC\".\"ESC_MEMBERS\"");
			ObservableList<String> playerlist = FXCollections.observableArrayList();
			while (rs.next()) {
				playerlist.add(new String(rs.getString(1)));
			}
			p_namecombobox.setItems(playerlist);
			rs.close();
			p_comboAction();

			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void deleteAction() {
		memberstableview.getItems().removeAll(this.memberstableview.getItems());
	}
	
	public void p_comboAction() {
		// ���ý� ����Ǵ� ����.
		p_namecombobox.setOnAction(event -> {
			pick_id = (p_namecombobox.getValue());
		});
	}

	public void deleteAction(MouseEvent e) {
		deleteAction();
		deleteplayer();
		showmembers();
	}

	public void deleteplayer() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			query = "DELETE FROM \"C##ORA_ESC\".\"ESC_MEMBERS\" WHERE P_ID = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pick_id);
			pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showmembers() {
		try {
			// �ܾ���°�.
			count = 0;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, ID, PW);
			query = "SELECT COUNT(*) FROM \"C##ORA_ESC\".\"ESC_MEMBERS\"";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = Integer.parseInt(rs.getString(1));
			}
			pstmt.close();
			
			String getDept[] = new String[count];
			String getName[] = new String[count];
			String getId[] = new String[count];
			
			query = "SELECT P_DEPT, P_NAME, P_ID FROM \"C##ORA_ESC\".\"ESC_MEMBERS\"";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			int i = 0;
			while(rs.next()) {
				if (i < count) {
					getName[i] = rs.getString(2);
					getDept[i] = rs.getString(1);
					getId[i] = rs.getString(3);
					System.out.println(getName[i]);
				}
				i++;
			}
			
			pstmt.close();
			conn.close();

			String insertDept;
			String insertName;
			String insertId;
			System.out.println("");
			for (int j = 0; j < count; j++) {

				insertDept = getDept[j];
				insertName = getName[j];
				insertId = getId[j];
				System.out.println(insertDept +"  " + insertName + "  " + insertId);

				memberstableview.getItems()
				.add((new TableRowDataModel(new SimpleStringProperty(insertDept),
						new SimpleStringProperty(insertName), new SimpleStringProperty(insertId))));
				
				p_dept.setCellValueFactory(cellData -> cellData.getValue().deptProperty());
				p_name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
				p_id.setCellValueFactory(cellData -> cellData.getValue().idProperty());
				
			

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class TableRowDataModel {
	private StringProperty dept;
	private StringProperty name;
	private StringProperty id;

	public TableRowDataModel(StringProperty dept, StringProperty name, StringProperty id) {
		this.dept = dept;
		this.name = name;
		this.id = id;
	}
	

	public StringProperty deptProperty() {
		return dept;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty idProperty() {
		return id;
	}

}
