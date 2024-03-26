/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eksagoges;

import MonthSales.MonthSalesDao;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class EksagogesDao {

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
}
