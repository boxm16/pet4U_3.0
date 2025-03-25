/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.SapBasicModel;

import java.util.ArrayList;

public class SapUnitOfMeasurement {

    private int UomEntry;
    private String UomCode;
    private String UomName;
    private ArrayList<SapAltercodeContainer> altercodeContainers;
    private double BaseQuantity;
    private boolean locked;
    private String dataSource;

    public SapUnitOfMeasurement() {
        this.altercodeContainers = new ArrayList<>();
    }

    public int getUomEntry() {
        return UomEntry;
    }

    public void setUomEntry(int UomEntry) {
        this.UomEntry = UomEntry;
    }

    public String getUomCode() {
        return UomCode;
    }

    public void setUomCode(String UomCode) {
        this.UomCode = UomCode;
    }

    public String getUomName() {
        return UomName;
    }

    public void setUomName(String UomName) {
        this.UomName = UomName;
    }

    public ArrayList<SapAltercodeContainer> getAltercodeContainers() {
        return altercodeContainers;
    }

    public void setAltercodeContainers(ArrayList<SapAltercodeContainer> altercodeContainers) {
        this.altercodeContainers = altercodeContainers;
    }

    public double getBaseQuantity() {
        return BaseQuantity;
    }

    public void setBaseQuantity(double BaseQuantity) {
        this.BaseQuantity = BaseQuantity;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    
    

}
