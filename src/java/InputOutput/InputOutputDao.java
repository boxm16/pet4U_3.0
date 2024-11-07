/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputOutput;

import CamelotItemsOfInterest.ItemSnapshot;
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
                    double delivery = inputOutput.getDelivery();
                    delivery = delivery + Double.parseDouble(resultSet.getString("QUANT1"));
                    inputOutput.setDelivery(delivery);
                    inputOutputs.put(creationDate, inputOutput);
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

    LinkedHashMap<LocalDate, InputOutput> fillEndoParalaves(LinkedHashMap<LocalDate, InputOutput> inputOutputs, String itemCode, String startDate, String endDate) {

        startDate = startDate + " 00:00:00.000";
        endDate = endDate + " 23:59:59.999";
        String query = "SELECT    [DOCDATE], [DATE_TIME], [ABBREVIATION], [QUANTITY]  FROM [petworld].[dbo].[WH_ENDP]"
                + " WHERE [ABBREVIATION]='" + itemCode + "' AND DATE_TIME BETWEEN '" + startDate + "' AND '" + endDate + "' ORDER BY [DATE_TIME];";

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
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
                    double endoParalavi = inputOutput.getEndoParalavi();
                    endoParalavi = endoParalavi + Double.parseDouble(resultSet.getString("QUANTITY"));
                    inputOutput.setEndoParalavi(endoParalavi);
                    inputOutputs.put(creationDate, inputOutput);
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

    LinkedHashMap<LocalDate, InputOutput> fillEndoApostoles(LinkedHashMap<LocalDate, InputOutput> inputOutputs, String itemCode, String startDate, String endDate) {

        startDate = startDate + " 00:00:00.000";
        endDate = endDate + " 23:59:59.999";
        String query = "SELECT    [DATE_TIME],  [ABBREVIATION], [QUANTITY]  FROM [petworld].[dbo].[WH_ENDA_VAR]"
                + " WHERE [ABBREVIATION]='" + itemCode + "' AND DATE_TIME BETWEEN '" + startDate + "' AND '" + endDate + "' ORDER BY [DATE_TIME];";

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
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
                    double endoApostoli = inputOutput.getEndoApostoli();
                    endoApostoli = endoApostoli + Double.parseDouble(resultSet.getString("QUANTITY"));
                    inputOutput.setEndoApostoli(endoApostoli);
                    inputOutputs.put(creationDate, inputOutput);
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

    LinkedHashMap<LocalDate, ItemSnapshot> fillSnapshots(LinkedHashMap<LocalDate, InputOutput> inputOutputs, String itemCode) {
        LinkedHashMap<LocalDate, ItemSnapshot> snapshots = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        LocalDate nowDate = LocalDate.now();
        //  LocalDate firstDate = date.minusDays(30);
        //  LocalDate lastDate = date.minusDays(1);
        LocalDate date = LocalDate.now();
        LocalDate firstDate = LocalDate.parse("2023-09-12");

        while (date.isAfter(firstDate)) {
            date = date.minusDays(1);

            snapshots.put(date, null);
        }

        String sql = "SELECT * FROM item_state_full_version WHERE item_code='" + itemCode + "' and date_stamp between '2023-09-11' AND '" + nowDate + "' ORDER BY date_stamp DESC;";
        // System.out.println("SQL: " + sql);
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemSnapshot itemSnapshot = new ItemSnapshot();

                String dateStamp = resultSet.getString("date_stamp");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date1 = LocalDate.parse(dateStamp, formatter);

                String quantity = resultSet.getString("item_stock");
                String state = resultSet.getString("state");
                String position = resultSet.getString("position");

                itemSnapshot.setDateStamp(dateStamp);
                itemSnapshot.setState(state);
                itemSnapshot.setPosition(position);
                itemSnapshot.setQuantity(quantity);

                itemSnapshot.setInputOutput(inputOutputs.get(date1));

                snapshots.put(date1, itemSnapshot);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(InputOutputDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return snapshots;
    }

    LinkedHashMap<LocalDate, ItemSnapshot> fillSnapshots(LinkedHashMap<LocalDate, InputOutput> inputOutputs, String itemCode, String startDate, String endDate) {
        LinkedHashMap<LocalDate, ItemSnapshot> snapshots = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        LocalDate nowDate = LocalDate.now();
        //  LocalDate firstDate = date.minusDays(30);
        //  LocalDate lastDate = date.minusDays(1);

//     LocalDate date = LocalDate.now();
        // LocalDate firstDate = LocalDate.parse("2023-09-12");
        LocalDate date = LocalDate.parse(startDate);
        LocalDate firstDate = LocalDate.parse(endDate);
        while (date.isAfter(firstDate)) {
            date = date.minusDays(1);

            snapshots.put(date, null);
        }

        String sql = "SELECT * FROM item_state_full_version WHERE item_code='" + itemCode + "' and date_stamp between '" + startDate + "' AND '" + endDate + "' ORDER BY date_stamp DESC;";
        // System.out.println("SQL: " + sql);
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemSnapshot itemSnapshot = new ItemSnapshot();

                String dateStamp = resultSet.getString("date_stamp");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date1 = LocalDate.parse(dateStamp, formatter);

                String quantity = resultSet.getString("item_stock");
                String state = resultSet.getString("state");
                String position = resultSet.getString("position");

                itemSnapshot.setDateStamp(dateStamp);
                itemSnapshot.setState(state);
                itemSnapshot.setPosition(position);
                itemSnapshot.setQuantity(quantity);

                itemSnapshot.setInputOutput(inputOutputs.get(date1));

                snapshots.put(date1, itemSnapshot);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(InputOutputDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return snapshots;
    }

    LinkedHashMap<String, InputOutputContainer> fillInputOutputContainersWithSales(LinkedHashMap<String, InputOutputContainer> inputOutputContainers, String startDate, String endDate) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM [petworld].[dbo].[WH_SALES_DOCS] WHERE "
                    + " DATE_TIME BETWEEN  '" + startDate + "' AND  '" + endDate + "' ORDER BY DATE_TIME;");

            LocalDate creationDate;
            while (resultSet.next()) {
                String itemCode = resultSet.getString("ABBREVIATION");

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
                InputOutputContainer ioc = inputOutputContainers.get(itemCode);
                if (ioc == null) {
                    System.out.println("Item Code is null/is not in inputOutputContainers:" + itemCode);
                    continue;
                }
                System.out.println("ITEM CODE NOW:" + itemCode);
                LinkedHashMap<LocalDate, InputOutput> inputOutputs = ioc.getInputOutputs();
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

        return inputOutputContainers;
    }

    LinkedHashMap<LocalDate, ItemSnapshot> combineInputOutputContainersWithSnapshots(LinkedHashMap<String, InputOutputContainer> inputOutputContainers, String startDate, String endDate) {
        LinkedHashMap<LocalDate, ItemSnapshot> snapshots = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        LocalDate nowDate = LocalDate.now();
        //  LocalDate firstDate = date.minusDays(30);
        //  LocalDate lastDate = date.minusDays(1);

//     LocalDate date = LocalDate.now();
        // LocalDate firstDate = LocalDate.parse("2023-09-12");
        LocalDate date = LocalDate.parse(startDate);
        LocalDate firstDate = LocalDate.parse(endDate);
        while (date.isAfter(firstDate)) {
            date = date.minusDays(1);

            snapshots.put(date, null);
        }

        String sql = "SELECT * FROM item_state_full_version WHERE  date_stamp between '" + startDate + "' AND '" + endDate + "' ORDER BY date_stamp DESC;";
        // System.out.println("SQL: " + sql);
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemSnapshot itemSnapshot = new ItemSnapshot();

                String dateStamp = resultSet.getString("date_stamp");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date1 = LocalDate.parse(dateStamp, formatter);

                String quantity = resultSet.getString("item_stock");
                String state = resultSet.getString("state");
                String position = resultSet.getString("position");

                itemSnapshot.setDateStamp(dateStamp);
                itemSnapshot.setState(state);
                itemSnapshot.setPosition(position);
                itemSnapshot.setQuantity(quantity);

                itemSnapshot.setInputOutputContainer(inputOutputContainers.get(date1));

                snapshots.put(date1, itemSnapshot);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(InputOutputDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return snapshots;
    }

}
