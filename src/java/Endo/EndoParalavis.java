/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class EndoParalavis {

    private String id;
    private String dateString;
    private String number;
    private LocalDate date;
    private ArrayList numberAsArrayList;

    private LinkedHashMap<String, Item> items;

    public EndoParalavis() {
        this.items = new LinkedHashMap<>();
        numberAsArrayList = new ArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LinkedHashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(LinkedHashMap<String, Item> items) {
        this.items = items;
    }

    public ArrayList getNumberAsArrayList() {
        return numberAsArrayList;
    }

    public void setNumberAsArrayList(String number) {
        String[] splittedNumber = number.split("/");
        ArrayList al = (ArrayList) Arrays.asList(splittedNumber);
        this.numberAsArrayList = al;
    }

}
