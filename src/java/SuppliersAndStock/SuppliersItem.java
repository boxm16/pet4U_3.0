/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import MonthSales.ItemEksagoges;
import java.time.LocalDate;

/**
 *
 * @author Michail Sitmalidis
 */
public class SuppliersItem extends ItemEksagoges {

    private int supplierId;
    private int orderHorizon;
    private double objectiveSales;
    private LocalDate objectiveSalesExpirationDate;
    private int minimalStockHorizon;
    private String orderUnit;
    private int orderUnitCapacity;
    private int orderQuantity;
    private String note;

    // ----------------

    public int getMinimalStockHorizon() {
        return minimalStockHorizon;
    }

    public void setMinimalStockHorizon(int minimalStockHorizon) {
        this.minimalStockHorizon = minimalStockHorizon;
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

    public int getOrderUnitCapacity() {
        return orderUnitCapacity;
    }

    public void setOrderUnitCapacity(int orderUnitCapacity) {
        this.orderUnitCapacity = orderUnitCapacity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public double getObjectiveSales() {
        return objectiveSales;
    }

    public void setObjectiveSales(double objectiveSales) {
        this.objectiveSales = objectiveSales;
    }

    public LocalDate getObjectiveSalesExpirationDate() {
        return objectiveSalesExpirationDate;
    }

    public void setObjectiveSalesExpirationDate(LocalDate objectiveSalesExpirationDate) {
        this.objectiveSalesExpirationDate = objectiveSalesExpirationDate;
    }

    public int getOrderHorizon() {
        return orderHorizon;
    }

    public void setOrderHorizon(int orderHorizon) {
        this.orderHorizon = orderHorizon;
    }

}
