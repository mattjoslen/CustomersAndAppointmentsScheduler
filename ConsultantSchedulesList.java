package software.ii.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsultantSchedulesList {
    
    public final StringProperty consultant;
    public final IntegerProperty appId;
    public final IntegerProperty custId;
    public final StringProperty location;
    public final StringProperty start;
    public final StringProperty end;

    public ConsultantSchedulesList(String consultant, int appId, int custId, String location, String start, String end) {
        this.consultant = new SimpleStringProperty(consultant);
        this.appId = new SimpleIntegerProperty(appId);
        this.custId = new SimpleIntegerProperty(custId);
        this.location = new SimpleStringProperty(location);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
    }
    
    public StringProperty getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant.set(consultant);
    }

    public IntegerProperty getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId.set(appId);
    }
    
    public IntegerProperty getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId.set(custId);
    }
    
    public StringProperty getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location.set(location);
    }
    
    public StringProperty getStart() {
        return start;
    }
    
    public void setStart(String start) {
        this.start.set(start);
    }
    
    public StringProperty getEnd() {
        return end;
    }
    
    public void setEnd(String end) {
        this.end.set(end);
    }
}
