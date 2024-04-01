/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import BasicModel.Item;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class InventoryItem extends Item {

    private int id;
    private String dateStampString;
    private Date dateStamp;
    private String timeStampString;
    private Date timeStamp;
    private String systemStock;
    private String realStock;
    private String note;
    private String inventarizationState;
    private LinkedHashMap<Integer, String> stockPositions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeStampString() {
        return timeStampString;
    }

    public void setTimeStampString(String timeStampString) {
        this.timeStampString = timeStampString;
    }

    public String getDateStampString() {
        return dateStampString;
    }

    public void setDateStampString(String dateStampString) {
        this.dateStampString = dateStampString;
    }

    public Date getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(Date dateStamp) {
        this.dateStamp = dateStamp;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSystemStock() {
        return systemStock;
    }

    public void setSystemStock(String systemStock) {
        this.systemStock = systemStock;
    }

    public String getRealStock() {
        return realStock;
    }

    public void setRealStock(String realStock) {
        this.realStock = realStock;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInventarizationState() {
        return inventarizationState;
    }

    public void setInventarizationState(String inventarizationState) {
        this.inventarizationState = inventarizationState;
    }

    public LinkedHashMap<Integer, String> getStockPositions() {
        return stockPositions;
    }

    public void setStockPositions(LinkedHashMap<Integer, String> stockPositions) {
        this.stockPositions = stockPositions;
    }

}
