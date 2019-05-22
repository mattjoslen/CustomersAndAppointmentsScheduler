package software.ii.project;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
public class WeeklyCalendarController implements Initializable {
    
    
    @FXML
    private TableView<AppointmentList> weeklyAppsView;

    @FXML
    private TableColumn<AppointmentList, Integer> weeklyAppID;

    @FXML
    private TableColumn<AppointmentList, Integer> weeklyCustID;

    @FXML
    private TableColumn<AppointmentList, String> weeklyStart;

    @FXML
    private TableColumn<AppointmentList, String> weeklyEnd;

    @FXML
    private TableColumn<AppointmentList, String> weeklyType;
    
    @FXML
    private Button weeklyReturnButton;
    
    // Requirement D: View calendar by week
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<AppointmentList> weeklyAppList = FXCollections.observableArrayList();
        
        try {
            weeklyAppsView.setItems(weeklyAppList);
            
            ResultSet result = conn.createStatement().executeQuery("SELECT appointmentId, customerId, start, end, type "
                    + "FROM appointment "
                    + "WHERE start <= DATE_ADD(now(), INTERVAL 7 DAY)");
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
                weeklyAppList.add(list);
            }
            // Populate table
            weeklyAppID.setCellValueFactory(cellData -> cellData.getValue().getAppID().asObject());
            weeklyCustID.setCellValueFactory(cellData -> cellData.getValue().getCustID().asObject());
            weeklyStart.setCellValueFactory(cellData -> cellData.getValue().getStart());
            weeklyEnd.setCellValueFactory(cellData -> cellData.getValue().getEnd());
            weeklyType.setCellValueFactory(cellData -> cellData.getValue().getType());
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
    
    @FXML
    void weeklyReturnButtonHandler (ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) weeklyReturnButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
