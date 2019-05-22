package software.ii.project;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;



public class HomeScreenController implements Initializable {

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button addAppButton;

    @FXML
    private Button updateAppButton;

    @FXML
    private Button deleteAppButton;
    
    @FXML
    private Button viewCustomersButton;
        
    @FXML
    private Button thisWeekButton;
    
    @FXML
    private Button thisMonthButton;
    
    @FXML
    private Button reportSubmitButton;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerAddressTextField;

    @FXML
    private TextField appTypeTextField;

    @FXML
    private TextField customerPhoneTextField;

    @FXML
    private TextField custIDTextField;

    @FXML
    private TextField custIDAppsTextField;

    @FXML
    private TextField appIDTextField;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> cityComboBox;
    
    @FXML
    private ComboBox<String> startHourComboBox;
    
    @FXML
    private ComboBox<String> startMinutesComboBox;
    
    @FXML
    private ComboBox<String> endHourComboBox;
    
    @FXML
    private ComboBox<String> endMinutesComboBox;
    
    @FXML
    private ComboBox<String> reportComboBox;
    
    @FXML
    private DatePicker datePicker;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initializeCountry();
        
        for (int i = 8; i < 18; i++) { // Requirement F: Exception control - appointments can only be scheduled between 8 a.m. and 5:45 p.m.
            startHourComboBox.getItems().add(Integer.toString(i));
        }
        startMinutesComboBox.getItems().add("00");
        startMinutesComboBox.getItems().add("15");
        startMinutesComboBox.getItems().add("30");
        startMinutesComboBox.getItems().add("45");
        
        for (int i = 8; i < 18; i++) {
            endHourComboBox.getItems().add(Integer.toString(i));
        }
        endMinutesComboBox.getItems().add("00");
        endMinutesComboBox.getItems().add("15");
        endMinutesComboBox.getItems().add("30");
        endMinutesComboBox.getItems().add("45");

        reportComboBox.getItems().add("Number of appointments by month");
        reportComboBox.getItems().add("Number of appointments by city");
        reportComboBox.getItems().add("Consultant schedules");
        
