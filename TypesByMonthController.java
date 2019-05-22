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

// Requirement I: Generate report - "number of appointment types by month"
public class TypesByMonthController implements Initializable {

    @FXML
    private TableView<TypesByMonthList> monthlyTypesView;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> janCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> febCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> marchCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> aprilCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> mayCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> juneCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> julyCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> augCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> sepCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> octCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> novCol;
    
    @FXML
    private TableColumn<TypesByMonthList, Integer> decCol;
    
    @FXML
    private Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<TypesByMonthList> typesList = FXCollections.observableArrayList();
        
        try {
            monthlyTypesView.setItems(typesList);
            
            ResultSet janRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-01-%'");
            janRS.next();
            int janCount = janRS.getInt("count");
            
            ResultSet febRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-02-%'");
            febRS.next();
            int febCount = febRS.getInt("count");
            
            ResultSet marchRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-03-%'");
            marchRS.next();
            int marchCount = marchRS.getInt("count");
            
            ResultSet aprilRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-04-%'");
            aprilRS.next();
            int aprilCount = aprilRS.getInt("count");
            
            ResultSet mayRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-05-%'");
            mayRS.next();
            int mayCount = mayRS.getInt("count");
            
            ResultSet juneRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-06-%'");
            juneRS.next();
            int juneCount = juneRS.getInt("count");
            
            ResultSet julyRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-07-%'");
            julyRS.next();
            int julyCount = julyRS.getInt("count");
            
            ResultSet augRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-08-%'");
            augRS.next();
            int augCount = augRS.getInt("count");
            
            ResultSet sepRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-09-%'");
            sepRS.next();
            int sepCount = sepRS.getInt("count");
            
            ResultSet octRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-10-%'");
            octRS.next();
            int octCount = octRS.getInt("count");
            
            ResultSet novRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-11-%'");
            novRS.next();
            int novCount = novRS.getInt("count");
            
            ResultSet decRS = conn.createStatement().executeQuery("SELECT COUNT(DISTINCT type) AS count " + 
                "FROM appointment " +
                "WHERE start LIKE '____-12-%'");
            decRS.next();
            int decCount = decRS.getInt("count");
            
            TypesByMonthList list = new TypesByMonthList(janCount, febCount, marchCount, aprilCount, mayCount, juneCount, julyCount, 
                    augCount, sepCount, octCount, novCount, decCount);
            typesList.add(list);
            
            janCol.setCellValueFactory(cellData -> cellData.getValue().getJanuary().asObject());
            febCol.setCellValueFactory(cellData -> cellData.getValue().getFebruary().asObject());
            marchCol.setCellValueFactory(cellData -> cellData.getValue().getMarch().asObject());
            aprilCol.setCellValueFactory(cellData -> cellData.getValue().getApril().asObject());
            mayCol.setCellValueFactory(cellData -> cellData.getValue().getMay().asObject());
            juneCol.setCellValueFactory(cellData -> cellData.getValue().getJune().asObject());
            julyCol.setCellValueFactory(cellData -> cellData.getValue().getJuly().asObject());
            augCol.setCellValueFactory(cellData -> cellData.getValue().getAugust().asObject());
            sepCol.setCellValueFactory(cellData -> cellData.getValue().getSeptember().asObject());
            octCol.setCellValueFactory(cellData -> cellData.getValue().getOctober().asObject());
            novCol.setCellValueFactory(cellData -> cellData.getValue().getNovember().asObject());
            decCol.setCellValueFactory(cellData -> cellData.getValue().getDecember().asObject());
            
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
