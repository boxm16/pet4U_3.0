/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;

/**
 *
 * @author Michail Sitmalidis
 */
public class EndoOrderItem extends Item {

    private String itemBarcode;
    double price;
    double amount;
    double orderedQuantity;
    double sentQuantity;
    String comment;
    String orderedAltercode;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(double orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public double getSentQuantity() {
        return sentQuantity;
    }

    public void setSentQuantity(double sentQuantity) {
        this.sentQuantity = sentQuantity;
    }

    public String getOrderedAltercode() {
        return orderedAltercode;
    }

    public void setOrderedAltercode(String orderedAltercode) {
        this.orderedAltercode = orderedAltercode;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

}
