/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonthSales;

import SalesX.SoldItem;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository

public class MonthSalesDao {

    public String insertNewUpload(String date, ArrayList<SoldItem> soldItems) {

        System.out.println("STARTING INSERTING UPLOADED DATA");
        System.out.println(" STARTING ADDING ITEMS TO 'month_sales' INSERTION BATCH");
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO month_sales (code, date,  eshop_sales, shops_supply) VALUES (?,?,?,?)");
            int index = 0;
            for (SoldItem soldItem : soldItems) {

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
            Logger.getLogger(MonthSalesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "SALES UPLOAD  EXECUTED SUCCESSFULLY.";
    }

    LinkedHashMap<String, MonthSales> getSales() {

        LinkedHashMap<String, MonthSales> allItems = new LinkedHashMap<>();
        String sql = "SELECT * FROM month_sales ;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

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

                int eshopSales = resultSet.getInt("eshop_sales");
                int shopsSupply = resultSet.getInt("shops_supply");

                if (allItems.containsKey(code)) {
                    MonthSales item = allItems.get(code);
                    Sales sales = new Sales();
                    sales.setEshopSales(eshopSales);
                    sales.setShopsSupply(shopsSupply);
                    item.addSales(saleDate, sales);
                    allItems.put(code, item);
                } else {
                    MonthSales item = new MonthSales();
                    item.setCode(code);

                    Sales sales = new Sales();
                    sales.setEshopSales(eshopSales);
                    sales.setShopsSupply(shopsSupply);
                    item.addSales(saleDate, sales);
                    allItems.put(code, item);
                }

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(MonthSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allItems;
    }

    public MonthSales getItemSales(String itemCode) {

        String sql = "SELECT * FROM month_sales WHERE code='" + itemCode + "';";
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

                int eshopSales = resultSet.getInt("eshop_sales");
                int shopsSupply = resultSet.getInt("shops_supply");

                item.setCode(code);

                Sales sales = new Sales();
                sales.setEshopSales(eshopSales);
                sales.setShopsSupply(shopsSupply);
                item.addSales(saleDate, sales);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(MonthSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return item;
    }

    public LinkedHashMap<String, MonthSales> getLastMonthsSales11(int months) {

        LinkedHashMap<String, MonthSales> allItems = new LinkedHashMap<>();
        String sql = "SELECT * FROM month_sales ORDER BY date DESC;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            String currentDate = "FakeDate";
            int currentMonth = 0;
            while (resultSet.next()) {
                String code = resultSet.getString("code");

                String date = resultSet.getString("date");
                if (!currentDate.equals(date)) {
                    if (currentMonth > months) {
                        return allItems;
                    }
                    currentMonth++;
                    currentDate = date;
                }
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate saleDate = LocalDate.parse(date, formatter2);

                int eshopSales = resultSet.getInt("eshop_sales");
                int shopsSupply = resultSet.getInt("shops_supply");

                if (allItems.containsKey(code)) {
                    MonthSales item = allItems.get(code);
                    Sales sales = new Sales();
                    sales.setEshopSales(eshopSales);
                    sales.setShopsSupply(shopsSupply);
                    item.addSales(saleDate, sales);
                    allItems.put(code, item);
                } else {
                    MonthSales item = new MonthSales();
                    item.setCode(code);

                    Sales sales = new Sales();
                    sales.setEshopSales(eshopSales);
                    sales.setShopsSupply(shopsSupply);
                    item.addSales(saleDate, sales);
                    allItems.put(code, item);
                }

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(MonthSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allItems;
    }

    public ArrayList<String> getSalesPeriod() {
        ArrayList<String> salesPeriod = new ArrayList();
        String sql = "SELECT DISTINCT date FROM month_sales;";
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
            Logger.getLogger(MonthSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return salesPeriod;
    }

    public LinkedHashMap<String, Double> getLast30DaysSales(String itemCode) {

        LinkedHashMap<String, Double> daysSales = new LinkedHashMap<>();
        LocalDate date = LocalDate.now();
        for (int x = 31; x > 0; x--) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate d = date.minusDays(x);
            String formattedString = d.format(formatter);
            daysSales.put(formattedString, 0.0);
        }
        LocalDate firstDate = date.minusDays(31);
        LocalDate lastDate = date.minusDays(1);

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM [petworld].[dbo].[WH_SALES_VAR] WHERE ABBREVIATION='" + itemCode + "' "
                    + " AND ENTRYDATE >= '" + firstDate + "' "
                    + "AND ENTRYDATE <= '" + lastDate + "'  ORDER BY ENTRYDATE DESC;");

            while (resultSet.next()) {
                String day = resultSet.getString("ENTRYDATE").trim();
                double sales = resultSet.getDouble("QTY");
                daysSales.put(day, sales);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(MonthSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daysSales;
    }
}
