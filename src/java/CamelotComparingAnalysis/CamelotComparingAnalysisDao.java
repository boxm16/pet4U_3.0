/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotComparingAnalysis;

import BasicModel.AltercodeContainer;
import CamelotSales.CamelotSalesDao;
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
public class CamelotComparingAnalysisDao {

    LinkedHashMap<String, SoldItem3> getTotalSales(LinkedHashMap<String, SoldItem3> camelotItemsForSales) {
        String firstDate = "2023-7-1";
        String lastDate = "2023-9-1";
        // String lastDate = "2024-4-18";

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ITEMCODE, SUM(QTY) AS SALES"
                    + "  FROM [fotiou].[dbo].[WH_SALES] WHERE ENTRYDATE >= '" + firstDate + "' "
                    + "AND ENTRYDATE <= '" + lastDate + "' group by ITEMCODE order by ITEMCODE;");

            while (resultSet.next()) {
                String code = resultSet.getString("ITEMCODE").trim();
                SoldItem3 soldItem = camelotItemsForSales.get(code);
                if (soldItem == null) {
                    soldItem = new SoldItem3();
                    soldItem.setCode(code);
                    System.out.println("BAD THING WITH CODE : " + code);
                }
                double sales = resultSet.getDouble("SALES");
                //   System.out.println(code+" "+sales);
                soldItem.setEshopSales(sales);
                camelotItemsForSales.put(code, soldItem);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotItemsForSales;

    }

    public LinkedHashMap<String, SoldItem3> getCamelotItemsForSales() {
        LinkedHashMap<String, SoldItem3> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 ORDER BY EXPR1, EXPR2;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                SoldItem3 item = null;
                if (!items.containsKey(code)) {
                    item = new SoldItem3();
                    item.setCode(resultSet.getString("ABBREVIATION").trim());
                    item.setDescription(resultSet.getString("NAME").trim());
                    String position_1 = "";
                    String position_2 = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position_1 = resultSet.getString("EXPR1").trim();
                    }
                    if (resultSet.getString("EXPR2") != null) {
                        position_2 = resultSet.getString("EXPR2").trim();
                    }
                    item.setPosition(position_1 + position_2);
                    item.setQuantity(resultSet.getString("QTYBALANCE").trim());
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
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }
}
