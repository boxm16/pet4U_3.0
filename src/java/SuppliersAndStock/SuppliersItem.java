/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import BasicModel.Item;

/**
 *
 * @author Michail Sitmalidis
 */
public class SuppliersItem extends Item{
   
    private int minimalStock;
    private String orderUnit;
    private int orderQuantity;

    private double pet4uStock;
   
    private double totalSalesInPieces;

    public int getMinimalStock() {
        return minimalStock;
    }

    public void setMinimalStock(int minimalStock) {
        this.minimalStock = minimalStock;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getPet4uStock() {
        return pet4uStock;
    }

    public void setPet4uStock(double pet4uStock) {
        this.pet4uStock = pet4uStock;
    }

    public double getTotalSalesInPieces() {
        return totalSalesInPieces;
    }

    public void setTotalSalesInPieces(double totalSalesInPieces) {
        this.totalSalesInPieces = totalSalesInPieces;
    }
 
    
}
