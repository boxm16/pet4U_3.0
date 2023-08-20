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
public class SuppliersItem extends Item {

    private double eshopSales;
    private double shopsSupply;
    private int minimalStock;
    private String orderUnit;
    private int orderQuantity;

    public double getEshopSales() {
        return eshopSales;
    }

    public void setEshopSales(double eshopSales) {
        this.eshopSales = eshopSales;
    }

    public double getShopsSupply() {
        return shopsSupply;
    }

    public void setShopsSupply(double shopsSupply) {
        this.shopsSupply = shopsSupply;
    }

    public double getEshopSoldPieces() {

        if (this.eshopSales == 0) {
            return 0;
        }
        if (this.getWeightCoefficient() != 1) {
            try {
                return this.eshopSales / this.getWeightCoefficient();

            } catch (NumberFormatException ex) {
                System.out.println(ex);
            }
        }
        return this.eshopSales;
    }

    public double getShopsSuppliedPieces() {
        if (this.shopsSupply == 0) {
            return 0;
        }
        if (this.getWeightCoefficient() != 1) {
            try {
                return this.shopsSupply / this.getWeightCoefficient();

            } catch (NumberFormatException ex) {
                System.out.println(ex);
            }
        }
        return this.shopsSupply;
    }

    public double getTotalShippedPieces() {

        double totalShippedPieces = this.eshopSales + this.shopsSupply;
        if (totalShippedPieces == 0) {
            return 0;
        }
        if (this.getWeightCoefficient() != 1) {
            try {
                double d = totalShippedPieces / this.getWeightCoefficient();
                return d;
            } catch (NumberFormatException ex) {
                System.out.println(ex);
            }
        }
        return totalShippedPieces;
    }

    public int getTotalShippedPiecesForPeriod() {

        // 6 monthes X 4 evdomade = 24~25 evdomades
        Double tspForPeriod = (this.getTotalShippedPieces() / 26) * 2;
        return tspForPeriod.intValue();
    }

    // ----------------
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

}
