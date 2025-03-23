package SAP.SapBasicModel;

import BasicModel.Item;

public class SapItem extends Item {

    private SapUnitOfMeasurementGroup unitOfMeasurementGroup;
    private Integer itemsGroupCode;

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

}
