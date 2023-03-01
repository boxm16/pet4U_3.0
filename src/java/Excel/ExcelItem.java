/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import BasicModel.Item;

/**
 *
 * @author Michail Sitmalidis
 */
public class ExcelItem extends Item {

    private String altercode;
    private String altercodeStatus;

    public String getAltercode() {
        return altercode;
    }

    public void setAltercode(String altercode) {
        this.altercode = altercode;
    }

    public String getAltercodeStatus() {
        return altercodeStatus;
    }

    public void setAltercodeStatus(String altercodeStatus) {
        this.altercodeStatus = altercodeStatus;
    }

}
