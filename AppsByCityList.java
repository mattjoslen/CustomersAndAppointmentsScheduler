/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppsByCityList {
    
    public final StringProperty city;
    public final IntegerProperty appCount;

    public AppsByCityList(String city, int appCount) {
        this.city = new SimpleStringProperty(city);
        this.appCount = new SimpleIntegerProperty(appCount);
    }
    
    public StringProperty getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public IntegerProperty getAppCount() {
        return appCount;
    }

    public void setAppCount(int appCount) {
        this.appCount.set(appCount);
    }
}