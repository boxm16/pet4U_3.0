package Delivery;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
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

                String invoiceId = resultSet.getString("DOCID");
                String number = resultSet.getString("DOCNUMBER");
                String supplier = resultSet.getString("SUPPLIER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");

                deliveryInvoice.setInvoiceId(invoiceId);
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

        String sql = " SELECT COUNT(*) count FROM pet4u_db.delivery_title WHERE invoice_id='" + invoiceId + "';";
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

    public String saveDeliveryChecking(String invoiceId, String supplier, String invoiceNumber, ArrayList<DeliveryItem> deliveryItems) {
        LocalDateTime idItem = LocalDateTime.now();
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement invoiceInsertionPreparedStatement = connection.prepareStatement("INSERT INTO delivery_title (invoice_id, id, number,supplier, note) VALUES(?,?,?,?,?);");
            PreparedStatement deliveredItemsInPreparedStatement = connection.prepareStatement("INSERT INTO delivery_data (delivery_id, item_code, sent,delivered) VALUES (?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            invoiceInsertionPreparedStatement.setString(1, invoiceId);
            invoiceInsertionPreparedStatement.setString(2, idItem.toString());
            invoiceInsertionPreparedStatement.setString(3, invoiceNumber);
            invoiceInsertionPreparedStatement.setString(4, supplier);
            invoiceInsertionPreparedStatement.setString(5, " ");

            invoiceInsertionPreparedStatement.addBatch();

            for (DeliveryItem deliveryItem : deliveryItems) {

                deliveredItemsInPreparedStatement.setString(1, invoiceId);
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
                deliveryInvoice.setInvoiceId(resultSet.getString("invoice_id"));
                deliveryInvoice.setId(resultSet.getString("id"));
                deliveryInvoice.setSupplier(resultSet.getString("supplier"));
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

    DeliveryInvoice getDeliveryInvoiceByInvoiceId(String invoiceId) {
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();

        String sql = "SELECT * FROM delivery_title "
                + "INNER JOIN delivery_data ON delivery_title.invoice_id=delivery_data.delivery_id "
                + "WHERE invoice_id='" + invoiceId + "';";
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
                    deliveryInvoice.setInvoiceId(resultSet.getString("invoice_id"));
                    deliveryInvoice.setId(resultSet.getString("id"));
                    deliveryInvoice.setSupplier(resultSet.getString("supplier"));
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
                + "WHERE invoice_id='" + invoiceId + "';";

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

    LinkedHashMap<String, Item> getAllActiveItems() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1  ORDER BY EXPR1;");

            while (resultSet.next()) {
                String disabled = resultSet.getString("DISABLED");
                if (disabled == null) {
                } else {
                    if (disabled.equals("1")) {
                        continue;
                    }
                }
                //diklida asfalias
                if (resultSet.getString("QTYBALANCE") == null) {
                    continue;
                }

                String code = resultSet.getString("ABBREVIATION").trim();
                Item item = null;
                if (!items.containsKey(code)) {
                    item = new Item();
                    item.setCode(resultSet.getString("ABBREVIATION").trim());
                    item.setDescription(resultSet.getString("NAME").trim());
                    String position = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position = resultSet.getString("EXPR1").trim();
                    }
                    item.setPosition(position);
                    item.setQuantity(resultSet.getString("QTYBALANCE"));
                    String state = "";
                    if (resultSet.getString("EXPR2") != null) {
                        state = resultSet.getString("EXPR2").trim();
                    }
                    item.setState(state);
                    items.put(code, item);
                }
                AltercodeContainer altercodeContainer = new AltercodeContainer();
                altercodeContainer.setAltercode(resultSet.getString("ALTERNATECODE").trim());
                if (resultSet.getString("CODEDESCRIPTION") == null) {
                    altercodeContainer.setStatus("");
                } else {
                    altercodeContainer.setStatus(resultSet.getString("CODEDESCRIPTION").trim());
                }
                items.get(code).addAltercodeContainer(altercodeContainer);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao_V_3_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    public LinkedHashMap<String, Item> getPet4UItemsRowByRow() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                Item item = new Item();
                item.setCode(resultSet.getString("ABBREVIATION").trim());
                String description = resultSet.getString("NAME").trim();
                description = description.replace("\"", "'");//replaces all occurrences of ' `  
                item.setDescription(description);

                if (resultSet.getString("EXPR1") != null) {
                    item.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    item.setPosition("");
                }
                item.setQuantity(resultSet.getString("QTYBALANCE"));
                String state = "";
                if (resultSet.getString("EXPR2") != null) {
                    state = resultSet.getString("EXPR2").trim();
                }
                item.setState(state);
                items.put(altercode, item);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao_V_3_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    ////------SAP VERSION---------
    public String saveSAPDeliveryChecking(String invoiceId, String supplier, String invoiceNumber, ArrayList<DeliveryItem> deliveryItems) {
        LocalDateTime idItem = LocalDateTime.now();
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement invoiceInsertionPreparedStatement = connection.prepareStatement("INSERT INTO delivery_title (invoice_id, id, number,supplier, note) VALUES(?,?,?,?,?);");
            PreparedStatement deliveredItemsInPreparedStatement = connection.prepareStatement("INSERT INTO delivery_data (delivery_id, item_code, sent, delivered, baseLine) VALUES (?,?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            invoiceInsertionPreparedStatement.setString(1, invoiceId);
            invoiceInsertionPreparedStatement.setString(2, idItem.toString());
            invoiceInsertionPreparedStatement.setString(3, invoiceNumber);
            invoiceInsertionPreparedStatement.setString(4, supplier);
            invoiceInsertionPreparedStatement.setString(5, " ");

            invoiceInsertionPreparedStatement.addBatch();

            for (DeliveryItem deliveryItem : deliveryItems) {

                deliveredItemsInPreparedStatement.setString(1, invoiceId);
                deliveredItemsInPreparedStatement.setString(2, deliveryItem.getCode());
                deliveredItemsInPreparedStatement.setString(3, deliveryItem.getSentQuantity());
                deliveredItemsInPreparedStatement.setString(4, deliveryItem.getDeliveredQuantity());
                deliveredItemsInPreparedStatement.setInt(4, deliveryItem.getBaseLine());

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

}
