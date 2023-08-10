package SalesX;

import BasicModel.Item;

public class SoldItem extends Item {

    private double eshopSales;
    private double shopsSupply;
    private double totalSales;

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

}
