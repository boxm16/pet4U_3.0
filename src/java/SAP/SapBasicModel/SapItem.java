package SAP.SapBasicModel;

import BasicModel.Item;

public class SapItem extends Item {

    private SapUnitOfMeasurementGroup unitOfMeasurementGroup;
    private Integer itemsGroupCode;
    private boolean food;
    private boolean accessory;

    public SapUnitOfMeasurementGroup getUnitOfMeasurementGroup() {
        return unitOfMeasurementGroup;
    }

    public void setUnitOfMeasurementGroup(SapUnitOfMeasurementGroup unitOfMeasurementGroup) {
        this.unitOfMeasurementGroup = unitOfMeasurementGroup;
    }

    public Integer getItemsGroupCode() {
        return itemsGroupCode;
    }

    public void setItemsGroupCode(Integer itemsGroupCode) {
        this.itemsGroupCode = itemsGroupCode;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isAccessory() {
        return accessory;
    }

    public void setAccessory(boolean accessory) {
        this.accessory = accessory;
    }

}
