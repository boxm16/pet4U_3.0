/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputOutput;

import DailySales.DailySale;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michail Sitmalidis
 */
@Repository
public class InputOutputDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    LinkedHashMap<LocalDate, InputOutput> fillSales(LinkedHashMap<LocalDate, InputOutput> inputOutputs, String itemCode, String startDate, String endDate) {

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM [petworld].[dbo].[WH_SALES_DOCS] WHERE ABBREVIATION='" + itemCode + "' "
                    + " AND DATE_TIME BETWEEN  '" + startDate + "' AND  '" + endDate + "' ORDER BY DATE_TIME;");

            LocalDate creationDate;
            while (resultSet.next()) {
                String creationDateTimeStampString = resultSet.getString("DATE_TIME");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

                LocalDateTime creationDateTime;
                if (creationDateTimeStampString.length() == 23) {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter2);
                } else if (creationDateTimeStampString.length() == 22) {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter3);
                } else {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter4);
                }

                creationDate = creationDateTime.toLocalDate();
                InputOutput inputOutput = inputOutputs.get(creationDate);

                if (inputOutput == null) {
                    System.out.println("Something Wrong. InputOutputDao. inputOutput=null:" + creationDate);
                } else {
                    DailySale dailySale = inputOutput.getDailySale();
                    String number = resultSet.getString("DOCNUMBER");
                    String doctype = resultSet.getString("DOCNAME");
                    double quantity = resultSet.getDouble("QUANT1");
                    if (number.equals("0")) {
                        dailySale.setPresoldQuantiy(quantity + dailySale.getPresoldQuantiy());
                        System.out.println("DOCNUMBER");
                    }
                    if (doctype.equals("ΚΑΠΔ") || doctype.equals("ΚΔΑΤ1")) {
                        dailySale.setSoldQuantiy(quantity + dailySale.getSoldQuantiy());
                    }
                    if (doctype.equals("ΚΑΕΛ") || doctype.equals("ΚΠΤΔ1")) {
                        dailySale.setSoldQuantiy(dailySale.getSoldQuantiy() - quantity);
                    }
                }

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(InputOutputDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inputOutputs;
    }

    LinkedHashMap<LocalDate, InputOutput> fillDeliveries(LinkedHashMap<LocalDate, InputOutput> inputOutputs, String itemCode, String startDate, String endDate) {

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT [DATEOFUPDATE], [ABBREVIATION], [QUANT1] FROM  [petworld].[dbo].[WH_DEPA]  WHERE [ABBREVIATION]='" + itemCode + "'  AND [DATEOFUPDATE] BETWEEN '" + startDate + "' AND '" + endDate + "' ORDER BY [DATEOFUPDATE];";

            ResultSet resultSet = statement.executeQuery(sql);
            LocalDate creationDate;
            while (resultSet.next()) {
                String creationDateTimeStampString = resultSet.getString("DATEOFUPDATE");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

                LocalDateTime creationDateTime;
                if (creationDateTimeStampString.length() == 23) {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter2);
                } else if (creationDateTimeStampString.length() == 22) {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter3);
                } else {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter4);
                }

                creationDate = creationDateTime.toLocalDate();
                InputOutput inputOutput = inputOutputs.get(creationDate);

                if (inputOutput == null) {
                    System.out.println("Something Wrong. InputOutputDao. inputOutput=null:" + creationDate);
                } else {
                    System.out.println("---" + resultSet.getString("QUANT1"));
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(InputOutputDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inputOutputs;
    }

}
