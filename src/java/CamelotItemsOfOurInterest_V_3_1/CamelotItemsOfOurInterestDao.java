/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotItemsOfOurInterest_V_3_1;

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
public class CamelotItemsOfOurInterestDao {

    LinkedHashMap<String, CamelotItemOfInterest> getCamelotItemsOfOurInterset() {
        LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest = new LinkedHashMap<>();
        String sql = "SELECT * FROM camelot_interest;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
                String referralAltercode = resultSet.getString("item_code");
                camelotItemOfInterest.setReferralAltercode(referralAltercode.trim());
                camelotItemOfInterest.setMinimalStock(resultSet.getInt("minimal_stock"));
                camelotItemOfInterest.setWeightCoefficient(resultSet.getInt("weight_coefficient"));
                camelotItemOfInterest.setOrderUnit(resultSet.getString("order_unit"));
                camelotItemOfInterest.setOrderQuantity(resultSet.getInt("order_quantity"));
                camelotItemOfInterest.setCamelotMinimalStock(resultSet.getInt("camelot_minimal_stock"));
                String note = resultSet.getString("note");
                if (note == null) {
                    note = "";
                }
                camelotItemOfInterest.setNote(note);
                camelotItemsOfInterest.put(referralAltercode, camelotItemOfInterest);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfOurInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return camelotItemsOfInterest;
    }

    LinkedHashMap<String, CamelotItemOfInterest> addPet4uBasicData(LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest, StringBuilder inPartForSqlQuery) {

        StringBuilder query
                = new StringBuilder("SELECT * FROM WH1 WHERE  ALTERNATECODE IN ")
                        .append(inPartForSqlQuery).append(" ORDER BY EXPR1");
        System.out.println(query);
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                String referalAltercode = resultSet.getString("ALTERNATECODE").trim();

                if (camelotItemsOfInterest.containsKey(referalAltercode)) {
                    CamelotItemOfInterest camelotItemOfInterest = camelotItemsOfInterest.get(referalAltercode);
                    camelotItemOfInterest.setCode(resultSet.getString("ABBREVIATION").trim());
                    camelotItemOfInterest.setDescription(resultSet.getString("NAME").trim());
                    String position = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position = resultSet.getString("EXPR1").trim();
                    } else {
                        System.out.println("Something Wrong Here. Position null for referalCode in pet4u main database (WH1): " + referalAltercode);
                    }
                    if (position.isEmpty()) {
                        System.out.println("Something Wrong Here.Empty position for referalCode in pet4u main database (WH1): " + referalAltercode);
                        continue;
                    }
                    camelotItemOfInterest.setPosition(position);
                    camelotItemOfInterest.setPet4uStock(resultSet.getDouble("QTYBALANCE"));
                    String state = "";
                    if (resultSet.getString("EXPR2") != null) {
                        state = resultSet.getString("EXPR2").trim();
                    }
                    camelotItemOfInterest.setState(state);
                    camelotItemsOfInterest.put(referalAltercode, camelotItemOfInterest);
                } else {
                    System.out.println("Something Wrong Here. Can't find referalAltercode in pet4u main database (WH1): " + referalAltercode);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfOurInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotItemsOfInterest;
    }

    LinkedHashMap<String, CamelotItemOfInterest> addCamelotData(LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfOurInterest, StringBuilder inPartForSqlQuery) {
        StringBuilder query
                = new StringBuilder("SELECT * FROM WH1 WHERE  ALTERNATECODE IN ")
                        .append(inPartForSqlQuery);
        System.out.println(query);
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                String referalAltercode = resultSet.getString("ALTERNATECODE").trim();

                if (camelotItemsOfOurInterest.containsKey(referalAltercode)) {
                    CamelotItemOfInterest camelotItemOfInterest = camelotItemsOfOurInterest.get(referalAltercode);

                    String position_1 = "";
                    String position_2 = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position_1 = resultSet.getString("EXPR1").trim();
                    }
                    if (resultSet.getString("EXPR2") != null) {
                        position_2 = resultSet.getString("EXPR2").trim();
                    }
                    camelotItemOfInterest.setCamelotPosition(position_1 + position_2);
                    camelotItemOfInterest.setCamelotStock(resultSet.getDouble("QTYBALANCE"));

                    camelotItemsOfOurInterest.put(referalAltercode, camelotItemOfInterest);
                } else {
                    System.out.println("Something Wrong Here. Can't find referalAltercode in camelot  database (WH1): " + referalAltercode);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfOurInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotItemsOfOurInterest;
    }

}
