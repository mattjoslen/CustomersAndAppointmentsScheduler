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

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ViewCustomersController implements Initializable {

    @FXML
    private TableView<ViewCustomersList> customerView;

    @FXML
    private TableColumn<ViewCustomersList, Integer> custIdCol;

    @FXML
    private TableColumn<ViewCustomersList, String> nameCol;

    @FXML
    private TableColumn<ViewCustomersList, Integer> addIdCol;

    @FXML
    private TableColumn<ViewCustomersList, String> addressCol;

    @FXML
    private TableColumn<ViewCustomersList, String> cityCol;

    @FXML
    private TableColumn<ViewCustomersList, String> countryCol;

    @FXML
    private TableColumn<ViewCustomersList, String> phoneCol;

    @FXML
    private Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<ViewCustomersList> custList = FXCollections.observableArrayList();

        try {
            customerView.setItems(custList);
            
            ResultSet custRS = conn.createStatement().executeQuery("SELECT customerId, customerName, addressId FROM customer");
            while (custRS.next()) {
                int custId = custRS.getInt("customerId");
                String custName = custRS.getString("customerName");
                int addId = custRS.getInt("addressId");
                
                ResultSet addRS = conn.createStatement().executeQuery("SELECT address, cityId, phone FROM address WHERE addressId = " + addId);
                while (addRS.next()) {
                    String address = addRS.getString("address");
                    int cityId = addRS.getInt("cityId");
                    String phone = addRS.getString("phone");
                    
                    ResultSet cityRS = conn.createStatement().executeQuery("SELECT city, countryId FROM city WHERE cityId = " + cityId);
                    while (cityRS.next()) {
                        String city = cityRS.getString("city");
                        int countryId = cityRS.getInt("countryId");
                        
                        ResultSet countryRS = conn.createStatement().executeQuery("SELECT country FROM country WHERE countryId = " + countryId);
                        while (countryRS.next()) {
                            String country = countryRS.getString("country");
                            
                            ViewCustomersList list = new ViewCustomersList(custId, custName, addId, address, city, country, phone);
                            custList.add(list);
                            
                            custIdCol.setCellValueFactory(cellData -> cellData.getValue().getCustId().asObject());
                            nameCol.setCellValueFactory(cellData -> cellData.getValue().getCustName());
                            addIdCol.setCellValueFactory(cellData -> cellData.getValue().getAddId().asObject());
                            addressCol.setCellValueFactory(cellData -> cellData.getValue().getAddress());
                            cityCol.setCellValueFactory(cellData -> cellData.getValue().getCity());
                            countryCol.setCellValueFactory(cellData -> cellData.getValue().getCountry());
                            phoneCol.setCellValueFactory(cellData -> cellData.getValue().getPhone());
                            
                        }
                    }
                }
            }
            
        } catch (Exception e) {
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
