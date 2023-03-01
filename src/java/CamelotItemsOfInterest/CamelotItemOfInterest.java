package CamelotItemsOfInterest;

import BasicModel.Item;

public class CamelotItemOfInterest extends Item {

    private String owner;
    private int minimalStock;
    private String orderUnit;
    private int orderQuantity;
    private int orderTotalItems;
    private String camelotPosition;
    private int pet4uStock;
    private int weightCoefficient;
    private int camelotStock;
    private int camelotBinded;

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

    public int getOrderTotalItems() {
        return orderTotalItems;
    }

    public void setOrderTotalItems(int orderTotalItems) {
        this.orderTotalItems = orderTotalItems;
    }

    public String getCamelotPosition() {
        return camelotPosition;
    }

    public void setCamelotPosition(String camelotPosition) {
        this.camelotPosition = camelotPosition;
    }

    public int getPet4uStock() {
        return pet4uStock;
    }

    public void setPet4uStock(int pet4uStock) {
        this.pet4uStock = pet4uStock;
    }

    public int getCamelotStock() {
        return camelotStock;
    }

    public void setCamelotStock(int camelotStock) {
        this.camelotStock = camelotStock;
    }

    public int getCamelotBinded() {
        return camelotBinded;
    }

    public void setCamelotBinded(int camelotBinded) {
        this.camelotBinded = camelotBinded;
    }

    public int getWeightCoefficient() {
        return weightCoefficient;
    }

    public void setWeightCoefficient(int weightCoefficient) {
        this.weightCoefficient = weightCoefficient;
    }

}
