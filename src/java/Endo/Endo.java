/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class Endo {

    private String id;
    private String dateString;
    private LocalDate date;
    private String type;
    private String sender;
    private String receiver;
    private LinkedHashMap<String, Item> items;

    public Endo() {

        this.items = new LinkedHashMap<>();
    }

    public Endo(String id, LocalDate date, String type, String sender, String receiver, LinkedHashMap<String, Item> items) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LinkedHashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(LinkedHashMap<String, Item> items) {
        this.items = items;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

}