        // Requirement G: Lambda expression - An alternate way to activate a button event without using a handler
        reportSubmitButton.setOnAction((ActionEvent event) -> {
        Stage stage;
        Parent root;
        Scene scene;
        String selection = reportComboBox.getValue();
        
        switch (selection) {
            case "Number of appointments by month":
                try{
                    stage = (Stage) reportSubmitButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TypesByMonth.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }       
                break;
            case "Number of appointments by city":
                try{
                    stage = (Stage) reportSubmitButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AppsByCity.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }       
                break;
            default:
                try{
                    stage = (Stage) reportSubmitButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultantSchedules.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }       
                break;
        }
    });
}
    
    @FXML
    private void initializeCity() {
        // activates when a country is selected
        String country = countryComboBox.getValue();
        
        String sql = "SELECT city.city "
                + "FROM city, country "
                + "WHERE city.countryId = country.countryId "
                + "AND country.country = \"" + country + "\"";
        
        ResultSet result = accessDB(sql);
        cityComboBox.getItems().clear();
        
        try {
            while (result.next()) {
                cityComboBox.getItems().add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initializeCountry() {
        
        ResultSet result = accessDB("SELECT country FROM country");
        try {
            while (result.next()) {
                countryComboBox.getItems().add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet accessDB(String sql) {

        ResultSet result = null;

        try {

            Statement stmt;

            stmt = conn.createStatement();
            result = stmt.executeQuery(sql);
            
            result.beforeFirst();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @FXML
    void addCustomerButtonHandler(ActionEvent event) {
        // Requirement F: Exception control - ensure no fields are null
        if (!customerNameTextField.getText().equals("") && !customerAddressTextField.getText().equals("") && !customerPhoneTextField.getText().equals("") 
                && countryComboBox != null && cityComboBox != null && event.getSource() == addCustomerButton){
            try {      
                int cityId = 0;
                int addressId = 0;
                PreparedStatement pstmt = null;
                boolean addressExists = false;
                String citySelected = cityComboBox.getValue();
                
                // grab cityId
                ResultSet cityIdRS = conn.createStatement().executeQuery("SELECT cityId FROM city WHERE city = '" + citySelected + "'");
                cityIdRS.next();
                cityId = cityIdRS.getInt("cityId");
                
                // check to see if inputted address already exists (using address, phone, and cityId)
                ResultSet existAdd = conn.createStatement().executeQuery("SELECT address, phone, addressId FROM address WHERE cityId = '" + cityId + "'");
                while (existAdd.next()) {
                    String selAddress = existAdd.getString("address");
                    String selPhone = existAdd.getString("phone");
                    
                    if (selAddress.equals(customerAddressTextField.getText()) && selPhone.equals(customerPhoneTextField.getText())) {
                        addressExists = true;
                        addressId = existAdd.getInt("addressId");
                    }
                }
                
                // insert new address if it doesn't already exist
                if (addressExists == false) {
                    pstmt = conn.prepareStatement("INSERT INTO address(address, address2, phone, cityId, postalCode, createDate, "
                            + "createdBy, lastUpdate, lastUpdateBy) VALUES (" + 
                            "'" + customerAddressTextField.getText() + "', " +
                            "0, " +
                            "'" + customerPhoneTextField.getText() + "', " +
                            "'" + cityId + "', " +
                            "0, " +
                            "now(), " +
                            "'" + LogInScreenController.user + "', " +
                            "now(), " +
                            "'" + LogInScreenController.user + "')");
                    pstmt.execute();
                    
                    ResultSet custAddIdRS = conn.createStatement().executeQuery("SELECT LAST_INSERT_ID() FROM address");
                    custAddIdRS.next();
                    addressId = custAddIdRS.getInt("LAST_INSERT_ID()");
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Notice!");
                    alert.setContentText("Address Successfully Added!\n\nNew Address ID: " + addressId);
                    alert.showAndWait();
                }
                

                // insert new customer
                pstmt = conn.prepareStatement("INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (" + 
                        "'" + customerNameTextField.getText() + "', " + 
                        "'" + addressId + "', " +
                        "'1', " +
                        "now(), " +
                        "'" + LogInScreenController.user + "', " +
                        "now(), " +
                        "'" + LogInScreenController.user + "')");
                pstmt.execute();
                
                ResultSet newCustIdRS = conn.createStatement().executeQuery("SELECT LAST_INSERT_ID() FROM customer");
                newCustIdRS.next();
                int newCustId = newCustIdRS.getInt("LAST_INSERT_ID()");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Notice!");
                alert.setContentText("Customer Successfully Added!\n\nCustomer ID: " + newCustId);
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please ensure that no fields are null.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateCustomerButtonHandler(ActionEvent event) {
        // Requirement F: Exception control - ensure no fields are null
        if (!customerNameTextField.getText().equals("") && !customerAddressTextField.getText().equals("") && !customerPhoneTextField.getText().equals("") 
                && countryComboBox != null && cityComboBox != null && !custIDTextField.getText().equals("") && event.getSource() == updateCustomerButton){
            try {
                ResultSet result = conn.createStatement().executeQuery("SELECT customerId FROM customer WHERE customerId = '" + custIDTextField.getText() + "'");
                if (result.next()) { // Requirement F: Exception control - check to see if customerId exists
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Customer Update");
                    alert.setContentText("Are you sure you want to update this customer?");

                    Optional<ButtonType> button = alert.showAndWait();
                    if (button.get() == ButtonType.OK){
                        String customerId = result.getString("customerId");                  
                        int addressId = 0;
                        ResultSet addressIdRS = conn.createStatement().executeQuery("SELECT addressId FROM customer WHERE customerId = " + customerId);
                        addressIdRS.next();
                        addressId = addressIdRS.getInt("addressId");

                        int cityId = 0;
                        PreparedStatement pstmt = null;
                        boolean addressExists = false;
                        String citySelected = cityComboBox.getValue();

                        // grab cityId
                        ResultSet cityIdRS = conn.createStatement().executeQuery("SELECT cityId FROM city WHERE city = '" + citySelected + "'");
                        cityIdRS.next();
                        cityId = cityIdRS.getInt("cityId");

                        // check to see if inputted address already exists (using address, phone, and cityId)
                        ResultSet existAdd = conn.createStatement().executeQuery("SELECT address, phone, addressId FROM address WHERE cityId = '" + cityId + "'");
                        while (existAdd.next()) {
                            String selAddress = existAdd.getString("address");
                            String selPhone = existAdd.getString("phone");

                            if (selAddress.equals(customerAddressTextField.getText()) && selPhone.equals(customerPhoneTextField.getText())) {
                                addressExists = true;
                                addressId = existAdd.getInt("addressId");
                            }
                        }

                        // update new address if it doesn't already exist
                        if (addressExists == false) {
                            pstmt = conn.prepareStatement("UPDATE address " +
                                    "SET address = '" + customerAddressTextField.getText() + "', " +
                                    "phone = '" + customerPhoneTextField.getText() + "', " +
                                    "cityId = '" + cityId + "', " +
                                    "lastUpdate = now(), " +
                                    "lastUpdateBy = '" + LogInScreenController.user + "'" +
                                    "WHERE addressId = '" + addressId + "'");
                            pstmt.execute();

                            Alert updateAlert = new Alert(Alert.AlertType.INFORMATION);
                            updateAlert.setTitle("Success!");
                            updateAlert.setHeaderText("Notice!");
                            updateAlert.setContentText("Address Successfully Updated!\n\nAddress ID: " + addressId);
                            updateAlert.showAndWait();

                        }
                        // update new customer
                        pstmt = conn.prepareStatement("UPDATE customer " + 
                                "SET customerName = '" + customerNameTextField.getText() + "', " + 
                                "addressId = '" + addressId + "', " +
                                "lastUpdate = now(), " +
                                "lastUpdateBy = '" + LogInScreenController.user + "' " +
                                "WHERE customerId = '" + customerId + "'");
                        pstmt.execute();

                        Alert updateAlert = new Alert(Alert.AlertType.INFORMATION);
                        updateAlert.setTitle("Success!");
                        updateAlert.setHeaderText("Notice!");
                        updateAlert.setContentText("Customer Successfully Updated!\n\nCustomer ID: " + customerId);
                        updateAlert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Warning");
                    alert.setContentText("The customer ID does not exist.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please ensure that no fields are null.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void deleteCustomerButtonHandler(ActionEvent event) {
        // Requirement F: Exception control - ensure no fields are null
        if (!custIDTextField.getText().equals("") && event.getSource() == deleteCustomerButton) {
            try {
                ResultSet getCustID = conn.createStatement().executeQuery("SELECT customerId FROM customer WHERE customerId = '" + custIDTextField.getText() + "'");
                if (getCustID.next()) { // Requirement F: Exception control - check to see if customerId exists
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Deleting Customer...");
                    alert.setContentText("Are you sure you want to delete this customer?");

                    Optional<ButtonType> button = alert.showAndWait();
                    if (button.get() == ButtonType.OK){
                        String customerId = getCustID.getString("customerId");
                        if (custIDTextField.getText().equals(customerId)) {
                            // Delete all appointments associated with customer first
                            PreparedStatement pstmt = null;
                            ResultSet getAppID = conn.createStatement().executeQuery("SELECT appointmentId FROM appointment WHERE customerId = " + customerId);
                            if (getAppID.next()) {
                                pstmt = conn.prepareStatement("DELETE FROM appointment WHERE customerId = '" + customerId + "'");
                                pstmt.execute();
                            }
                            // Get customer address ID
                            ResultSet getCustAdd = conn.createStatement().executeQuery("SELECT addressId FROM customer WHERE customerId = '" + customerId + "'");
                            getCustAdd.next();
                            String addressId = getCustAdd.getString("addressId");
                            // Delete customer record
                            pstmt = conn.prepareStatement("DELETE FROM customer WHERE customerId = '" + customerId + "'");
                            pstmt.execute();
                            // Check if any other customer records have same address ID
                            ResultSet addRS = conn.createStatement().executeQuery("SELECT addressId FROM customer WHERE addressId = '" + addressId + "'");
                            if (!addRS.next()) { // Delete address record if not used with any other customers
                                pstmt = conn.prepareStatement("DELETE FROM address WHERE addressId = '" + addressId + "'");
                                pstmt.execute();
                            }

                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Warning");
                    alert.setContentText("Customer ID does not exist.");
                    alert.showAndWait();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please insert a Customer ID to delete.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void addAppButtonHandler(ActionEvent event) {
        // Requirement F: Exception control - ensure no fields are null
        if (!custIDAppsTextField.getText().equals("") && !appTypeTextField.getText().equals("") && startHourComboBox != null && startMinutesComboBox != null
                && endHourComboBox != null && endMinutesComboBox != null && datePicker != null && event.getSource() == addAppButton){ 
            try {
                int userId = 0;
                String customerId = null;
                int cityId = 0;
                int addId = 0;
                String location = null;
                PreparedStatement pstmt = null;
                boolean noOverlap = true;
                
                String startHour = startHourComboBox.getValue();
                String startMinutes = startMinutesComboBox.getValue();
                String startTimeStr = startHour + startMinutes;
                int startTime = Integer.parseInt(startTimeStr);
                String endHour = endHourComboBox.getValue();
                String endMinutes = endMinutesComboBox.getValue();
                String endTimeStr = endHour + endMinutes;
                int endTime = Integer.parseInt(endTimeStr);
                ResultSet custIDRS = conn.createStatement().executeQuery("SELECT customerId FROM customer WHERE customerId = '" + 
                            custIDAppsTextField.getText() + "'");
                if (custIDRS.next()) { // Requirement F: Exception control - check to see if customerId exists
                    if (startTime < endTime) { // Requirement F: Exception control - ensure that start is before end
                        String appType = appTypeTextField.getText();

                        LocalDate startDate = datePicker.getValue();
                        LocalDate endDate = datePicker.getValue();

                        ResultSet userIdRS = conn.createStatement().executeQuery("SELECT userId FROM user WHERE userName = '" + LogInScreenController.user + "'");
                        userIdRS.next();
                        userId = userIdRS.getInt("userId");
                        customerId = custIDAppsTextField.getText();

                        // get location (city of customer)
                        ResultSet addIdRS = conn.createStatement().executeQuery("SELECT addressId FROM customer WHERE customerId = '" + customerId + "'");
                        addIdRS.next();
                        addId = addIdRS.getInt("addressId");
                        ResultSet cityIdRS = conn.createStatement().executeQuery("SELECT cityId FROM address WHERE addressId = '" + addId + "'");
                        cityIdRS.next();
                        cityId = cityIdRS.getInt("cityId");
                        ResultSet cityRS = conn.createStatement().executeQuery("SELECT city FROM city WHERE cityId = '" + cityId + "'");
                        cityRS.next();
                        location = cityRS.getString("city");

                        ResultSet appsRS = conn.createStatement().executeQuery("SELECT start, end FROM appointment WHERE customerId = " + customerId);
                        while (appsRS.next()) { // Requirement F: Exception Control - ensure no appointments overlap
                            Timestamp selStartDate = appsRS.getTimestamp("start");
                            LocalDate selStartLD = selStartDate.toLocalDateTime().toLocalDate();
                            Timestamp selStartTime = appsRS.getTimestamp("start");
                            LocalTime selStartLT = selStartTime.toLocalDateTime().toLocalTime();
                            Timestamp selEndTime = appsRS.getTimestamp("end");
                            LocalTime selEndLT = selEndTime.toLocalDateTime().toLocalTime();

                            // Convert new entry into LocalTime
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
                            String startLTString = startHour + ":" + startMinutes + ":00";
                            LocalTime startLT = LocalTime.parse(startLTString, formatter);
                            String endLTString = endHour + ":" + endMinutes + ":00";
                            LocalTime endLT = LocalTime.parse(endLTString, formatter);
                            

                            // Compare new entry with existing entries
                            // Scheduling restrictions: New app cannot begin same time that another ends or end when another begins
                            LocalDate appDate = datePicker.getValue();
                            if (selStartLD.equals(appDate) && ((endLT.isAfter(selStartLT) && endLT.isBefore(selEndLT)) || (startLT.isBefore(selEndLT) && startLT.isAfter(selStartLT)))) {
                                noOverlap = false;
                            }

                        }
                        
                        if (noOverlap == true) {

                            LocalDate appDate = datePicker.getValue();
                            String startTimeString = startHour + ":" + startMinutes + ":00";
                            String endTimeString = endHour + ":" + endMinutes + ":00";
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
                            
                            LocalTime startLTime = LocalTime.parse(startTimeString, formatter);
                            ZonedDateTime startZDT = ZonedDateTime.of(appDate, startLTime, ZoneId.systemDefault()); // set start time at user timezone
                            ZonedDateTime startUTC = startZDT.withZoneSameInstant(ZoneId.of("UTC")); // convert start time to UTC (database timezone)
                            
                            LocalTime endLTime = LocalTime.parse(endTimeString, formatter);
                            ZonedDateTime endZDT = ZonedDateTime.of(appDate, endLTime, ZoneId.systemDefault()); // set end time at user timezone
                            ZonedDateTime endUTC = endZDT.withZoneSameInstant(ZoneId.of("UTC")); // convert end time to UTC (database timezone)
                            
                            // change converted timezones into database format
                            String startUTCStr = startUTC.toString().substring(0, 16).replace("T", " ");
                            String endUTCStr = endUTC.toString().substring(0, 16).replace("T", " ");
                            
                            
                            pstmt = conn.prepareStatement("INSERT INTO appointment(customerId, title, description, location, contact, url, start, end, "
                                    + "createDate, createdBy, lastUpdate, lastUpdateBy, userId, type) VALUES (" + 
                                    "'" + customerId + "', " +
                                    "'null', " +
                                    "'null', " +
                                    "'" + location + "', " +
                                    "'null', " +
                                    "'null', " +
                                    "'" + startUTCStr + "', " +
                                    "'" + endUTCStr + "', " +
                                    "now(), " +
                                    "'" + LogInScreenController.user + "', " +
                                    "now(), " +
                                    "'" + LogInScreenController.user + "', " +
                                    "'" + userId + "', " +
                                    "'" + appType + "')");
                            pstmt.execute();
                            
                            ResultSet appIdRS = conn.createStatement().executeQuery("SELECT LAST_INSERT_ID() FROM appointment");
                            appIdRS.next();
                            int appointmentId = appIdRS.getInt("LAST_INSERT_ID()");
                            
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success!");
                            alert.setHeaderText("Notice!");
                            alert.setContentText("Appointment Successfully Added!\n\nAppointment ID: " + appointmentId);
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Warning");
                            alert.setContentText("This customer already has an appointment during this time frame");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("Please ensure that 'end' time is after 'start' time.");
                        alert.showAndWait();
                    }
                } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("Customer ID does not exist.");
                        alert.showAndWait();
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please ensure that no fields are null.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void updateAppButtonHandler(ActionEvent event) { 
        // Requirement F: Exception control - ensure no fields are null
        if (!custIDAppsTextField.getText().equals("") && !appTypeTextField.getText().equals("") && startHourComboBox != null && startMinutesComboBox != null
                && endHourComboBox != null && endMinutesComboBox != null && datePicker != null && !appIDTextField.getText().equals("")
                && event.getSource() == updateAppButton) {
            
            try {
                int userId = 0;
                String customerId = null;
                int cityId = 0;
                int addId = 0;
                String appointmentId = null;
                String location = null;
                PreparedStatement pstmt = null;
                boolean noOverlap = true;
                
                String startHour = startHourComboBox.getValue();
                String startMinutes = startMinutesComboBox.getValue();
                String startTimeStr = startHour + startMinutes;
                int startTime = Integer.parseInt(startTimeStr);
                String endHour = endHourComboBox.getValue();
                String endMinutes = endMinutesComboBox.getValue();
                String endTimeStr = endHour + endMinutes;
                int endTime = Integer.parseInt(endTimeStr);
                
                ResultSet custIDRS = conn.createStatement().executeQuery("SELECT customerId FROM customer WHERE customerId = '" + 
                            custIDAppsTextField.getText() + "'");
                if (custIDRS.next()) { // Requirement F: Exception control - check to see if customerId exists
                    if (startTime < endTime) { // Requirement F: Exception control = ensure that start is before end
                        String appType = appTypeTextField.getText();
                        ResultSet result = conn.createStatement().executeQuery("SELECT appointmentId FROM appointment WHERE appointmentId = '" + 
                                appIDTextField.getText() + "'");
                        
                        while(result.next()) { // Requirement F: Exception control - check to see if appointmentId exists
                            appointmentId = result.getString("appointmentId");         
                            if (appIDTextField.getText().equals(appointmentId)) {
                                LocalDate startDate = datePicker.getValue();
                                LocalDate endDate = datePicker.getValue();

                                ResultSet userIdRS = conn.createStatement().executeQuery("SELECT userId FROM user WHERE userName = '" + LogInScreenController.user + "'");
                                userIdRS.next();
                                userId = userIdRS.getInt("userId");
                                customerId = custIDAppsTextField.getText();
                                // Get location (city of customer)
                                ResultSet addIdRS = conn.createStatement().executeQuery("SELECT addressId FROM customer WHERE customerId = '" + customerId + "'");
                                addIdRS.next();
                                addId = addIdRS.getInt("addressId");
                                ResultSet cityIdRS = conn.createStatement().executeQuery("SELECT cityId FROM address WHERE addressId = '" + addId + "'");
                                cityIdRS.next();
                                cityId = cityIdRS.getInt("cityId");
                                ResultSet cityRS = conn.createStatement().executeQuery("SELECT city FROM city WHERE cityId = '" + cityId + "'");
                                cityRS.next();
                                location = cityRS.getString("city");

                                ResultSet appsRS = conn.createStatement().executeQuery("SELECT start, end FROM appointment WHERE customerId = " + customerId
                                                    + " AND appointmentId <> " + appointmentId);
                                while (appsRS.next()) { // Requirement F: Exception Control - ensure no appointments overlap
                                    Timestamp selStartDate = appsRS.getTimestamp("start");
                                    LocalDate selStartLD = selStartDate.toLocalDateTime().toLocalDate();
                                    Timestamp selStartTime = appsRS.getTimestamp("start");
                                    LocalTime selStartLT = selStartTime.toLocalDateTime().toLocalTime();
                                    Timestamp selEndTime = appsRS.getTimestamp("end");
                                    LocalTime selEndLT = selEndTime.toLocalDateTime().toLocalTime();

                                    // Convert new entry into LocalTime
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                    String startLTString = startHour + ":" + startMinutes + ":00";
                                    LocalTime startLT = LocalTime.parse(startLTString, formatter);
                                    String endLTString = endHour + ":" + endMinutes + ":00";
                                    LocalTime endLT = LocalTime.parse(endLTString, formatter);

                                    // Compare new entry with existing entries
                                    // Scheduling restrictions: New app cannot begin same time that another ends or end when another begins
                                    LocalDate appDate = datePicker.getValue();
                                    if (selStartLD.equals(appDate) && ((endLT.isAfter(selStartLT) && endLT.isBefore(selEndLT)) || 
                                            (startLT.isBefore(selEndLT) && startLT.isAfter(selStartLT)))) {
                                        noOverlap = false;
                                    }

                                } 

                                if (noOverlap == true) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation");
                                    alert.setHeaderText("Appointment Update");
                                    alert.setContentText("Are you sure you want to update this appointment?");

                                    Optional<ButtonType> button = alert.showAndWait();
                                    if (button.get() == ButtonType.OK){
                                        pstmt = conn.prepareStatement("UPDATE appointment " +
                                                "SET customerId = '" + custIDAppsTextField.getText() + "', " +
                                                "location = '" + location + "', " +
                                                "start = '" + startDate + " " + startHour + ":" + startMinutes + ":00', " +
                                                "end = '" + endDate + " " + endHour + ":" + endMinutes + ":00', " +
                                                "lastUpdate = now(), " +
                                                "lastUpdateBy = '" + LogInScreenController.user + "', " +
                                                "userId = '" + userId + "', " +
                                                "type = '" + appType + "' " +
                                                "WHERE appointmentId = '" + appointmentId + "'");
                                        pstmt.execute();

                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Success!");
                                        alert.setHeaderText("Notice!");
                                        alert.setContentText("Appointment Successfully Updated!\n\nAppointment ID: " + appointmentId);
                                        alert.showAndWait();
                                    }
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Warning");
                                    alert.setHeaderText("Warning");
                                    alert.setContentText("This customer already has an appointment during this time frame");
                                    alert.showAndWait();
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Warning");
                                alert.setHeaderText("Warning");
                                alert.setContentText("The appointment ID does not exist.");
                                alert.showAndWait();
                            }
                        }
                    } else {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("Please ensure that 'end' time is after 'start' time.");
                        alert.showAndWait();
                    } 
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Warning");
                    alert.setContentText("Please insert an existing Customer ID");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }   
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please ensure that no fields are null.");
            alert.showAndWait();
            }
    }
    
    @FXML
    void deleteAppButtonHandler(ActionEvent event) {
        // Requirement F: Exception control - ensure no fields are null
        if (!appIDTextField.getText().equals("") && event.getSource() == deleteAppButton) {
            try {
                ResultSet getAppID = conn.createStatement().executeQuery("SELECT appointmentId FROM appointment WHERE appointmentId = '" + appIDTextField.getText() + "'");
                if (getAppID.next()) { // Requirement F: Exception control - check to see if appointmentId exists
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Deleting Appointment...");
                    alert.setContentText("Are you sure you want to delete this appointment?");

                    Optional<ButtonType> button = alert.showAndWait();
                    if (button.get() == ButtonType.OK){
                    String appointmentId = getAppID.getString("appointmentId");
                        if (appIDTextField.getText().equals(appointmentId)) {
                            PreparedStatement pstmt = null;
                            pstmt = conn.prepareStatement("DELETE FROM appointment WHERE appointmentId = '" + appointmentId + "'");
                            pstmt.execute();
                        }
                    }
                } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("Appointment ID does not exist.");
                        alert.showAndWait();
                    }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please insert an Appointment ID to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void viewCustomersButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) viewCustomersButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewCustomers.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void thisWeekButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) thisWeekButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WeeklyCalendar.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void thisMonthButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) thisMonthButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MonthlyCalendar.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
