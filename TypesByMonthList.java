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

public class TypesByMonthList {
    
    public final IntegerProperty janCount;
    public final IntegerProperty febCount;
    public final IntegerProperty marchCount;
    public final IntegerProperty aprilCount;
    public final IntegerProperty mayCount;
    public final IntegerProperty juneCount;
    public final IntegerProperty julyCount;
    public final IntegerProperty augCount;
    public final IntegerProperty sepCount;
    public final IntegerProperty octCount;
    public final IntegerProperty novCount;
    public final IntegerProperty decCount;

    public TypesByMonthList(int janCount, int febCount, int marchCount, int aprilCount, int mayCount, int juneCount, int julyCount, int augCount,
                int sepCount, int octCount, int novCount, int decCount) {
        this.janCount = new SimpleIntegerProperty(janCount);
        this.febCount = new SimpleIntegerProperty(febCount);
        this.marchCount = new SimpleIntegerProperty(marchCount);
        this.aprilCount = new SimpleIntegerProperty(aprilCount);
        this.mayCount = new SimpleIntegerProperty(mayCount);
        this.juneCount = new SimpleIntegerProperty(juneCount);
        this.julyCount = new SimpleIntegerProperty(julyCount);
        this.augCount = new SimpleIntegerProperty(augCount);
        this.sepCount = new SimpleIntegerProperty(sepCount);
        this.octCount = new SimpleIntegerProperty(octCount);
        this.novCount = new SimpleIntegerProperty(novCount);
        this.decCount = new SimpleIntegerProperty(decCount);
    }
    
    
    public IntegerProperty getJanuary() {
        return janCount;
    }
    
    public void setJanuary(int janCount) {
        this.janCount.set(janCount);
    }
    
    public IntegerProperty getFebruary() {
        return febCount;
    }
    
    public void setFebruary(int febCount) {
        this.febCount.set(febCount);
    }
    
    public IntegerProperty getMarch() {
        return marchCount;
    }
    
    public void setMarch(int marchCount) {
        this.marchCount.set(marchCount);
    }
    
    public IntegerProperty getApril() {
        return aprilCount;
    }
    
    public void setApril(int aprilCount) {
        this.aprilCount.set(aprilCount);
    }
    
    public IntegerProperty getMay() {
        return mayCount;
    }
    
    public void setMay(int mayCount) {
        this.mayCount.set(mayCount);
    }
    
    public IntegerProperty getJune() {
        return juneCount;
    }
    
    public void setJune(int juneCount) {
        this.juneCount.set(juneCount);
    }
    
    public IntegerProperty getJuly() {
        return julyCount;
    }
    
    public void setJuly(int julyCount) {
        this.julyCount.set(julyCount);
    }
    
    public IntegerProperty getAugust() {
        return augCount;
    }
    
    public void setAugust(int augCount) {
        this.augCount.set(augCount);
    }
    
    public IntegerProperty getSeptember() {
        return sepCount;
    }
    
    public void setSeptember(int sepCount) {
        this.sepCount.set(sepCount);
    }
    
    public IntegerProperty getOctober() {
        return octCount;
    }
    
    public void setOctober(int octCount) {
        this.octCount.set(octCount);
    }
    
    public IntegerProperty getNovember() {
        return novCount;
    }
    
    public void setNovember(int novCount) {
        this.novCount.set(novCount);
    }
    
    public IntegerProperty getDecember() {
        return decCount;
    }
    
    public void setDecember(int decCount) {
        this.decCount.set(decCount);
    }
}
