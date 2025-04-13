/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotDelivery;

import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        //   this.dbSchema = "TRAINING_PC";
        this.dbSchema = "PETCAMELOT_UAT2";
    }
    
    LinkedHashMap<String, ArrayList<DeliveryInvoice>> getDuePurchaseOrders() {
        LinkedHashMap<String, ArrayList<DeliveryInvoice>> duePurchaseOrders = new LinkedHashMap<>();
        
        String query = "SELECT "
                + dbSchema + ".OPOR.\"DocEntry\", "
                + dbSchema + ".OPOR.\"DocNum\", "
                + dbSchema + ".OPOR.\"CardCode\", "
                + dbSchema + ".OPOR.\"CardName\", "
                + dbSchema + ".OPOR.\"DocDate\", "
                + dbSchema + ".OPOR.\"DocStatus\" "
                + "FROM "
                + dbSchema + ".OPOR";
        //  System.out.println("Query: " + query);

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
                
                purchaseOrderInvoice.setInvoiceId(resultSet.getString("DocEntry"));
                purchaseOrderInvoice.setNumber(resultSet.getString("DocNum"));
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
    
    DeliveryInvoice getPurchaseOrderForDeliveryChecking(String purchaseOrderNumber) {
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        String query = "SELECT "
                + dbSchema + ".OPOR.\"DocEntry\", "
                + dbSchema + ".OPOR.\"DocNum\", "
                + dbSchema + ".OPOR.\"CardCode\", "
                + dbSchema + ".OPOR.\"CardName\", "
                + dbSchema + ".OPOR.\"DocDate\", "
                + dbSchema + ".POR1.\"ItemCode\", "
                + dbSchema + ".POR1.\"Dscription\", "
                + dbSchema + ".POR1.\"Quantity\", "
                + dbSchema + ".POR1.\"Price\", "
                + dbSchema + ".POR1.\"WhsCode\", "
                + dbSchema + ".POR1.\"LineNum\" " // ← THIS IS THE CRITICAL ADDITION
                + "FROM "
                + dbSchema + ".OPOR "
                + "JOIN "
                + dbSchema + ".POR1 ON "
                + dbSchema + ".OPOR.\"DocEntry\" = " + dbSchema + ".POR1.\"DocEntry\" "
                + "WHERE "
                + dbSchema + ".OPOR.\"DocEntry\" = ?";

        // System.out.println("Query: " + query);
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        
        try (Connection connection = databaseConnectionFactory.getSapHanaConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the prepared statement
            preparedStatement.setString(1, purchaseOrderNumber);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean isFirstRow = true;
                
                while (resultSet.next()) {
                    if (isFirstRow) {
                        deliveryInvoice.setInvoiceId(resultSet.getString("DocEntry"));
                        deliveryInvoice.setNumber(resultSet.getString("DocNum"));
                        deliveryInvoice.setSupplier(resultSet.getString("CardCode"));
                        deliveryInvoice.setInsertionDate(resultSet.getString("DocDate"));
                        isFirstRow = false;
                    }
                    
                    DeliveryItem item = new DeliveryItem();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("Dscription"));
                    item.setQuantity(resultSet.getString("Quantity"));
                    item.setPrice(resultSet.getBigDecimal("Price"));
                    item.setBaseLine(resultSet.getInt("LineNum")); // ← POPULATE THE BASE LINE

                    deliveryInvoice.getItems().put(item.getCode(), item);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
            deliveryInvoice.setErrorMessages("Error retrieving purchase order: " + ex.getMessage());
        }
        return deliveryInvoice;
    }

    //-------------------Good Receits----------------------------
    public LinkedHashMap<String, ArrayList<DeliveryInvoice>> getGoodsReceipts() {
        LinkedHashMap<String, ArrayList<DeliveryInvoice>> goodsReceipts = new LinkedHashMap<>();
        
        String query = "SELECT "
                + dbSchema + ".OPDN.\"DocEntry\", "
                + dbSchema + ".OPDN.\"DocNum\", "
                + dbSchema + ".OPDN.\"CardCode\", "
                + dbSchema + ".OPDN.\"CardName\", "
                + dbSchema + ".OPDN.\"DocDate\", "
                + dbSchema + ".OPDN.\"DocStatus\", "
                + dbSchema + ".OPDN.\"Comments\" "
                + "FROM " + dbSchema + ".OPDN "
                + "ORDER BY " + dbSchema + ".OPDN.\"CardName\", " + dbSchema + ".OPDN.\"DocDate\" DESC";
        
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                String supplierName = resultSet.getString("CardName");
                if (goodsReceipts.containsKey(supplierName)) {
                    //do nothing
                } else {
                    goodsReceipts.put(supplierName, new ArrayList<>());
                }
                DeliveryInvoice purchaseOrderInvoice = new DeliveryInvoice();
                purchaseOrderInvoice.setSupplier(supplierName);
                
                purchaseOrderInvoice.setInvoiceId(resultSet.getString("DocEntry"));
                purchaseOrderInvoice.setNumber(resultSet.getString("DocNum"));
                purchaseOrderInvoice.setInsertionDate(resultSet.getString("DocDate"));
                ArrayList<DeliveryInvoice> deliveryInvoices = goodsReceipts.get(supplierName);
                deliveryInvoices.add(purchaseOrderInvoice);
                goodsReceipts.put(supplierName, deliveryInvoices);
                
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return goodsReceipts;
    }
    
    public DeliveryInvoice getGoodsReceipt(String invoiceId) {
        DeliveryInvoice goodsReceipt = new DeliveryInvoice();
        String query = "SELECT "
                + dbSchema + ".OPDN.\"DocEntry\", "
                + dbSchema + ".OPDN.\"DocNum\", "
                + dbSchema + ".OPDN.\"CardCode\", "
                + dbSchema + ".OPDN.\"CardName\", "
                + dbSchema + ".OPDN.\"DocDate\", "
                + dbSchema + ".PDN1.\"ItemCode\", "
                + dbSchema + ".PDN1.\"Dscription\", "
                + dbSchema + ".PDN1.\"Quantity\", "
                + dbSchema + ".PDN1.\"Price\", "
                + dbSchema + ".PDN1.\"WhsCode\", "
                + dbSchema + ".PDN1.\"BaseEntry\", " // Original PO DocEntry
                + dbSchema + ".PDN1.\"BaseLine\", " // Original PO LineNum
                + dbSchema + ".PDN1.\"LineNum\", " // GRPO LineNum
                + dbSchema + ".OPOR.\"DocNum\" AS \"PONum\" " // Original PO Document Number
                + "FROM "
                + dbSchema + ".OPDN " // Goods Receipt (GRPO) header table
                + "JOIN "
                + dbSchema + ".PDN1 ON " // Goods Receipt (GRPO) lines table
                + dbSchema + ".OPDN.\"DocEntry\" = " + dbSchema + ".PDN1.\"DocEntry\" "
                + "LEFT JOIN "
                + dbSchema + ".OPOR ON " // Purchase Order table
                + dbSchema + ".PDN1.\"BaseEntry\" = " + dbSchema + ".OPOR.\"DocEntry\" "
                + "WHERE "
                + dbSchema + ".OPDN.\"DocEntry\" = ?";  // Filter by GRPO DocEntry (primary key)

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        
        try (Connection connection = databaseConnectionFactory.getSapHanaConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, invoiceId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean isFirstRow = true;
                
                while (resultSet.next()) {
                    if (isFirstRow) {
                        goodsReceipt.setInvoiceId(resultSet.getString("DocEntry"));
                        goodsReceipt.setNumber(resultSet.getString("DocNum"));
                        goodsReceipt.setSupplier(resultSet.getString("CardName"));
                        goodsReceipt.setInsertionDate(resultSet.getString("DocDate"));
                        isFirstRow = false;
                    }
                    
                    DeliveryItem item = new DeliveryItem();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("Dscription"));
                    item.setDeliveredQuantity(resultSet.getString("Quantity"));
                    item.setPrice(resultSet.getBigDecimal("Price"));
                    item.setBaseLine(resultSet.getInt("BaseLine")); // Original PO line number
                    //    item.setGrpoLineNum(resultSet.getInt("LineNum")); // GRPO line number

                    goodsReceipt.getItems().put(item.getCode(), item);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
            goodsReceipt.setErrorMessages("Error retrieving goods receipt: " + ex.getMessage());
        }
        return goodsReceipt;
    }
    
    ArrayList<DeliveryInvoice> getDuePurchaseOrdersX() {
        ArrayList<DeliveryInvoice> duePurchaseOrders = new ArrayList<>();
        
        String query = "SELECT "
                + dbSchema + ".OPOR.\"DocEntry\", "
                + dbSchema + ".OPOR.\"DocNum\", "
                + dbSchema + ".OPOR.\"CardCode\", "
                + dbSchema + ".OPOR.\"CardName\", "
                + dbSchema + ".OPOR.\"DocDate\", "
                + dbSchema + ".OPOR.\"DocStatus\" "
                + "FROM "
                + dbSchema + ".OPOR "
                + "WHERE " + dbSchema + ".OPOR.\"DocStatus\" = 'O' "
                + "ORDER BY " + dbSchema + ".OPOR.\"DocDate\" DESC";
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        
        try (Connection connection = databaseConnectionFactory.getSapHanaConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                DeliveryInvoice purchaseOrderInvoice = new DeliveryInvoice();
                purchaseOrderInvoice.setSupplier(resultSet.getString("CardName"));
                purchaseOrderInvoice.setInvoiceId(resultSet.getString("DocEntry"));
                purchaseOrderInvoice.setNumber(resultSet.getString("DocNum"));
                purchaseOrderInvoice.setInsertionDate(resultSet.getString("DocDate"));
                
                duePurchaseOrders.add(purchaseOrderInvoice);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return duePurchaseOrders;
    }
    
    ArrayList<DeliveryInvoice> getGoodsReceiptsX() {
        ArrayList<DeliveryInvoice> goodsReceipts = new ArrayList<>();
        
        String query = "SELECT "
                + dbSchema + ".OPDN.\"DocEntry\", "
                + dbSchema + ".OPDN.\"DocNum\", "
                + dbSchema + ".OPDN.\"CardCode\", "
                + dbSchema + ".OPDN.\"CardName\", "
                + dbSchema + ".OPDN.\"DocDate\", "
                + dbSchema + ".OPDN.\"DocStatus\", "
                + dbSchema + ".OPDN.\"Comments\", "
                + dbSchema + ".OPCH.\"DocNum\" AS \"ReferencedPO\" "
                + "FROM " + dbSchema + ".OPDN "
                + "LEFT JOIN " + dbSchema + ".OPCH ON " + dbSchema + ".OPDN.\"BaseEntry\" = " + dbSchema + ".OPCH.\"DocEntry\" "
                + "ORDER BY " + dbSchema + ".OPDN.\"DocDate\" DESC";
        
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        
        try (Connection connection = databaseConnectionFactory.getSapHanaConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                String string = resultSet.getString("DocDate");
                String cleanedDate = string.split("\\.")[0];

                // Define expected format
                try {
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    
                    LocalDate dbDate = LocalDate.parse(cleanedDate, formatter);
                    LocalDate today = LocalDate.now();
                    
                    if (dbDate.equals(today)) {
                        System.out.println("----------------------"+resultSet.getString("ReferencedPO"));
                        DeliveryInvoice goodsReceipt = new DeliveryInvoice();
                        goodsReceipt.setSupplier(resultSet.getString("CardName"));
                        goodsReceipt.setInvoiceId(resultSet.getString("DocEntry"));
                        goodsReceipt.setNumber(resultSet.getString("DocNum"));
                        goodsReceipt.setInsertionDate(resultSet.getString("DocDate"));
                        goodsReceipt.setReferencedPO(resultSet.getString("ReferencedPO"));

//  goodsReceipt.setComments(resultSet.getString("Comments"));
                        goodsReceipts.add(goodsReceipt);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return goodsReceipts;
    }
    
    public Double getExchangeRate(String currency, Date docDate) {
        String query = "SELECT \"Rate\" FROM "
                + dbSchema + ".ORTT WHERE " + dbSchema + ".ORTT.\"Currency\" = ? AND " + dbSchema + ".ORTT.\"RateDate\" = ?";
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        
        try (Connection connection = databaseConnectionFactory.getSapHanaConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, currency);
            preparedStatement.setDate(2, new java.sql.Date(docDate.getTime()));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Rate");
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch exchange rate: " + e.getMessage());
        }
        return null;
    }
    
    public String getSupplierCurrency(String cardCode) {
        String query = "SELECT \"Currency\" FROM " + dbSchema + ".OCRD WHERE " + dbSchema + ".OCRD.\"CardCode\" = ?";
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        try (Connection connection = databaseConnectionFactory.getSapHanaConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cardCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Currency");
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch supplier currency: " + e.getMessage());
        }
        return "EUR"; // Default fallback
    }

    ////------SAP VERSION---------
    public String saveSapTempoDeliveryChecking(String invoiceId, String supplier, String invoiceNumber, ArrayList<DeliveryItem> deliveryItems, String status) {
        LocalDateTime idItem = LocalDateTime.now();
        try (Connection connection = new DatabaseConnectionFactory().getMySQLConnection();
                PreparedStatement invoiceInsertionPreparedStatement = connection.prepareStatement(
                        "INSERT INTO delivery_title (invoice_id, id, number, supplier, note , status) VALUES (?, ?, ?, ?, ?, ?);");
                PreparedStatement deliveredItemsPreparedStatement = connection.prepareStatement(
                        "INSERT INTO delivery_data (delivery_id, item_code, sent, delivered, baseLine) VALUES (?, ?, ?, ?, ?);")) {
            connection.setAutoCommit(false); // Begin transaction

            System.out.println("🚚 Starting SAP Delivery Save Insertion...");

            // Insert invoice title
            invoiceInsertionPreparedStatement.setString(1, invoiceId);
            invoiceInsertionPreparedStatement.setString(2, idItem.toString());
            invoiceInsertionPreparedStatement.setString(3, invoiceNumber);
            invoiceInsertionPreparedStatement.setString(4, supplier);
            invoiceInsertionPreparedStatement.setString(5, " "); // Note field
            invoiceInsertionPreparedStatement.setString(6, status); // Note field
            invoiceInsertionPreparedStatement.addBatch();

            // Insert delivery items
            for (DeliveryItem deliveryItem : deliveryItems) {
                deliveredItemsPreparedStatement.setString(1, invoiceId);
                deliveredItemsPreparedStatement.setString(2, deliveryItem.getCode());
                deliveredItemsPreparedStatement.setString(3, deliveryItem.getSentQuantity());
                deliveredItemsPreparedStatement.setString(4, deliveryItem.getDeliveredQuantity());
                deliveredItemsPreparedStatement.setInt(5, deliveryItem.getBaseLine());
                deliveredItemsPreparedStatement.addBatch();
            }

            // Execute batch inserts
            invoiceInsertionPreparedStatement.executeBatch();
            deliveredItemsPreparedStatement.executeBatch();
            connection.commit(); // Commit transaction

            System.out.println("✅ SAP Delivery Batch Insertion Complete.");
            return "SUCCESS";
            
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, "❌ Database error during SAP delivery insertion", ex);
            return "ERROR: " + ex.getMessage();
        }
    }
    
    public ArrayList<DeliveryInvoice> getAllOpenSapTempoDeliveryInvoices() {
        ArrayList<DeliveryInvoice> deliveryInvoices = new ArrayList<>();
        
        String sql = "SELECT * FROM delivery_title where status='open' ;";
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
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deliveryInvoices;
    }
    
    DeliveryInvoice getSapCamelotTempoDeliveryInvoice(String invoiceId) {
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
                deliveryItem.setBaseLine(resultSet.getInt("baseLine"));
                deliveryItems.put(resultSet.getString("item_code"), deliveryItem);
                x++;
                
            }
            deliveryInvoice.setItems(deliveryItems);
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deliveryInvoice;
    }
    
    public String deleteDeliveryChecking(String invoiceId) {
        
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
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Delivery Checking with id" + invoiceId + "deleted";
    }
    
    public boolean tempoExist(String invoiceId) {
        try (Connection connection = new DatabaseConnectionFactory().getMySQLConnection();
                PreparedStatement checkInvoicePreparedStatement = connection.prepareStatement(
                        "SELECT COUNT(*) FROM delivery_title WHERE invoice_id = ?;")) {
            
            System.out.println("🔍 Checking if tempo delivery exists for invoice ID: " + invoiceId);
            
            checkInvoicePreparedStatement.setString(1, invoiceId);
            ResultSet resultSet = checkInvoicePreparedStatement.executeQuery();
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, "❌ Database error during tempo delivery check", ex);
        }
        
        return false; // Default return if an error occurs or no record is found
    }
    
}
