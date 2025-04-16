/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import BasicModel.Item;
import java.math.BigDecimal;

public class DeliveryItem extends Item {

    private String altercode;
    private String sentQuantity;
    private String deliveredQuantity;
    private int baseLine;
    private double packQuantity;

    private BigDecimal price;

    public String getAltercode() {
        return altercode;
    }

    public void setAltercode(String altercode) {
        this.altercode = altercode;
    }

    public String getSentQuantity() {
        return sentQuantity;
    }

    public void setSentQuantity(String sentQuantity) {
        this.sentQuantity = sentQuantity;
    }

    public String getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(String deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(int baseLine) {
        this.baseLine = baseLine;
    }

    public double getPackQuantity() {
        return packQuantity;
    }

    public void setPackQuantity(double packQuantity) {
        this.packQuantity = packQuantity;
    }

}
