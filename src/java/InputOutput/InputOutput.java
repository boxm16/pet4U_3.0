/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputOutput;

import CamelotItemsOfInterest.ItemSnapshot;
import DailySales.DailySale;

public class InputOutput {

    private double delivery;
    private DailySale dailySale;
    private double endoParalavi;
    private double camelotParalavi;
    private double endoApostoli;
    private ItemSnapshot itemSnapshot;

    public InputOutput() {
        dailySale = new DailySale();
    }

    public DailySale getDailySale() {
        return dailySale;
    }

    public void setDailySale(DailySale dailySale) {
        this.dailySale = dailySale;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public double getEndoParalavi() {
        return endoParalavi;
    }

    public void setEndoParalavi(double endoParalavi) {
        this.endoParalavi = endoParalavi;
    }

    public double getEndoApostoli() {
        return endoApostoli;
    }

    public void setEndoApostoli(double endoApostoli) {
        this.endoApostoli = endoApostoli;
    }

    public ItemSnapshot getItemSnapshot() {
        return itemSnapshot;
    }

    public void setItemSnapshot(ItemSnapshot itemSnapshot) {
        this.itemSnapshot = itemSnapshot;
    }

    public double getCamelotParalavi() {
        return camelotParalavi;
    }

    public void setCamelotParalavi(double camelotParalavi) {
        this.camelotParalavi = camelotParalavi;
    }
    

}
