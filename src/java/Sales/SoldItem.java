package Sales;

import BasicModel.Item;

public class SoldItem extends Item {

    private String measureUnit;
    private int eshopSales;
    private int shopsSupply;
    private int totalSalesX;
    private int coeficient;
    private int totalSalesInPieces;
    private int tts;

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
        return totalSalesX;
    }

    public void setTotalSales(int totalSales) {
        this.totalSalesX = totalSales;
        System.out.println("///////////////" + this.totalSalesX);

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

    public String getShopsSupplyPieces() {
        if (this.shopsSupply == 0) {
            return "0";
        }
        if (this.getWeightCoefficient() != 1) {

            try {

                double d = this.shopsSupply / this.getWeightCoefficient();
                String dS = Double.toString(d);
                return dS;
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                return "N/A";
            }
        }
        return "" + this.shopsSupply;
    }

    public String getEshopSalesPieces() {
        if (this.eshopSales == 0) {
            return "0";
        }
        if (this.getWeightCoefficient() != 1) {

            try {

                double d = this.eshopSales / this.getWeightCoefficient();
                String dS = Double.toString(d);
                return dS;
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                return "N/A";
            }
        }
        return "" + this.eshopSales;
    }

    public String getTotalSalesInPiecesString() {
        System.out.println("----------X------------" + this.totalSalesX);
        if (this.totalSalesX == 0) {
            return "0";
        }
        if (this.getWeightCoefficient() != 1) {

            try {

                double d = this.totalSalesX / this.getWeightCoefficient();
                String dS = Double.toString(d);
                return dS;
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                return "N/A";
            }
        }
        return "" + this.totalSalesX;
    }

    public int getTts() {
        return tts;
    }

    public void setTts(int tts) {
        this.tts = tts;
    }

    public String getTtsPieces() {

        int yy = this.eshopSales + this.shopsSupply;
        if (yy == 0) {
            return "0";
        }
        if (this.getWeightCoefficient() != 1) {

            try {

                double d = yy / this.getWeightCoefficient();
                String dS = Double.toString(d);
                return dS;
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                return "N/A";
            }
        }
        return "" + yy;
    }

}
