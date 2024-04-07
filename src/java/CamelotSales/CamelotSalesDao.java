/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotSales;

import SalesX.SoldItem;
import java.util.ArrayList;

/**
 *
 * @author Michail Sitmalidis
 */
public class CamelotSalesDao {

    String insertNewUpload(String date, ArrayList<SoldItem> soldItems) {
        for (SoldItem soldItem : soldItems) {
            System.out.println(soldItem.getCode() + "-" + soldItem.getEshopSales());
        }
        return "done";
    }

}
