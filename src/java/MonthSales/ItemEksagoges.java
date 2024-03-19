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

    private TreeMap<LocalDate, EksagogesB> eksagoges;

    public ItemEksagoges() {
        this.eksagoges = new TreeMap();
    }

    public TreeMap<LocalDate, EksagogesB> getEksagoges() {
        return eksagoges;
    }

    public void setEksagoges(TreeMap<LocalDate, EksagogesB> eksagoges) {
        this.eksagoges = eksagoges;
    }

    void addSales(LocalDate date, EksagogesB eksagoges) {
        this.eksagoges.put(date, eksagoges);
    }

    public EksagogesB getEksagogesForLastMonths(int months) {

        EksagogesB eksagoges = new EksagogesB();
        int currentMonth = 1;
        for (Map.Entry<LocalDate, EksagogesB> entrySet : this.eksagoges.descendingMap().entrySet()) {
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
