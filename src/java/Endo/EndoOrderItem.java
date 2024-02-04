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

    double price;
    double amount;
    double quantityD;
    String comment;

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

    public double getQuantityD() {
        return quantityD;
    }

    public void setQuantityD(double quantityD) {
        this.quantityD = quantityD;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
