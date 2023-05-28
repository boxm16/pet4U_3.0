package Sales;

import BasicModel.Item;

public class SoldItem extends Item {

    private String measureUnit;
    private int eshopSales;
    private int shopsSupply;
    private int totalSales;
    private int coeficient;
    private int totalSalesInPieces;

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getEshopSales() {
        return eshopSales;
    }

    public void setEshopSales(int eshopSales) {
        this.eshopSales = eshopSales;
    }

    public int getShopsSupply() {
        return shopsSupply;
    }

    public void setShopsSupply(int shopsSupply) {
        this.shopsSupply = shopsSupply;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(int coeficient) {
        this.coeficient = coeficient;
    }

    public int getTotalSalesInPieces() {
        return totalSalesInPieces;
    }

    public void setTotalSalesInPieces(int totalSalesInPieces) {
        this.totalSalesInPieces = totalSalesInPieces;
    }

}
