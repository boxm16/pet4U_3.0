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
import java.text.SimpleDateFormat;
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

    LinkedHashMap<String, ItemSales> getLastSixMonthsSales() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate startingDate = LocalDate.now();
        startingDate = startingDate.minusMonths(7);
        System.out.println(formatter.format(startingDate));

        LinkedHashMap<String, ItemSales> allItems = new LinkedHashMap<>();
        String sql = "SELECT * FROM month_sales WHERE date>" + startingDate;
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
                    ItemSales item = allItems.get(code);
                    Sales sales = new Sales();
                    sales.setEshopSales(eshopSales);
                    sales.setShopsSupply(shopsSupply);
                    item.addSales(saleDate, sales);
                    allItems.put(date, item);
                } else {
                    ItemSales item = new ItemSales();
                    item.setCode(code);

                    Sales sales = new Sales();
                    sales.setEshopSales(eshopSales);
                    sales.setShopsSupply(shopsSupply);
                    item.addSales(saleDate, sales);
                    allItems.put(date, item);
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
}
