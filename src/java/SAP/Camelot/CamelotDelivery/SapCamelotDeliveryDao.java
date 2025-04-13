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
                        deliveryInvoice.setSupplier(resultSet.getString("CardName"));
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
                + dbSchema + ".OPDN.\"Comments\" "
                + "FROM " + dbSchema + ".OPDN "
                + "ORDER BY " + dbSchema + ".OPDN.\"DocDate\" DESC";  // Order by date descending (newest first)

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();

        try (Connection connection = databaseConnectionFactory.getSapHanaConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                DeliveryInvoice goodsReceipt = new DeliveryInvoice();
                goodsReceipt.setSupplier(resultSet.getString("CardName"));
                goodsReceipt.setInvoiceId(resultSet.getString("DocEntry"));
                goodsReceipt.setNumber(resultSet.getString("DocNum"));
                goodsReceipt.setInsertionDate(resultSet.getString("DocDate"));
                //  goodsReceipt.setComments(resultSet.getString("Comments"));

                goodsReceipts.add(goodsReceipt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotDeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return goodsReceipts;
    }

}
