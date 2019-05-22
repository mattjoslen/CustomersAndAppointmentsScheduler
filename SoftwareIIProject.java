package software.ii.project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matt
 */
public class SoftwareIIProject extends Application {
    static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");
        
        Parent main = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInScreen.fxml"));
            loader.setResources(rb);
            main = loader.load();
            
            Scene scene = new Scene(main);
            
            stage.setScene(scene);
            
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        
        launch(args);
        
    }
    
    public static Stage getStage() {
        return stage;
    }
    
}
