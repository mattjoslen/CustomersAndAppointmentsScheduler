/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.project;

/**
 *
 * @author Matt
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppointmentList {
    
    public final IntegerProperty appID;
    public final IntegerProperty custID;
    public final StringProperty start;
    public final StringProperty end;
    public final StringProperty type;

    public AppointmentList(int appID, int custID, String start, String end, String type) {
        this.appID = new SimpleIntegerProperty(appID);
        this.custID = new SimpleIntegerProperty(custID);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
        this.type = new SimpleStringProperty(type);
    }
    
    public IntegerProperty getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID.set(appID);
    }

    public IntegerProperty getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID.set(custID);
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
    
    public StringProperty getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type.set(type);
    }
}
