package SAP.SapBasicModel;

import org.apache.commons.collections4.map.LinkedMap;

public class SapUnitOfMeasurementGroup {

    private int UgpEntry;
    private String UgpCode;
    private String UgpName;
    private LinkedMap<String, SapUnitOfMeasurement> unitOfMeasurements;

    public SapUnitOfMeasurementGroup() {
        this.unitOfMeasurements = new LinkedMap<>();
    }

    public int getUgpEntry() {
        return UgpEntry;
    }

    public void setUgpEntry(int UgpEntry) {
        this.UgpEntry = UgpEntry;
    }

    public String getUgpCode() {
        return UgpCode;
    }

    public void setUgpCode(String UgpCode) {
        this.UgpCode = UgpCode;
    }

    public String getUgpName() {
        return UgpName;
    }

    public void setUgpName(String UgpName) {
        this.UgpName = UgpName;
    }

    public LinkedMap<String, SapUnitOfMeasurement> getUnitOfMeasurements() {
        return unitOfMeasurements;
    }

    public void setUnitOfMeasurements(LinkedMap<String, SapUnitOfMeasurement> unitOfMeasurements) {
        this.unitOfMeasurements = unitOfMeasurements;
    }

}
