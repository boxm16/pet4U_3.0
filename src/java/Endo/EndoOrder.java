/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class EndoOrder {

    private LocalDate date;
    private String destination;
    LinkedHashMap<String, EndoOrderItem> orderedItems;

    public EndoOrder() {
        this.orderedItems = new LinkedHashMap<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LinkedHashMap<String, EndoOrderItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(LinkedHashMap<String, EndoOrderItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void addOrderItem(String itemCode, EndoOrderItem orderedItem) {
        this.orderedItems.put(itemCode, orderedItem);
    }

}
