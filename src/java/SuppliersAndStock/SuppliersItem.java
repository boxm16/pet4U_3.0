/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import CamelotItemsOfInterest.CamelotItemOfInterest;

/**
 *
 * @author Michail Sitmalidis
 */
public class SuppliersItem extends CamelotItemOfInterest {

    private double eshopSales;
    private double shopsSupply;

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

    public double getTotalShippedPiecesFor__() {
        return getTotalShippedPieces() / 13;
    }
}
