/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DailySales;

import BasicModel.Item;
import java.time.LocalDate;

/**
 *
 * @author Michail Sitmalidis
 */
public class DailySale extends Item {

  
    LocalDate dateTime;
    private double soldQuantiy;
    private double presoldQuantiy;

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public double getSoldQuantiy() {
        return soldQuantiy;
    }

    public void setSoldQuantiy(double soldQuantiy) {
        this.soldQuantiy = soldQuantiy;
    }

    public double getPresoldQuantiy() {
        return presoldQuantiy;
    }

    public void setPresoldQuantiy(double presoldQuantiy) {
        this.presoldQuantiy = presoldQuantiy;
    }

   
    
    

}
