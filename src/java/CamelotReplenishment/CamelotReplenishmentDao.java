/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotReplenishment;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CamelotReplenishmentDao {

    private LocalDateTime oldestReplenishmentDateTime;

    public Item getItemForReplenishment(String altercode) {

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();
        Item item = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
//maybe thre is some join, or other clause for things like this, but i dnt know yet, no time for search
            resultSet = statement.executeQuery("select ABBREVIATION from WH1 WHERE ALTERNATECODE='" + altercode + "';");
            String code = "";
            while (resultSet.next()) {
                code = resultSet.getString("ABBREVIATION");
            }
            resultSet = statement.executeQuery("select * from WH1 WHERE ABBREVIATION='" + code + "';");
            int index = 0;
            while (resultSet.next()) {
                if (index == 0) {
                    item = new Item();
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

                    item.setQuantity(resultSet.getString("QTYBALANCE").trim());

                    index++;
                }
                AltercodeContainer altercodeContainer = new AltercodeContainer();
                altercodeContainer.setAltercode(resultSet.getString("ALTERNATECODE").trim());
                if (resultSet.getString("CODEDESCRIPTION") == null) {
                    altercodeContainer.setStatus("");
                } else {
                    altercodeContainer.setStatus(resultSet.getString("CODEDESCRIPTION").trim());
                }
                if (item != null) {//It should never be, i mean, if there is an altercode, there is an item. But, just in any case
                    item.addAltercodeContainer(altercodeContainer);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    CamelotReplenishment getItemReplenishment(String code) {

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();
        CamelotReplenishment item = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM camelot_shelves_replenishment WHERE item_code='" + code + "' ;");

            while (resultSet.next()) {

                item = new CamelotReplenishment();
                item.setCode(resultSet.getString("item_code"));
                item.setReplenishmentUnit(resultSet.getString("replenishment_unit"));
                item.setReplenishmentQuantity(resultSet.getInt("replenishment_quantity"));
                item.setItemsInReplenishmentUnit(resultSet.getInt("items_int_replenishment_unit"));

                String dateTimeString = resultSet.getString("referal_date_time");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, format);
                item.setDateTime(dateTime);
                item.setMinimalShelfStock(resultSet.getInt("minimal_stock"));
                item.setNote(resultSet.getString("note"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public String insertReplenishment(String itemCode, String replenishmentUnit, String itemsInReplenishmentUnit, String minimalShelfStock, String replenishmentQuantity, String note) {
        LocalDateTime timeNow = LocalDateTime.now();
        try {

            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO camelot_shelves_replenishment (item_code, referal_date_time, replenishment_unit,items_int_replenishment_unit, minimal_stock,  replenishment_quantity, note) VALUES (?,?,?,?,?,?,?)");

            itemInsertStatement.setString(1, itemCode);
            itemInsertStatement.setString(2, timeNow.toString());
            itemInsertStatement.setString(3, replenishmentUnit);
            itemInsertStatement.setString(4, itemsInReplenishmentUnit);
            itemInsertStatement.setString(5, minimalShelfStock);
            itemInsertStatement.setString(6, replenishmentQuantity);
            itemInsertStatement.setString(7, note);
            itemInsertStatement.execute();

            itemInsertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Replenishment Done Successfully";
    }

    String updateReplenishment(String itemCode, String replenishmentQuantity, String note) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();
        LocalDateTime timeNow = LocalDateTime.now();
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE camelot_shelves_replenishment  SET referal_date_time=? , replenishment_quantity=?, note=? WHERE item_code=?");
            updateStatement.setString(1, timeNow.toString());
            updateStatement.setString(2, replenishmentQuantity);
            updateStatement.setString(3, note);
            updateStatement.setString(4, itemCode);
            updateStatement.executeUpdate();
            updateStatement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

        return "Camelot Replenishment Updated Successfully";
    }

    LinkedHashMap<String, CamelotReplenishment> getAllReplenishments() {
        oldestReplenishmentDateTime = LocalDateTime.now();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();
        LinkedHashMap<String, CamelotReplenishment> allReplenishments = new LinkedHashMap<String, CamelotReplenishment>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM camelot_shelves_replenishment;");

            while (resultSet.next()) {

                CamelotReplenishment item = new CamelotReplenishment();
                String itemCode = resultSet.getString("item_code");
                item.setCode(itemCode);
                item.setReplenishmentQuantity(resultSet.getInt("replenishment_quantity"));
                item.setItemsInReplenishmentUnit(resultSet.getInt("items_int_replenishment_unit"));

                item.setMinimalShelfStock(resultSet.getInt("minimal_stock"));
                String dateTimeString = resultSet.getString("referal_date_time");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, format);
                item.setDateTime(dateTime);
                item.setNote(resultSet.getString("note"));
                allReplenishments.put(itemCode, item);

                if (dateTime.isBefore(oldestReplenishmentDateTime)) {
                    oldestReplenishmentDateTime = dateTime;
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allReplenishments;
    }

    LinkedHashMap<String, CamelotReplenishment> addCamelotBasicData(LinkedHashMap<String, CamelotReplenishment> replenishments, StringBuilder inPartForSqlQuery) {
        StringBuilder query = new StringBuilder("SELECT * FROM WH1 WHERE  ALTERNATECODE IN ")
                .append(inPartForSqlQuery).append(";");
        //   System.out.println(query);
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                String referalAltercode = resultSet.getString("ALTERNATECODE").trim();
                String itemCode = resultSet.getString("ABBREVIATION").trim();

                if (replenishments.containsKey(referalAltercode)) {
                    CamelotReplenishment replenishment = replenishments.get(referalAltercode);
                    replenishment.setCode(itemCode);
                    replenishment.setDescription(resultSet.getString("NAME").trim());
                    String position_1 = "";
                    String position_2 = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position_1 = resultSet.getString("EXPR1").trim();
                    }
                    if (resultSet.getString("EXPR2") != null) {
                        position_2 = resultSet.getString("EXPR2").trim();
                    }
                    replenishment.setPosition(position_1 + position_2);

                    replenishment.setQuantity(resultSet.getString("QTYBALANCE").trim());
                    replenishments.put(itemCode, replenishment);
                } else {
                    System.out.println("Something Wrong Here. Can't find referalAltercode in Camelot main database (WH1): " + referalAltercode);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replenishments;
    }

    LinkedHashMap<String, CamelotReplenishment> addSailsData(LinkedHashMap<String, CamelotReplenishment> replenishments, StringBuilder inPartForSqlQueryByReferralAltercodes) {
        return replenishments;
    }

    LinkedHashMap<String, CamelotReplenishment> addVarPcData(LinkedHashMap<String, CamelotReplenishment> replenishments, StringBuilder inPartForSqlQueryByReferralAltercodes) {
        return replenishments;
    }

}
