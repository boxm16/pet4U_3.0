/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryDao {

    public ArrayList<DeliveryItem> getPet4UItemsRowByRow() {
        ArrayList<DeliveryItem> items = new ArrayList();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                DeliveryItem item = new DeliveryItem();
                item.setCode(resultSet.getString("ABBREVIATION").trim());
                String description = resultSet.getString("NAME").trim();

                description = description.replace("\"", "'");//replaces all occurrences of ' `  
                item.setDescription(description);

                if (resultSet.getString("EXPR1") != null) {
                    item.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    item.setPosition("");
                }
                item.setAltercode(altercode);
                items.add(item);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    //not good architecture here in thise method, but, it works :D :D 
    String saveDeliveryChecking(String number, ArrayList<DeliveryItem> deliveryItems) {

        LocalDateTime idItem = LocalDateTime.now();
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement invoiceInsertionPreparedStatement = connection.prepareStatement("INSERT INTO delivery_title (invoice_id, id, number,supplier, note) VALUES(?,?,?,?,?);");
            PreparedStatement deliveredItemsInPreparedStatement = connection.prepareStatement("INSERT INTO delivery_data (delivery_id, item_code, sent,delivered) VALUES (?,?,?,?);");

            System.out.println("Starting INSERTION: ....");
            
            invoiceInsertionPreparedStatement.setString(1, idItem.toString());
            invoiceInsertionPreparedStatement.setString(2, idItem.toString());
            invoiceInsertionPreparedStatement.setString(3, number);
            invoiceInsertionPreparedStatement.setString(4, "ROYAL");
            invoiceInsertionPreparedStatement.setString(5, " ");

            invoiceInsertionPreparedStatement.addBatch();

            for (DeliveryItem deliveryItem : deliveryItems) {

                deliveredItemsInPreparedStatement.setString(1, idItem.toString());
                deliveredItemsInPreparedStatement.setString(2, deliveryItem.getCode());
                deliveredItemsInPreparedStatement.setString(3, deliveryItem.getSentQuantity());
                deliveredItemsInPreparedStatement.setString(4, deliveryItem.getDeliveredQuantity());

                deliveredItemsInPreparedStatement.addBatch();

            }

            //Executing the batch
            invoiceInsertionPreparedStatement.executeBatch();
            deliveredItemsInPreparedStatement.executeBatch();

            System.out.println(" Batch Insertion: DONE");

            //Saving the changes
            connection.commit();
            //  deleteTripPeriodPreparedStatement.close();
            // deleteTripVoucherPreparedStatement.close();
            invoiceInsertionPreparedStatement.close();
            deliveredItemsInPreparedStatement.close();
            connection.close();
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }
    }

    public ArrayList<DeliveryInvoice> getAllCheckedDeliveryInvoices() {
        ArrayList<DeliveryInvoice> deliveryInvoices = new ArrayList<>();

        String sql = "SELECT * FROM delivery_title ;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
                deliveryInvoice.setId(resultSet.getString("id"));
                deliveryInvoice.setNumber(resultSet.getString("number"));

                deliveryInvoices.add(deliveryInvoice);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deliveryInvoices;
    }

    public DeliveryInvoice getDeliveryInvoice(String number) {
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();

        String sql = "SELECT * FROM delivery_title "
                + "INNER JOIN delivery_data ON delivery_title.id=delivery_data.delivery_id "
                + "WHERE number=" + number;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            int x = 0;
            LinkedHashMap<String, DeliveryItem> deliveryItems = new LinkedHashMap<>();
            while (resultSet.next()) {
                if (x == 0) {
                    deliveryInvoice.setId(resultSet.getString("id"));
                    deliveryInvoice.setNumber(resultSet.getString("number"));

                }
                DeliveryItem deliveryItem = new DeliveryItem();
                deliveryItem.setCode(resultSet.getString("item_code"));
                deliveryItem.setQuantity(resultSet.getString("sent"));
                deliveryItem.setDeliveredQuantity(resultSet.getString("delivered"));
                deliveryItems.put(resultSet.getString("item_code"), deliveryItem);
                x++;

            }
            deliveryInvoice.setItems(deliveryItems);
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deliveryInvoice;
    }

    String deleteDeliveryChecking(String invoiceId) {

        String sql = "DELETE  FROM delivery_data WHERE delivery_id='" + invoiceId + "';";

        String sql1 = "DELETE  FROM delivery_title "
                + "WHERE id='" + invoiceId + "';";

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
            statement.executeUpdate(sql1);

            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Delivery Checking with id" + invoiceId + "deleted";
    }
}
