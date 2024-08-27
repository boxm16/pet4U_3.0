/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Replenishment;

import BasicModel.Item;

/**
 *
 * @author Michail Sitmalidis
 */
public class Replenishment extends Item{
    
    private int replenishmentQuantity;

    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }
    
    
}
