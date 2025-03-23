package SAP.SapBasicModel;

import BasicModel.Item;

public class SapItem extends Item {

    private SapUnitOfMeasurementGroup unitOfMeasurementGroup;
    private Integer itemsGroupCode;

    public SapItem() {
        this.unitOfMeasurementGroup = new SapUnitOfMeasurementGroup();
    }

    public SapUnitOfMeasurementGroup getUnitOfMeasurementGroup() {
        return unitOfMeasurementGroup;
    }

    public void setUnitOfMeasurementGroup(SapUnitOfMeasurementGroup unitOfMeasurementGroup) {
        this.unitOfMeasurementGroup = unitOfMeasurementGroup;
    }

    public int getItemsGroupCode() {
        return itemsGroupCode;
    }

    public void setItemsGroupCode(int itemsGroupCode) {
        this.itemsGroupCode = itemsGroupCode;
    }
    

}
