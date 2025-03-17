package SAP.SapBasicModel;

import BasicModel.Item;

public class SapItem extends Item {

    private SapUnitOfMeasurementGroup unitOfMeasurementGroup;

    public SapUnitOfMeasurementGroup getUnitOfMeasurementGroup() {
        return unitOfMeasurementGroup;
    }

    public void setUnitOfMeasurementGroup(SapUnitOfMeasurementGroup unitOfMeasurementGroup) {
        this.unitOfMeasurementGroup = unitOfMeasurementGroup;
    }

}
