package Delivery;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryDao_V_3_1 {

    LinkedHashMap<String, DeliveryInvoice> getDeliveryInvoices(String date) {
        LinkedHashMap<String, DeliveryInvoice> deliveryInvoices = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DATEOFUPDATE],  [SUPPLIER] FROM  [petworld].[dbo].[WH_DEPA]  WHERE  [DATEOFUPDATE] = '" + date + "' ORDER BY [DOCID];";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String id = resultSet.getString("DOCID");

                //  String date = resultSet.getString("DOCDATE");
                // String[] splittedDate = date.split(" ");
                //date = splittedDate[0];
                String number = resultSet.getString("DOCNUMBER");

                String supplier = resultSet.getString("SUPPLIER");

                DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
                deliveryInvoice.setId(id);
                deliveryInvoice.setInsertionDate(date);
                deliveryInvoice.setSupplier(supplier);
                deliveryInvoice.setNumber(number);

                deliveryInvoices.put(id, deliveryInvoice);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deliveryInvoices;
    }

    DeliveryInvoice getDeliveryInvoice(String id) {
        String sql = "SELECT  [DOCID], [DOCNUMBER],  [DATEOFUPDATE],  [SUPPLIER], [ABBREVIATION], [NAME], [QUANT1]   FROM [petworld].[dbo].[WH_DEPA] WHERE [DOCID]='" + id + "' ;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        deliveryInvoice.setId(id);
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String date = resultSet.getString("DATEOFUPDATE");
                String[] splittedDate = date.split(" ");
                date = splittedDate[0];

                String number = resultSet.getString("DOCNUMBER");
                String supplier = resultSet.getString("SUPPLIER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");

                deliveryInvoice.setInsertionDate(date);
                deliveryInvoice.setNumber(number);
                deliveryInvoice.setSupplier(supplier);

                LinkedHashMap<String, DeliveryItem> items = deliveryInvoice.getItems();
                if (items.containsKey(itemCode)) {
                    DeliveryItem item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));
                    deliveryInvoice.getItems().put(itemCode, item);
                } else {
                    DeliveryItem item = new DeliveryItem();
                    item.setCode(itemCode);
                    item.setQuantity(String.valueOf(Double.valueOf(quantity)));
                    item.setDeliveredQuantity("0");
                    item.setDescription(description);
                    deliveryInvoice.getItems().put(itemCode, item);
                }

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao_V_3_1.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deliveryInvoice;
    }

    boolean deliveryInvocieIsChecked(String invoiceId) {

        String sql = " SELECT COUNT(*) count FROM pet4u_db.delivery_title WHERE id='" + invoiceId + "';";
        ResultSet resultSet;
        int count = 0;
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao_V_3_1.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (count == 0) {
            return false;
        }
        return true;
    }

}
