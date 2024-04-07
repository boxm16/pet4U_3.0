/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotSales;

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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class CamelotSalesDao {

    String insertNewUpload(String date, ArrayList<SoldItem> soldItems) {
        System.out.println("STARTING INSERTING UPLOADED DATA");
        System.out.println(" STARTING ADDING ITEMS TO 'camelot_month_sales' INSERTION BATCH");
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO camelot_month_sales (code, date, sales) VALUES (?,?,?)");
            int index = 0;
            for (SoldItem soldItem : soldItems) {

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

}
