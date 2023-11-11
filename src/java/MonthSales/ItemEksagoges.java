/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonthSales;

import BasicModel.Item;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class ItemEksagoges extends Item {

    private TreeMap<LocalDate, Eksagoges> eksagoges;

    public ItemEksagoges() {
        this.eksagoges = new TreeMap();
    }

    public TreeMap<LocalDate, Eksagoges> getEksagoges() {
        return eksagoges;
    }

    public void setEksagoges(TreeMap<LocalDate, Eksagoges> eksagoges) {
        this.eksagoges = eksagoges;
    }

    void addSales(LocalDate date, Eksagoges eksagoges) {
        this.eksagoges.put(date, eksagoges);
    }

    public Eksagoges getEksagogesForLastMonths(int months) {

        Eksagoges eksagoges = new Eksagoges();
        int currentMonth = 1;
        for (Map.Entry<LocalDate, Eksagoges> entrySet : this.eksagoges.descendingMap().entrySet()) {
            //      System.out.println("MONTH:" + currentMonth);
            if (currentMonth > months) {
                break;
            }
            double eshopSales = eksagoges.getEshopSales();
            eshopSales += entrySet.getValue().getEshopSales();
            eksagoges.setEshopSales(eshopSales);

            double shopsSupply = eksagoges.getShopsSupply();
            shopsSupply += entrySet.getValue().getShopsSupply();
            eksagoges.setShopsSupply(shopsSupply);
          

            currentMonth++;
        }

        if (this.getWeightCoefficient() != 1) {
            try {
                double eshopSales = eksagoges.getEshopSales();
                double d = eshopSales / this.getWeightCoefficient();
                eksagoges.setEshopSales(d);

                double shopsSupply = eksagoges.getShopsSupply();
                double ssd = shopsSupply / this.getWeightCoefficient();
                eksagoges.setShopsSupply(ssd);
            } catch (NumberFormatException ex) {
                System.out.println(ex);

            }
        }
        return eksagoges;
    }

}
