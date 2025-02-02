/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camelot.CamelotItemSearch;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CamelotItemSearchDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    Item getItemByAltercode(String altercode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        Item item = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;

            resultSet = statement.executeQuery("SELECT 'ItemCode', 'ItemName', Stock, InvntryUom, MainBarcode, PickLocation,BarCode, UnitOfMeasurement "
                    + "FROM \"PETCAMELOT_UAT2\".\"BYT_V_ITEMDETAILS\" t1 "
                    + "JOIN \"PETCAMELOT_UAT2\".\"BYT_V_BARCODEDETAILS\" t2 ON t1.\"ItemCode\" = t2.\"ItemCode\" "
                    + "WHERE t2.\"ItemCode\" = ("
                    + "    SELECT \"ItemCode\" FROM \"PETCAMELOT_UAT2\".\"BYT_V_BARCODEDETAILS\" WHERE \"BarCode\" = '" + altercode + "'"
                    + ");");
            int index = 0;
            while (resultSet.next()) {
                if (index == 0) {
                    item = new Item();
                    item.setCode(resultSet.getString("ItemCode"));
                    item.setDescription(resultSet.getString("ItemName"));

                }
                item.setPosition(resultSet.getString("PickLocation"));

                item.setQuantity(resultSet.getString("Stock"));

                index++;
            }
            AltercodeContainer altercodeContainer = new AltercodeContainer();
            altercodeContainer.setAltercode(resultSet.getString("BarCode"));
            if (resultSet.getString("UnitOfMeasurement") == null) {
                altercodeContainer.setStatus("");
            } else {
                altercodeContainer.setStatus(resultSet.getString("UnitOfMeasurement").trim());
            }
            if (resultSet.getString("MainBarcode") == null) {
                //do nothing
            } else {
                if (resultSet.getString("MainBarcode").equals(resultSet.getString("BarCode"))) {
                    altercodeContainer.setMainBarcode(true);
                    //  item.setMainBarcode(resultSet.getString("ALTERNATECODE"));// HERE ALTERNATECODE AND MAIN_CODE IS THE SAME
                    item.setMainBarcode(resultSet.getString("BarCode"));//
                } else {
                    altercodeContainer.setMainBarcode(false);
                }
            }

            if (resultSet.getShort("IS_PACK_BC") == 0) {
                //do nothing
            } else {
                altercodeContainer.setPackageBarcode(true);
                altercodeContainer.setItemsInPackage(resultSet.getDouble("PACK_QTY"));
            }

            if (item != null) {//It should never be, i mean, if there is an altercode, there is an item. But, just in any case
                item.addAltercodeContainer(altercodeContainer);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemSearchDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

}
