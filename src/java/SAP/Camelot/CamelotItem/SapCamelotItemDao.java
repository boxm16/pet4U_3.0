/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItem;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

            resultSet = statement.executeQuery("SELECT \n"
                    + "    OITM.\"ItemCode\",      -- Item Code\n"
                    + "    OITM.\"ItemName\",      -- Item Name\n"
                    + "    OITM.\"CodeBars\",      -- Item Name\n"
                    + "    OITM.\"U_PickLocation\",      -- Item Name\n"
                    + "    OBCD.\"BcdCode\",       -- Barcode\n"
                    + "    OUOM.\"UomEntry\"        -- Unit of Measure\n"
                    + "    OUOM.\"UomCode\"        -- Unit of Measure\n"
                    + "    OUOM.\"UomName\"        -- Unit of Measure\n"
                    + "FROM "
                    + "    OITM                -- Item Master Table\n"
                    + "JOIN "
                    + "    OBCD ON OITM.\"ItemCode\" = OBCD.\"ItemCode\"  -- Barcodes Table\n"
                    + "LEFT JOIN "
                    + "    OUOM ON OBCD.\"UomEntry\" = OUOM.\"UomEntry\"  -- Units of Measure Table\n"
                    + "WHERE "
                    + "    OITM.\"ItemCode\" = '1271';");
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
                if (resultSet.getString("UomName") == null) {
                    altercodeContainer.setStatus("");
                } else {
                    altercodeContainer.setStatus(resultSet.getString("UomName").trim());
                }
                if (resultSet.getString("CodeBars") == null) {
                    //do nothing
                } else {
                    if (resultSet.getString("BarCodes").equals(resultSet.getString("BarCode"))) {
                        altercodeContainer.setMainBarcode(true);
                        //  item.setMainBarcode(resultSet.getString("ALTERNATECODE"));// HERE ALTERNATECODE AND MAIN_CODE IS THE SAME
                        item.setMainBarcode(resultSet.getString("BarCode"));//
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

}
