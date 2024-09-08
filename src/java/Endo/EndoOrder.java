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

    private String id;
    private LocalDate date;
    private String dateString;
    private String destination;
    private String note;

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
        if (this.orderedItems.containsKey(itemCode)) {
            double orderedQuantity_1 = this.orderedItems.get(itemCode).getOrderedQuantity();
            double orderedQuantity_2 = orderedItem.getOrderedQuantity();
            orderedItem.setOrderedQuantity(orderedQuantity_1 + orderedQuantity_2);

            String comment_1 = this.orderedItems.get(itemCode).getComment();
            String comment_2 = orderedItem.getComment();
            orderedItem.setComment(comment_1 + ":" + comment_2);

            double amount_1 = this.orderedItems.get(itemCode).getAmount();
            double amount_2 = orderedItem.getAmount();
            orderedItem.setAmount(amount_1 + amount_2);

        }
        this.orderedItems.put(itemCode, orderedItem);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

}
