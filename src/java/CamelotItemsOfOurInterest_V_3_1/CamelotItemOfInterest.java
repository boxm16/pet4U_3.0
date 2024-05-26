package CamelotItemsOfOurInterest_V_3_1;

import BasicModel.Item;

public class CamelotItemOfInterest extends Item {

    private String referralAltercode;

    private int minimalStock;
    private String orderUnit;
    private int orderQuantity;

    private String camelotPosition;
    private double pet4uStock;
    // private int weightCoefficient;
    private double camelotStock;
    private int camelotMinimalStock;
    private double camelotLast30DaysSales;

    private double lastSixMonthsSales;
    private double last30DaysSales;
    private String note;

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

    public String getCamelotPosition() {
        return camelotPosition;
    }

    public void setCamelotPosition(String camelotPosition) {
        this.camelotPosition = camelotPosition;
    }

    public double getPet4uStock() {
        return pet4uStock;
    }

    public void setPet4uStock(double pet4uStock) {
        this.pet4uStock = pet4uStock;
    }

    /*
    public int getWeightCoefficient() {
        return weightCoefficient;
    }

    public void setWeightCoefficient(int weightCoefficient) {
        this.weightCoefficient = weightCoefficient;
    }
     */

    public double getCamelotStock() {
        return camelotStock;
    }

    public void setCamelotStock(double camelotStock) {
        this.camelotStock = camelotStock;
    }

    public int getCamelotMinimalStock() {
        return camelotMinimalStock;
    }

    public void setCamelotMinimalStock(int camelotMinimalStock) {
        this.camelotMinimalStock = camelotMinimalStock;
    }

    public double getLastSixMonthsSales() {
        return lastSixMonthsSales;
    }

    public void setLastSixMonthsSales(double lastSixMonthsSales) {
        this.lastSixMonthsSales = lastSixMonthsSales;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReferralAltercode() {
        return referralAltercode;
    }

    public void setReferralAltercode(String referralAltercode) {
        this.referralAltercode = referralAltercode;
    }

    public double getLastSixMonthsSoldPieces() {

        return this.lastSixMonthsSales / super.getWeightCoefficient();
    }

    public double getLast30DaysSales() {
        return last30DaysSales;
    }

    public void setLast30DaysSales(double last30DaysSales) {
        this.last30DaysSales = last30DaysSales;
    }

    public double getCamelotLast30DaysSales() {
        return camelotLast30DaysSales;
    }

    public void setCamelotLast30DaysSales(double camelotLast30DaysSales) {
        this.camelotLast30DaysSales = camelotLast30DaysSales;
    }

}
