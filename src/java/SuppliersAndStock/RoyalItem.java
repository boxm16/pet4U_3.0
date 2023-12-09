/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import MonthSales.ItemEksagoges;

/**
 *
 * @author Michail Sitmalidis
 */
public class RoyalItem extends ItemEksagoges{
    private int offLineStock;
    private int onLineStock;
    private int maximalStock;
    private String note;

    public int getOffLineStock() {
        return offLineStock;
    }

    public void setOffLineStock(int offLineStock) {
        this.offLineStock = offLineStock;
    }

    public int getOnLineStock() {
        return onLineStock;
    }

    public void setOnLineStock(int onLineStock) {
        this.onLineStock = onLineStock;
    }

    public int getMaximalStock() {
        return maximalStock;
    }

    public void setMaximalStock(int maximalStock) {
        this.maximalStock = maximalStock;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

  
    
    
}
