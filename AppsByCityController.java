package software.ii.project;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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

// Requirement I: Generate report = "one additional report of your choice" [number of appointments by city]
public class AppsByCityController implements Initializable {

    @FXML
    private TableView<AppsByCityList> appCityView;

    @FXML
    private TableColumn<AppsByCityList, String> cityCol;

    @FXML
    private TableColumn<AppsByCityList, Integer> appCol;

    @FXML
    private Button returnButton;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<AppsByCityList> appsByCityList = FXCollections.observableArrayList();
        
        try {
            appCityView.setItems(appsByCityList);
            
            ResultSet result = conn.createStatement().executeQuery("SELECT COUNT(appointmentId) AS appCount, location "
                    + "FROM appointment "
                    + "GROUP BY location "
                    + "ORDER BY appCount DESC");
            while (result.next()) {
                int appCount = result.getInt("appCount");
                String city = result.getString("location");
                
                AppsByCityList list = new AppsByCityList(city, appCount);
                appsByCityList.add(list);
                
                cityCol.setCellValueFactory(cellData -> cellData.getValue().getCity());
                appCol.setCellValueFactory(cellData -> cellData.getValue().getAppCount().asObject());
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
