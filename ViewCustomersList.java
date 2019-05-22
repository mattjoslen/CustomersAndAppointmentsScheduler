package software.ii.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewCustomersList {
    
    public final IntegerProperty custId;
    public final StringProperty custName;
    public final IntegerProperty addId;
    public final StringProperty address;
    public final StringProperty city;
    public final StringProperty country;
    public final StringProperty phone;

    public ViewCustomersList(int custId, String custName, int addId, String address, String city, String country, String phone) {
        this.custId = new SimpleIntegerProperty(custId);
        this.custName = new SimpleStringProperty(custName);
        this.addId = new SimpleIntegerProperty(addId);
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.country = new SimpleStringProperty(country);
        this.phone = new SimpleStringProperty(phone);
    }
    
    public IntegerProperty getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId.set(custId);
    }

    public StringProperty getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName.set(custName);
    }
    
    public IntegerProperty getAddId() {
        return addId;
    }

    public void setAddId(int addId) {
        this.addId.set(addId);
    }
    
    public StringProperty getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address.set(address);
    }
    
    public StringProperty getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city.set(city);
    }
    
    public StringProperty getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country.set(country);
    }
    
    public StringProperty getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}