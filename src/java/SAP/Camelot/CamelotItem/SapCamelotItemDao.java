/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItem;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import SAP.SapBasicModel.SapAltercodeContainer;
import SAP.SapBasicModel.SapItem;
import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
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
public class SapCamelotItemDao {

    Item getItemByItemCode(String itemCode) {

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        Item item = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            String query = "SELECT OITM.\"ItemCode\",  "
                    + " OITM.\"ItemName\", OITM.\"CodeBars\",  "
                    + " OITM.\"U_PickLocation\", "
                    + " OBCD.\"BcdCode\",   "
                    + " OUOM.\"UomEntry\",  "
                    + " OUOM.\"UomCode\", "
                    + " OUOM.\"UomName\", "
                    + " UGP1.\"BaseQty\" "
                    + "  FROM "
                    + " PETCAMELOT_UAT2.\"OITM\" "
                    + "  LEFT JOIN "
                    + " PETCAMELOT_UAT2.OBCD ON PETCAMELOT_UAT2.OITM.\"ItemCode\" = PETCAMELOT_UAT2.OBCD.\"ItemCode\"  "
                    + "  LEFT JOIN "
                    + " PETCAMELOT_UAT2.OUOM ON OBCD.\"UomEntry\" = PETCAMELOT_UAT2.OUOM.\"UomEntry\"  "
                    + "  LEFT JOIN "
                    + " PETCAMELOT_UAT2.UGP1 ON OUOM.\"UomEntry\" = UGP1.\"UomEntry\" AND UGP1.\"UgpEntry\" = OITM.\"UgpEntry\" " + "  WHERE \n"
                    + " PETCAMELOT_UAT2.OITM.\"ItemCode\" = '" + itemCode + "';";

            resultSet = statement.executeQuery(query);
            int index = 0;
            while (resultSet.next()) {
                if (index == 0) {
                    item = new Item();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("ItemName"));
                    item.setPosition(resultSet.getString("U_PickLocation"));

                }

                index++;

                AltercodeContainer altercodeContainer = new AltercodeContainer();
                altercodeContainer.setAltercode(resultSet.getString("BcdCode"));
                altercodeContainer.setItemsInPackage(resultSet.getDouble("BaseQty"));
                if (resultSet.getString("UomName") == null) {
                    altercodeContainer.setStatus("");
                } else {
                    altercodeContainer.setStatus(resultSet.getString("UomName").trim());
                }
                if (resultSet.getString("BcdCode") == null) {
                    //do nothing
                } else {
                    if (resultSet.getString("CodeBars").equals(resultSet.getString("BcdCode"))) {
                        altercodeContainer.setMainBarcode(true);
                        //  item.setMainBarcode(resultSet.getString("ALTERNATECODE"));// HERE ALTERNATECODE AND MAIN_CODE IS THE SAME
                        item.setMainBarcode(resultSet.getString("BcdCode"));//
                    } else {
                        altercodeContainer.setMainBarcode(false);
                    }
                }

                if (item != null) {//It should never be, i mean, if there is an altercode, there is an item. But, just in any case
                    item.addAltercodeContainer(altercodeContainer);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    SapItem getSapItemByItemCode(String itemCode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        SapItem item = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            String query = "SELECT "
                    + " OITM.\"ItemCode\", "
                    + " OITM.\"ItemName\", "
                    + " OITM.\"CodeBars\", "
                    + " OITM.\"U_PickLocation\", "
                    + " OITM.\"ItmsGrpCod\", " // Added item group code
                    + " OBCD.\"BcdCode\", "
                    + " OBCD.\"BcdName\", "
                    + " OUOM.\"UomEntry\", "
                    + " OUOM.\"UomCode\", "
                    + " OUOM.\"UomName\", "
                    + " UGP1.\"BaseQty\", "
                    + " OUGP.\"UgpEntry\", "
                    + " OUGP.\"UgpCode\", "
                    + " OUGP.\"UgpName\" "
                    + "FROM "
                    + " PETCAMELOT_UAT2.\"OITM\" "
                    + " LEFT JOIN PETCAMELOT_UAT2.UGP1 ON OITM.\"UgpEntry\" = UGP1.\"UgpEntry\" "
                    + " LEFT JOIN PETCAMELOT_UAT2.OUOM ON UGP1.\"UomEntry\" = OUOM.\"UomEntry\" "
                    + " LEFT JOIN PETCAMELOT_UAT2.OBCD ON OITM.\"ItemCode\" = OBCD.\"ItemCode\" "
                    + "     AND OUOM.\"UomEntry\" = OBCD.\"UomEntry\" "
                    + " LEFT JOIN PETCAMELOT_UAT2.OUGP ON OITM.\"UgpEntry\" = OUGP.\"UgpEntry\" "
                    + "WHERE "
                    + " OITM.\"ItemCode\" = '" + itemCode + "';";

            resultSet = statement.executeQuery(query);
            int index = 0;
            while (resultSet.next()) {
                if (index == 0) {
                    item = new SapItem();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("ItemName"));
                    item.setPosition(resultSet.getString("U_PickLocation"));
                    item.setItemsGroupCode(resultSet.getInt("ItmsGrpCod")); // Set item group code

                    item.getUnitOfMeasurementGroup().setUgpEntry(resultSet.getInt("UgpEntry"));
                    item.getUnitOfMeasurementGroup().setUgpCode(resultSet.getString("UgpCode"));
                    item.getUnitOfMeasurementGroup().setUgpName(resultSet.getString("UgpName"));

                }

                index++;

                String unitOfMeasurementCode = resultSet.getString("UomCode");
                if (!item.getUnitOfMeasurementGroup().getUnitOfMeasurements().containsKey(unitOfMeasurementCode)) {
                    SapUnitOfMeasurement unitOfMeasurement = new SapUnitOfMeasurement();
                    unitOfMeasurement.setUomEntry(resultSet.getInt("UomEntry"));
                    unitOfMeasurement.setUomCode(resultSet.getString("UomCode"));
                    unitOfMeasurement.setUomName(resultSet.getString("UomName"));
                    unitOfMeasurement.setBaseQuantity(resultSet.getDouble("BaseQty"));
                    item.getUnitOfMeasurementGroup().getUnitOfMeasurements().put(unitOfMeasurementCode, unitOfMeasurement);

                }
                SapUnitOfMeasurement unitOfMeasurement = item.getUnitOfMeasurementGroup().getUnitOfMeasurements().get(unitOfMeasurementCode);

                SapAltercodeContainer sapAltercodeContainer = new SapAltercodeContainer();
                sapAltercodeContainer.setAltercode(resultSet.getString("BcdCode"));
                sapAltercodeContainer.setAltercodeName(resultSet.getString("BcdName"));

                unitOfMeasurement.getAltercodeContainers().add(sapAltercodeContainer);

                AltercodeContainer altercodeContainer = new AltercodeContainer();
                altercodeContainer.setAltercode(resultSet.getString("BcdCode"));
                altercodeContainer.setItemsInPackage(resultSet.getDouble("BaseQty"));
                if (resultSet.getString("UomName") == null) {
                    altercodeContainer.setStatus("");
                } else {
                    altercodeContainer.setStatus(resultSet.getString("UomName").trim());
                }
                if (resultSet.getString("BcdCode") == null) {
                    //do nothing
                } else {
                    if (resultSet.getString("CodeBars") != null && resultSet.getString("CodeBars").equals(resultSet.getString("BcdCode"))) {
                        altercodeContainer.setMainBarcode(true);
                        //  item.setMainBarcode(resultSet.getString("ALTERNATECODE"));// HERE ALTERNATECODE AND MAIN_CODE IS THE SAME
                        item.setMainBarcode(resultSet.getString("BcdCode"));//
                    } else {
                        altercodeContainer.setMainBarcode(false);
                    }
                }

                if (item != null) {//It should never be, i mean, if there is an altercode, there is an item. But, just in any case
                    item.addAltercodeContainer(altercodeContainer);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    LinkedHashMap<Short, SapUnitOfMeasurementGroup> getAllUnitOfMeasurementGroups() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = new LinkedHashMap<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            String query = "SELECT * FROM  PETCAMELOT_UAT2.OUGP "
                    + "INNER JOIN  PETCAMELOT_UAT2.UGP1 "
                    + "ON  PETCAMELOT_UAT2.OUGP.\"UgpEntry\" = PETCAMELOT_UAT2.UGP1.\"UgpEntry\" "
                    + "INNER JOIN  PETCAMELOT_UAT2.OUOM  "
                    + "ON  PETCAMELOT_UAT2.UGP1.\"UomEntry\" = PETCAMELOT_UAT2.OUOM.\"UomEntry\" ; ";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                short ugpEntry = resultSet.getShort("UgpEntry");
                if (!allUnitOfMeasurementGroups.containsKey(ugpEntry)) {
                    SapUnitOfMeasurementGroup unitOfMeasurementGroup = new SapUnitOfMeasurementGroup();
                    unitOfMeasurementGroup.setUgpEntry(ugpEntry);
                    unitOfMeasurementGroup.setUgpCode(resultSet.getString("UgpCode"));
                    unitOfMeasurementGroup.setUgpName(resultSet.getString("UgpName"));
                    allUnitOfMeasurementGroups.put(ugpEntry, unitOfMeasurementGroup);
                }
                SapUnitOfMeasurementGroup unitOfMeasurementGroup = allUnitOfMeasurementGroups.get(ugpEntry);

                SapUnitOfMeasurement unitOfMeasurement = new SapUnitOfMeasurement();
                unitOfMeasurement.setUomEntry(resultSet.getShort("UomEntry"));
                unitOfMeasurement.setUomCode(resultSet.getString("UomCode"));
                unitOfMeasurement.setUomName(resultSet.getString("UomName"));
                unitOfMeasurement.setBaseQuantity(resultSet.getDouble("BaseQty"));
                unitOfMeasurementGroup.getUnitOfMeasurements().put(resultSet.getString("UomCode"), unitOfMeasurement);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUnitOfMeasurementGroups;
    }

    LinkedHashMap<String, SapItem> getAllItemsFromView() {
        LinkedHashMap<String, SapItem> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BYT_V_ITEMDETAILS.\"ItemCode\" as ItemCode,"
                    + "BYT_V_ITEMDETAILS.\"ItemName\" AS ItemName, "
                    + "BYT_V_ITEMDETAILS.\"PickLocation\" AS PickLocation, "
                    + "BYT_V_ITEMDETAILS.\"Stock\" as Stock, "
                    + "BYT_V_BARCODEDETAILS.\"BarCode\" as ALTERNATECODE, "
                    + "BYT_V_BARCODEDETAILS.\"UnitOfMeasurement\" as CODEDESCRIPTION "
                    + " FROM PETCAMELOT_UAT2.BYT_V_ITEMDETAILS "
                    + "JOIN PETCAMELOT_UAT2.BYT_V_BARCODEDETAILS "
                    + "ON PETCAMELOT_UAT2.BYT_V_ITEMDETAILS.\"ItemCode\" = PETCAMELOT_UAT2.BYT_V_BARCODEDETAILS.\"ItemCode\" "
                    + "ORDER BY BYT_V_ITEMDETAILS.\"ItemCode\"; ");

            while (resultSet.next()) {
                String code = resultSet.getString("ItemCode");
                SapItem item = null;
                if (!items.containsKey(code)) {
                    item = new SapItem();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("ItemName"));
                    String position = "";
                    if (resultSet.getString("PickLocation") != null) {
                        position = resultSet.getString("PickLocation");
                    }
                    item.setPosition(position);
                    item.setQuantity(resultSet.getString("Stock"));

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
            Logger.getLogger(SapCamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    LinkedHashMap<Integer, String> getAllItemsGroups() {
        LinkedHashMap<Integer, String> itemsGroups = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT PETCAMELOT_UAT2.OITB.\"ItmsGrpCod\" as ItemsGroupCode, "
                    + " PETCAMELOT_UAT2.OITB.\"ItmsGrpNam\" as ItemsGroupName "
                    + " FROM PETCAMELOT_UAT2.OITB  ; ");
            while (resultSet.next()) {
                itemsGroups.put(resultSet.getInt("ItemsGroupCode"), resultSet.getString("ItemsGroupName"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemsGroups;
    }

    public LinkedHashMap<Short, SapUnitOfMeasurement> getAllUnitsOfMeasurement() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = new LinkedHashMap<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM PETCAMELOT_UAT2.OUOM";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                short uomEntry = resultSet.getShort("UomEntry");

                if (!allUnitsOfMeasurement.containsKey(uomEntry)) {
                    SapUnitOfMeasurement unitOfMeasurement = new SapUnitOfMeasurement();
                    unitOfMeasurement.setUomEntry(uomEntry);
                    unitOfMeasurement.setUomCode(resultSet.getString("UomCode"));
                    unitOfMeasurement.setUomName(resultSet.getString("UomName"));
                    unitOfMeasurement.setBaseQuantity(resultSet.getDouble("BaseQty"));
                    unitOfMeasurement.setLocked(resultSet.getString("Locked").equals("Y"));
                    unitOfMeasurement.setDataSource(resultSet.getString("DataSource"));

                    // Add any additional OUOM fields you need
                    allUnitsOfMeasurement.put(uomEntry, unitOfMeasurement);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUnitsOfMeasurement;
    }

}
