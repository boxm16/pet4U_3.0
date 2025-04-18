/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotSales;

import BasicModel.AltercodeContainer;
import MonthSales.MonthSales;
import MonthSales.Sales;
import SalesX.SoldItem;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class CamelotSalesDao {

    String insertNewUpload(String date, LinkedHashMap<String, SoldItem> soldItems) {
        System.out.println("STARTING INSERTING UPLOADED DATA");
        System.out.println(" STARTING ADDING ITEMS TO 'camelot_month_sales' INSERTION BATCH");
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO camelot_month_sales (code, date, sales) VALUES (?,?,?)");
            int index = 0;
            for (Map.Entry<String, SoldItem> soldItemEntry : soldItems.entrySet()) {
                SoldItem soldItem = soldItemEntry.getValue();
                itemInsertStatement.setString(1, soldItem.getCode());
                itemInsertStatement.setString(2, date);
                itemInsertStatement.setDouble(3, soldItem.getEshopSales());

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

            System.out.println("Camelot 'sales' INSERTION BATCH EXECUTED.");
            System.out.println("-----------------------------------");
        } catch (SQLException ex) {
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "CAMELOT SALES UPLOAD  EXECUTED SUCCESSFULLY.";
    }

    public ArrayList<String> getSalesPeriod() {
        ArrayList<String> salesPeriod = new ArrayList();
        String sql = "SELECT DISTINCT date FROM camelot_month_sales;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            System.out.println("SALES REFERAL MONTHS");
            while (resultSet.next()) {

                String date = resultSet.getString("date");
                System.out.println(date);
                salesPeriod.add(date);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return salesPeriod;
    }

    public MonthSales getItemSales(String itemCode) {

        String sql = "SELECT * FROM camelot_month_sales WHERE code='" + itemCode + "';";
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        MonthSales item = new MonthSales();
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String code = resultSet.getString("code");

                String date = resultSet.getString("date");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate saleDate = LocalDate.parse(date, formatter2);

                int eshopSales = resultSet.getInt("sales");

                item.setCode(code);

                Sales sales = new Sales();
                sales.setEshopSales(eshopSales);

                item.addSales(saleDate, sales);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return item;
    }

    public LinkedHashMap<String, SoldItem> getCamelotItemsForSales() {
        LinkedHashMap<String, SoldItem> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 ORDER BY EXPR1, EXPR2;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                SoldItem item = null;
                if (!items.containsKey(code)) {
                    item = new SoldItem();
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
                    item.setQuantity(resultSet.getString("QTYBALANCE"));
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

    LinkedHashMap<String, SoldItem> getMonthSalesFromMicrosoftDB(String dateString, LinkedHashMap<String, SoldItem> camelotAllItemsForSales) {
        LocalDate date = LocalDate.parse(dateString);
        LocalDate firstDate = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfMonth());

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ITEMCODE, SUM(QTY) AS SALES"
                    + "  FROM [fotiou].[dbo].[WH_SALES] WHERE ENTRYDATE >= '" + firstDate + "' "
                    + "AND ENTRYDATE <= '" + lastDate + "' group by ITEMCODE order by ITEMCODE;");

            while (resultSet.next()) {
                String code = resultSet.getString("ITEMCODE").trim();
                SoldItem soldItem = camelotAllItemsForSales.get(code);
                if (soldItem == null) {
                    soldItem = new SoldItem();
                    soldItem.setCode(code);
                }
                double sales = resultSet.getDouble("SALES");
                //   System.out.println(code+" "+sales);
                soldItem.setEshopSales(sales);
                camelotAllItemsForSales.put(code, soldItem);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotAllItemsForSales;

    }

    LinkedHashMap<String, SoldItem> getSales(LinkedHashMap<String, SoldItem> camelotItemsForSales, List<String> sixMonthsPeriod) {

        String firstDate = sixMonthsPeriod.get(0);
        String lastDate = sixMonthsPeriod.get(5);

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

    public LinkedHashMap<String, Double> getLast30DaysSales(String itemCode) {

        LinkedHashMap<String, Double> daysSales = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();

        LocalDate date = LocalDate.now();
        LocalDate firstDate = date.minusDays(31);
        LocalDate lastDate = date.minusDays(0);

        for (int x = 30; x > 0; x--) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String formattedString = date.format(formatter) + " 00:00:00.0";
            //   System.out.println(formattedString);
            daysSales.put(formattedString, 0.0);
            date = date.minusDays(1);
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM [fotiou].[dbo].[WH_SALES] WHERE ITEMCODE='" + itemCode + "' "
                    + " AND ENTRYDATE >= '" + firstDate + "' "
                    + "AND ENTRYDATE <= '" + lastDate + "'  ORDER BY ENTRYDATE DESC;");

            while (resultSet.next()) {
                String day = resultSet.getString("ENTRYDATE").trim();
                //         System.out.println(day);
                double sales = resultSet.getDouble("QTY");
                daysSales.put(day, sales);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daysSales;
    }

}
