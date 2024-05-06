/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4uSales;

import BasicModel.AltercodeContainer;
import SalesX.SoldItem;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class Pet4uSalesDao {

    LinkedHashMap<String, SoldItem> getPet4uItemsForSales() {
        LinkedHashMap<String, SoldItem> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 ORDER BY EXPR1;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                SoldItem item = null;
                if (!items.containsKey(code)) {
                    item = new SoldItem();
                    item.setCode(resultSet.getString("ABBREVIATION").trim());
                    item.setDescription(resultSet.getString("NAME").trim());
                    String position = "";

                    if (resultSet.getString("EXPR1") != null) {
                        position = resultSet.getString("EXPR1").trim();
                    }

                    item.setPosition(position);

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
            Logger.getLogger(Pet4uSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    LinkedHashMap<String, SoldItem> getPet4uMonthSalesFromMicrosoftDB(String dateString, LinkedHashMap<String, SoldItem> pet4uAllItemsForSales) {
        LocalDate date = LocalDate.parse(dateString);
        LocalDate firstDate = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfMonth());

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetSales = statement.executeQuery("SELECT ABBREVIATION, SUM(QTY) AS SALES"
                    + "  FROM [petworld].[dbo].[WH_SALES_VAR] WHERE ENTRYDATE >= '" + firstDate + "' "
                    + "AND ENTRYDATE <= '" + lastDate + "' group by ABBREVIATION order by ABBREVIATION;");

            while (resultSetSales.next()) {
                String code = resultSetSales.getString("ABBREVIATION").trim();
                SoldItem soldItem = pet4uAllItemsForSales.get(code);
                if (soldItem == null) {
                    System.out.println("Pet4u Month Sales Upload: Sales exist, itemCode doesnt exist. code:" + code);
                    soldItem = new SoldItem();
                    soldItem.setCode(code);
                }
                double sales = resultSetSales.getDouble("SALES");
                //   System.out.println(code+" "+sales);
                soldItem.setEshopSales(sales);
                pet4uAllItemsForSales.put(code, soldItem);
            }

            Statement statementEndo = connection.createStatement();
            ResultSet resultSetSalesEndo = statementEndo.executeQuery("SELECT ABBREVIATION, SUM(QUANTITY) AS SENT"
                    + "  FROM [petworld].[dbo].[WH_ENDA_VAR] WHERE DOCDATE >= '" + firstDate + "' "
                    + "AND DOCDATE <= '" + lastDate + "' group by ABBREVIATION order by ABBREVIATION;");
            while (resultSetSalesEndo.next()) {
                String code = resultSetSalesEndo.getString("ABBREVIATION").trim();
                SoldItem soldItem = pet4uAllItemsForSales.get(code);
                if (soldItem == null) {
                    soldItem = new SoldItem();
                    soldItem.setCode(code);
                }
                double sent = resultSetSalesEndo.getDouble("SENT");
                //   System.out.println(code+" "+sales);
                soldItem.setShopsSupply(sent);
                pet4uAllItemsForSales.put(code, soldItem);
            }
            resultSetSales.close();
            resultSetSalesEndo.close();
            statement.close();
            statementEndo.close();

            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pet4uAllItemsForSales;
    }

    public String insertNewUpload(String date, LinkedHashMap<String, SoldItem> soldItems) {

        System.out.println("STARTING INSERTING UPLOADED DATA");
        System.out.println(" STARTING ADDING ITEMS TO 'month_sales' INSERTION BATCH");
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO month_sales (code, date,  eshop_sales, shops_supply) VALUES (?,?,?,?)");
            int index = 0;
            for (Map.Entry<String, SoldItem> soldItemEntry : soldItems.entrySet()) {
                SoldItem soldItem = soldItemEntry.getValue();
                itemInsertStatement.setString(1, soldItem.getCode());
                itemInsertStatement.setString(2, date);
                itemInsertStatement.setDouble(3, soldItem.getEshopSales());
                itemInsertStatement.setDouble(4, soldItem.getShopsSupply());

                itemInsertStatement.addBatch();
                index++;
                if (index % 1000 == 0) {
                    System.out.println(index + " soldItems added to batch");
                }

            }

            itemInsertStatement.executeBatch();
            connection.commit();
            itemInsertStatement.close();

            connection.close();

            System.out.println("'sales' INSERTION BATCH EXECUTED.");
            System.out.println("-----------------------------------");
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uSalesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "SALES UPLOAD  EXECUTED SUCCESSFULLY.";
    }

}
