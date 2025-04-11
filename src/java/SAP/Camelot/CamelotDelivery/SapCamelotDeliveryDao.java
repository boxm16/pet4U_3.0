/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotDelivery;

import Delivery.DeliveryInvoice;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class SapCamelotDeliveryDao {

    private String dbSchema;

    public SapCamelotDeliveryDao() {
        this.dbSchema = "TRAINING_PC";
    }

    LinkedHashMap<String, ArrayList<DeliveryInvoice>> getDuePurchaseOrders() {
        LinkedHashMap<String, ArrayList<DeliveryInvoice>> getDuePurchaseOrders = new LinkedHashMap<>();
        String query = "SELECT "
                + dbSchema + ".\"DocNum\" AS PurchaseOrderNumber, "
                + dbSchema + ".OPOR.\"CardCode\", "
                + dbSchema + ".OPOR.\"CardName\", "
                + dbSchema + ".OPOR.\"DocDate\", "
                + dbSchema + ".POR1.\"ItemCode\", "
                + dbSchema + ".POR1.\"Dscription\", "
                + dbSchema + ".POR1.\"Quantity\", "
                + dbSchema + ".POR1.\"Price\", "
                + dbSchema + ".POR1.\"WhsCode\" "
                + "FROM  "
                + dbSchema + " OPOR   "
                + "JOIN  "
                + dbSchema + " POR1 ON "
                + dbSchema + " OPOR.\"DocEntry\" = " + dbSchema + ".POR1.\"DocEntry\"; ";
        System.out.println("Query: " + query);
        
        return getDuePurchaseOrders;
    }

}
