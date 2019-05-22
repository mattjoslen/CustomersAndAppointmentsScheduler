/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.project;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.console;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author Matt
 */



public class LogInScreenController implements Initializable {

    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button logInButton;
    
    private static ResultSet result;
    
    ResourceBundle rb;
    
    public static String user;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.rb = rb; // Languages available: English and Japanese
        System.out.println(Locale.getDefault());

        username.setText(rb.getString("username"));
        password.setText(rb.getString("password"));
        logInButton.setText(rb.getString("login"));
        SoftwareIIProject.getStage().setTitle(rb.getString("login"));
        
    }    
    
    @FXML
    void logInButtonHandler(ActionEvent event) throws IOException {
        // Requirement F: Exception control - ensure no fields are null
        if (!usernameTextField.getText().equals("") && !passwordTextField.getText().equals("") && event.getSource() == logInButton){
            try {      
                DBConnection.makeConnection();
                result = conn.createStatement().executeQuery("SELECT userName, password FROM user");
                while(result.next()) {
                    String username = result.getString("userName");                  
                    if (usernameTextField.getText().equals(username)) { // Requirement F: Exception control - check to see if userName exists
                        String password = result.getString("password");
                        if (passwordTextField.getText().equals(password)) { // Requirement F: Exception control - check to see if password is correct for user
                            Stage stage;
                            Parent root;

                            stage = (Stage) logInButton.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
                            root = loader.load();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                            
                            user = usernameTextField.getText();
                            // Requirement J: track user log-ins
                            LocalDateTime ldt = LocalDateTime.now();
                            String ldtStr = ldt.toString().replace("T", " ");
                            OutputFile(user, ldtStr);
                            
                            try { // Requirement H: notify user if they have an appointment in next 15 minutes
                                boolean appAlert = false;
                                Timestamp start = null;
                                LocalTime localStartTime = null;
                                ResultSet userAppsRS = conn.createStatement().executeQuery("SELECT start FROM appointment WHERE createdBy = '" + user + "'");
                                
                                while (userAppsRS.next()) {
                                    start = userAppsRS.getTimestamp("start");
                                    ZonedDateTime startUTC = start.toLocalDateTime().atZone(ZoneId.of("UTC"));
                                    ZonedDateTime localStart = startUTC.withZoneSameInstant(ZoneId.systemDefault());
                                    String localStartStr = localStart.toString();
                                    
                                    LocalDateTime userLDT = LocalDateTime.now();
                                    ZonedDateTime userZDT = userLDT.atZone(ZoneId.systemDefault());
                                    ZonedDateTime localUser = userZDT.withZoneSameInstant(ZoneId.systemDefault());
                                    String localUserStr = localUser.toString();
                                    
                                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm"); 
                                    localStartTime = LocalTime.parse(localStartStr.substring(11, 16), timeFormatter);
                                    LocalTime localUserTime = LocalTime.parse(localUserStr.substring(11, 16), timeFormatter);
                                    
                                    if (localStartTime.isAfter(localUserTime) && localStartTime.isBefore(localUserTime.plusMinutes(15))) {
                                        appAlert = true;
                                    }
                                }
                                if (appAlert == true) {
                                    ResultSet alertRS = conn.createStatement().executeQuery("SELECT type FROM appointment WHERE createdBy = '" + user + "' AND "
                                            + "start = '" + start + "'");
                                    alertRS.next(); 
                                    String type = alertRS.getString("type");
                                    
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Warning");
                                    alert.setHeaderText("Warning");
                                    alert.setContentText("You have an appointment for " + type + " at " + localStartTime);
                                    alert.showAndWait();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(rb.getString("signInFail"));
                            alert.setHeaderText(rb.getString("warning"));
                            alert.setContentText(rb.getString("passwordError"));
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(rb.getString("signInFail"));
                        alert.setHeaderText(rb.getString("warning"));
                        alert.setContentText(rb.getString("passwordError"));
                        alert.showAndWait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("signInFail"));
            alert.setHeaderText(rb.getString("warning"));
            alert.setContentText(rb.getString("signInError"));
            alert.showAndWait();
        }
    }
    
    public void OutputFile(String user, String ldtStr) throws IOException {
        try {
            FileWriter fwriter = new FileWriter("useractivity.txt", true);
            PrintWriter out = new PrintWriter(fwriter);
            out.println(user + "\t" + ldtStr);
            out.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
