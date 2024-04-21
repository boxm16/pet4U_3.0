/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotComparingAnalysis;

import CamelotSales.CamelotSalesDao;
import SalesX.SoldItem;
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

    LinkedHashMap<String, SoldItem> getSales(LinkedHashMap<String, SoldItem> camelotItemsForSales) {
        String firstDate ="2023-7-1";
        String lastDate = "2024-1-18";
      //  String lastDate = "2024-4-18";

        String sql = "SELECT * FROM camelot_month_sales WHERE date >='" + firstDate + "' AND date <= '" + lastDate + "' ;";
        System.out.println(sql);
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            int x = 0;
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                double eshopSales = resultSet.getDouble("sales");

                if (camelotItemsForSales.containsKey(code)) {
                    SoldItem soldItem = camelotItemsForSales.get(code);
                    soldItem.setEshopSales(soldItem.getEshopSales() + eshopSales);
                    camelotItemsForSales.put(code, soldItem);
                } else {
                    System.out.println("There is sales for camelot item, but there is not item. " + code + ". Total those items: " + x);
                    x++;
                }

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return camelotItemsForSales;
    }

}
