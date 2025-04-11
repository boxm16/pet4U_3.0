/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotDelivery;

import Delivery.DeliveryInvoice;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        LinkedHashMap<String, ArrayList<DeliveryInvoice>> duePurchaseOrders = new LinkedHashMap<>();

        String query = "SELECT "
                + dbSchema + ".OPOR.\"DocNum\",  "
                + dbSchema + ".OPOR.\"CardCode\", "
                + dbSchema + ".OPOR.\"CardName\", "
                + dbSchema + ".OPOR.\"DocDate\", "
                + dbSchema + ".OPOR.\"DocStatus\", " // Added DocStatus field
                + dbSchema + ".POR1.\"WhsCode\" "
                + "FROM  "
                + dbSchema + ".OPOR   "
                + "JOIN  "
                + dbSchema + ".POR1 ON "
                + dbSchema + ".OPOR.\"DocEntry\" = " + dbSchema + ".POR1.\"DocEntry\"";
        System.out.println("Query: " + query);

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String supplierName = resultSet.getString("CardName");
                if (duePurchaseOrders.containsKey(supplierName)) {
                    //do nothing
                } else {
                    duePurchaseOrders.put(supplierName, new ArrayList<>());
                }
                DeliveryInvoice purchaseOrderInvoice = new DeliveryInvoice();
                purchaseOrderInvoice.setSupplier(supplierName);
                purchaseOrderInvoice.setInvoiceId(resultSet.getString("DocNum"));
                purchaseOrderInvoice.setInsertionDate(resultSet.getString("DocDate"));
                ArrayList<DeliveryInvoice> deliveryInvoices = duePurchaseOrders.get(supplierName);
                deliveryInvoices.add(purchaseOrderInvoice);
                duePurchaseOrders.put(supplierName, deliveryInvoices);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return duePurchaseOrders;
    }

}
