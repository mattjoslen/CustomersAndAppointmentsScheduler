package software.ii.project;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class MonthlyCalendarController implements Initializable {
    
    
    @FXML
    private TableView<AppointmentList> monthlyAppsView;

    @FXML
    private TableColumn<AppointmentList, Integer> monthlyAppID;

    @FXML
    private TableColumn<AppointmentList, Integer> monthlyCustID;

    @FXML
    private TableColumn<AppointmentList, String> monthlyStart;

    @FXML
    private TableColumn<AppointmentList, String> monthlyEnd;

    @FXML
    private TableColumn<AppointmentList, String> monthlyType;
    
    @FXML
    private Button monthlyReturnButton;
    
    
    // Requirement D: View calendar by month
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<AppointmentList> monthlyAppList = FXCollections.observableArrayList();
        
        try {
            monthlyAppsView.setItems(monthlyAppList);
            
            ResultSet result = conn.createStatement().executeQuery("SELECT appointmentId, customerId, start, end, type "
                    + "FROM appointment "
                    + "WHERE start <= DATE_ADD(now(), INTERVAL 30 DAY)");
            while (result.next()) {
                Timestamp start = result.getTimestamp("start");
		// Requirement E: Convert database UTC appointment times to local timezone
                ZoneId newzid = ZoneId.systemDefault();
		ZonedDateTime newzdtStart = start.toLocalDateTime().atZone(ZoneId.of("UTC"));
		ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
                String newLocalStartString = newLocalStart.toString();

                String subStartDate = newLocalStartString.substring(0, 10);
                String subStartTime = newLocalStartString.substring(11, 16);
                newLocalStartString = subStartDate + " " + subStartTime;
                
                Timestamp end = result.getTimestamp("end");
                ZonedDateTime newzdtEnd = end.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);
                String newLocalEndString = newLocalEnd.toString();
                
                String subEndDate = newLocalEndString.substring(0, 10);
                String subEndTime = newLocalEndString.substring(11, 16);
                newLocalEndString = subEndDate + " " + subEndTime;
                
                
                int appID = result.getInt("appointmentId");
                int custID = result.getInt("customerId");                
                String type = result.getString("type");
                
                AppointmentList list = new AppointmentList(appID, custID, newLocalStartString, newLocalEndString, type);
                monthlyAppList.add(list);
            }
            // Populate table
            // Requirement G: Lambda expression - pulls data directly from getter methods and inserts into cells
            // without having to create new objects for each insertion
            monthlyAppID.setCellValueFactory(cellData -> cellData.getValue().getAppID().asObject());
            monthlyCustID.setCellValueFactory(cellData -> cellData.getValue().getCustID().asObject());
            monthlyStart.setCellValueFactory(cellData -> cellData.getValue().getStart());
            monthlyEnd.setCellValueFactory(cellData -> cellData.getValue().getEnd());
            monthlyType.setCellValueFactory(cellData -> cellData.getValue().getType());
            
        } catch(Exception e) {
                e.printStackTrace();
        }
    }    
    
    @FXML
    void monthlyReturnButtonHandler (ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) monthlyReturnButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}