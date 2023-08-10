package CamelotItemsOfInterest;

import BasicModel.Item;

public class CamelotItemOfInterest extends Item {

    private String owner;
    private int minimalStock;
    private String orderUnit;
    private int orderQuantity;

    private String camelotPosition;
    private double pet4uStock;
    private int weightCoefficient;
    private double camelotStock;
    private double camelotBinded;
    private int camelotMinimalStock;

    private double totalSalesInPieces;
    private String note;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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

    public void setPet4uStock(int pet4uStock) {
        this.pet4uStock = pet4uStock;
    }

    public void setCamelotStock(int camelotStock) {
        this.camelotStock = camelotStock;
    }

    public void setCamelotBinded(int camelotBinded) {
        this.camelotBinded = camelotBinded;
    }

    @Override
    public int getWeightCoefficient() {
        return weightCoefficient;
    }

    public void setWeightCoefficient(int weightCoefficient) {
        this.weightCoefficient = weightCoefficient;
    }

    public double getPet4uStock() {
        return pet4uStock;
    }

    public void setPet4uStock(double pet4uStock) {
        this.pet4uStock = pet4uStock;
    }

    public double getCamelotStock() {
        return camelotStock;
    }

    public void setCamelotStock(double camelotStock) {
        this.camelotStock = camelotStock;
    }

    public double getCamelotBinded() {
        return camelotBinded;
    }

    public void setCamelotBinded(double camelotBinded) {
        this.camelotBinded = camelotBinded;
    }

    public int getCamelotMinimalStock() {
        return camelotMinimalStock;
    }

    public void setCamelotMinimalStock(int camelotMinimalStock) {
        this.camelotMinimalStock = camelotMinimalStock;
    }

    public double getTotalSalesInPieces() {
        return totalSalesInPieces;
    }

    public void setTotalSalesInPieces(double totalSalesInPieces) {
        this.totalSalesInPieces = totalSalesInPieces;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    

}
