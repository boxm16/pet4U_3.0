/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputOutput;

import DailySales.DailySale;

public class InputOutput {

    private double delivery;
    private DailySale dailySale;
    private double endoParalavi;

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
    
    
}
