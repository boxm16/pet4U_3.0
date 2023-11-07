/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonthSales;

import BasicModel.Item;
import java.time.LocalDate;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class ItemEksagoges extends Item {

    private TreeMap<LocalDate, Eksagoges> eksagoges;

    public TreeMap<LocalDate, Eksagoges> getEksagoges() {
        return eksagoges;
    }

    public void setEksagoges(TreeMap<LocalDate, Eksagoges> eksagoges) {
        this.eksagoges = eksagoges;
    }

    void addSales(LocalDate saleDate, Eksagoges eksagoges) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
