/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItemSearch;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class SapCamelotItemSearchDao {

    public Item getItemByAltercode(String altercode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        Item item = null;

        try {
            // First try to find by barcode
            item = getItemByBarcode(connection, altercode);

            // If not found by barcode, try by item code
            if (item == null) {
                item = getItemByItemCode(connection, altercode);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemSearchDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SapCamelotItemSearchDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return item;
    }

    private Item getItemByBarcode(Connection connection, String barcode) throws SQLException {
        String query = "SELECT t1.*, t2.* "
                + "FROM \"PETCAMELOT_UAT2\".\"BYT_V_BARCODEDETAILS\" t1 "
                + "JOIN \"PETCAMELOT_UAT2\".\"BYT_V_ITEMDETAILS\" t2 "
                + "ON t1.\"ItemCode\" = t2.\"ItemCode\" "
                + "WHERE t1.\"BarCode\" = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, barcode);
        ResultSet resultSet = statement.executeQuery();

        return processResultSet(resultSet);
    }

    private Item getItemByItemCode(Connection connection, String itemCode) throws SQLException {
        String query = "SELECT t1.*, t2.* "
                + "FROM \"PETCAMELOT_UAT2\".\"BYT_V_BARCODEDETAILS\" t1 "
                + "JOIN \"PETCAMELOT_UAT2\".\"BYT_V_ITEMDETAILS\" t2 "
                + "ON t1.\"ItemCode\" = t2.\"ItemCode\" "
                + "WHERE t1.\"ItemCode\" = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, itemCode);
        ResultSet resultSet = statement.executeQuery();

        return processResultSet(resultSet);
    }

    private Item processResultSet(ResultSet resultSet) throws SQLException {
        Item item = null;
        int index = 0;

        while (resultSet.next()) {
            if (index == 0) {
                item = new Item();
                item.setCode(resultSet.getString("ItemCode"));
                item.setDescription(resultSet.getString("ItemName"));
                item.setPosition(resultSet.getString("PickLocation"));
                item.setQuantity(resultSet.getString("Stock"));
            }

            AltercodeContainer altercodeContainer = new AltercodeContainer();
            altercodeContainer.setAltercode(resultSet.getString("BarCode"));

            String uom = resultSet.getString("UnitOfMeasurement");
            altercodeContainer.setStatus(uom == null ? "" : uom.trim());

            String mainBarcode = resultSet.getString("MainBarcode");
            if (mainBarcode != null && mainBarcode.equals(resultSet.getString("BarCode"))) {
                altercodeContainer.setMainBarcode(true);
                item.setMainBarcode(resultSet.getString("BarCode"));
            } else {
                altercodeContainer.setMainBarcode(false);
            }

            if (item != null) {
                item.addAltercodeContainer(altercodeContainer);
            }

            index++;
        }

        resultSet.close();
        return item;
    }

    Item getItemByItemCodeFromDB(String itemCode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        Item item = null;

        try {
            // Query to fetch item details by ItemCode from tables
            String query = "SELECT t1.\"ItemCode\", t1.\"ItemName\",  t1.\"CodeBars\"   "
                    + "FROM \"PETCAMELOT_UAT2\".\"OITM\" t1 " // Items master table
                    + "WHERE t1.\"ItemCode\" = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, itemCode);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (item == null) {  // Create item only once for the first record
                    item = new Item();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("ItemName"));
                    item.setMainBarcode(resultSet.getString("CodeBars"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemSearchDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SapCamelotItemSearchDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return item;
    }

}
