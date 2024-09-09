/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime creationDateTime;
    private String creationUser;
    private ArrayList threeLastDigitsArrayList;

    private LinkedHashMap<String, Item> items;

    public EndoParalavis() {
        this.items = new LinkedHashMap<>();
        threeLastDigitsArrayList = new ArrayList();
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

    public ArrayList getThreeLastDigitsArrayList() {
        return threeLastDigitsArrayList;
    }

    public void setThreeLastDigitsArrayList(ArrayList threeLastDigitsArrayList) {
        this.threeLastDigitsArrayList = threeLastDigitsArrayList;
    }

    public void setNumberAsArrayList(String number) {

        String[] splittedNumber = number.split("/");
        for (int x = 0; x < splittedNumber.length; x++) {
            String numb = splittedNumber[x];
            if (numb.length() > 2) {
                String substr = numb.substring(numb.length() - 3);
                this.threeLastDigitsArrayList.add(substr);
            }
        }
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
