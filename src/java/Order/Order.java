/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import BasicModel.Item;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class Order {

    private int id;
    private String number;
    private LocalDateTime dateTimeStamp;
    private LocalDateTime creationDateTime;
    private String creationUser;
    private LinkedHashMap<String, Item> items;

    public Order() {
        items = new LinkedHashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(LocalDateTime dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public LinkedHashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(LinkedHashMap<String, Item> items) {
        this.items = items;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }
    
    

}
