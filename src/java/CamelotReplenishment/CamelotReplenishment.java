/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotReplenishment;

import BasicModel.Item;
import java.time.LocalDateTime;

/**
 *
 * @author Michail Sitmalidis
 */
public class CamelotReplenishment extends Item {

    private String replenishmentUnit;
    private int itemsInReplenishmentUnit;
    private int replenishmentQuantity;
    private String note;
    private LocalDateTime dateTime;
    private int minimalShelfStock;
    private int sailsAfterReplenishment;
    private int endoSailsAfterReplenishment;

    public String getReplenishmentUnit() {
        return replenishmentUnit;
    }

    public void setReplenishmentUnit(String replenishmentUnit) {
        this.replenishmentUnit = replenishmentUnit;
    }

    public int getItemsInReplenishmentUnit() {
        return itemsInReplenishmentUnit;
    }

    public void setItemsInReplenishmentUnit(int itemsInReplenishmentUnit) {
        this.itemsInReplenishmentUnit = itemsInReplenishmentUnit;
    }

    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getMinimalShelfStock() {
        return minimalShelfStock;
    }

    public void setMinimalShelfStock(int minimalShelfStock) {
        this.minimalShelfStock = minimalShelfStock;
    }

    public int getSailsAfterReplenishment() {
        return sailsAfterReplenishment;
    }

    public void setSailsAfterReplenishment(int sailsAfterReplenishment) {
        this.sailsAfterReplenishment = sailsAfterReplenishment;
    }

    public int getEndoSailsAfterReplenishment() {
        return endoSailsAfterReplenishment;
    }

    public void setEndoSailsAfterReplenishment(int endoSailsAfterReplenishment) {
        this.endoSailsAfterReplenishment = endoSailsAfterReplenishment;
    }
    
    
    
}
