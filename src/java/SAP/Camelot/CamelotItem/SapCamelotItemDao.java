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
                    + "  JOIN "
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
                    + " OBCD.\"BcdCode\", "
                    + " OBCD.\"BcdName\", "
                    + " OUOM.\"UomEntry\", "
                    + " OUOM.\"UomCode\", "
                    + " OUOM.\"UomName\", "
                    + " UGP1.\"BaseQty\", "
                    + " OUGP.\"UgpEntry\", " // Added OUGP.UgpEntry
                    + " OUGP.\"UgpCode\", " // Added OUGP.UgpCode
                    + " OUGP.\"UgpName\" " // Added OUGP.UgpName
                    + " FROM "
                    + " PETCAMELOT_UAT2.\"OITM\" "
                    + " JOIN "
                    + " PETCAMELOT_UAT2.OBCD ON PETCAMELOT_UAT2.OITM.\"ItemCode\" = PETCAMELOT_UAT2.OBCD.\"ItemCode\" "
                    + " LEFT JOIN "
                    + " PETCAMELOT_UAT2.OUOM ON OBCD.\"UomEntry\" = PETCAMELOT_UAT2.OUOM.\"UomEntry\" "
                    + " LEFT JOIN "
                    + " PETCAMELOT_UAT2.UGP1 ON OUOM.\"UomEntry\" = UGP1.\"UomEntry\" AND UGP1.\"UgpEntry\" = OITM.\"UgpEntry\" "
                    + " LEFT JOIN " // Added LEFT JOIN for OUGP
                    + " PETCAMELOT_UAT2.OUGP ON OITM.\"UgpEntry\" = OUGP.\"UgpEntry\" " // Join condition for OUGP
                    + " WHERE "
                    + " PETCAMELOT_UAT2.OITM.\"ItemCode\" = '" + itemCode + "';";
            
            resultSet = statement.executeQuery(query);
            int index = 0;
            while (resultSet.next()) {
                if (index == 0) {
                    item = new SapItem();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("ItemName"));
                    item.setPosition(resultSet.getString("U_PickLocation"));
                    item.getUnitOfMeasurementGroup().setUgpEntry(resultSet.getInt("UgpEntry"));
                    item.getUnitOfMeasurementGroup().setUgpCode(resultSet.getString("UgpCode"));
                    item.getUnitOfMeasurementGroup().setUgpName(resultSet.getString("UgpName"));
                    
                }
                
                index++;
                
                String unitOfMeasurementCode = resultSet.getString("UomCode");
                if (!item.getUnitOfMeasurementGroup().getUnitOfMeasurements().containsKey(unitOfMeasurementCode)) {
                    SapUnitOfMeasurement unitOfMeasurement = new SapUnitOfMeasurement();
                    unitOfMeasurement.setUomEntry(resultSet.getInt("UomEntry"));
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
    
    LinkedHashMap<Short, SapUnitOfMeasurementGroup> getAllUnitOfMeasurementGroups() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = new LinkedHashMap<>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            String query = "SELECT * FROM  OUGP "
                    + "INNER JOIN  UGP1 "
                    + "ON  OUGP.\"UgpEntry\" = UGP1.\"UgpEntry\" ;";
            
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
                unitOfMeasurement.setUomEntry(resultSet.getShort("uomEntry"));
                unitOfMeasurement.setUomCode(resultSet.getString("uomCode"));
                unitOfMeasurement.setUomName(resultSet.getString("uomName"));
                unitOfMeasurement.setBaseQuantity(resultSet.getDouble("BaseQty"));
            }
            
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUnitOfMeasurementGroups;
    }
    
}
