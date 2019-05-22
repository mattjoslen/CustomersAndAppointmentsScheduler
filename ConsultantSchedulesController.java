/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.project;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
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

// Requirement I: Generate report = Consultant Schedules
public class ConsultantSchedulesController implements Initializable {
    @FXML
    private TableView<ConsultantSchedulesList> scheduleView;

    @FXML
    private TableColumn<ConsultantSchedulesList, String> conCol;

    @FXML
    private TableColumn<ConsultantSchedulesList, Integer> appIDCol;

    @FXML
    private TableColumn<ConsultantSchedulesList, Integer> custIDCol;

    @FXML
    private TableColumn<ConsultantSchedulesList, String> locCol;

    @FXML
    private TableColumn<ConsultantSchedulesList, String> starCol;

    @FXML
    private TableColumn<ConsultantSchedulesList, String> endCol;

    @FXML
    private Button returnButton;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<ConsultantSchedulesList> schedList = FXCollections.observableArrayList();
        
        try {
            scheduleView.setItems(schedList);
            
            ResultSet result = conn.createStatement().executeQuery("SELECT createdBy, appointmentId, customerId, location, start, end FROM appointment ORDER BY createdBy, start");
            while(result.next()) {
                String consultant = result.getString("createdBy");
                int appId = result.getInt("appointmentId");
                int custId = result.getInt("customerId");
                String location = result.getString("location");
                String start = result.getString("start");
                String end = result.getString("end");
                
                ConsultantSchedulesList list = new ConsultantSchedulesList(consultant, appId, custId, location, start, end);
                schedList.add(list);
                
                conCol.setCellValueFactory(cellData -> cellData.getValue().getConsultant());
                appIDCol.setCellValueFactory(cellData -> cellData.getValue().getAppId().asObject());
                custIDCol.setCellValueFactory(cellData -> cellData.getValue().getCustId().asObject());
                locCol.setCellValueFactory(cellData -> cellData.getValue().getLocation());
                starCol.setCellValueFactory(cellData -> cellData.getValue().getStart());
                endCol.setCellValueFactory(cellData -> cellData.getValue().getEnd());
            }
        } catch(Exception e) {
                e.printStackTrace();
        }
        
    }    
    
    @FXML
    void returnButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) returnButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
