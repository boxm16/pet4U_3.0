/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Replenishment;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Notes.NotesDao;
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
import org.springframework.stereotype.Repository;

@Repository
public class ReplenishmentDao {

    private LocalDateTime oldestReplenishmentDateTime;

    Item getItemForReplenishment(String altercode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
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
                    String position = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position = resultSet.getString("EXPR1").trim();
                    }
                    item.setPosition(position);
                    item.setQuantity(resultSet.getString("QTYBALANCE").trim());
                    String state = "";
                    if (resultSet.getString("EXPR2") != null) {
                        state = resultSet.getString("EXPR2").trim();
                    }
                    item.setState(state);
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
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    Replenishment getItemReplenishment(String code) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();
        Replenishment item = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM shelves_replenishment WHERE item_code='" + code + "' ;");

            while (resultSet.next()) {

                item = new Replenishment();
                item.setCode(resultSet.getString("item_code"));
                item.setReplenishmentQuantity(resultSet.getInt("quantity"));

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
            Logger.getLogger(ReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public String insertReplenishment(String itemCode, String replenishmentQuantity, String note) {
        try {

            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO shelves_replenishment (item_code, referal_date_time, quantity, note) VALUES (?, now() ,?,?)");

            itemInsertStatement.setString(1, itemCode);

            itemInsertStatement.setString(2, replenishmentQuantity);
            itemInsertStatement.setString(3, note);
            itemInsertStatement.execute();

            itemInsertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Replenishment Done Successfully";
    }

    public String updateReplenishment(String itemCode, String replenishmentQuantity, String note) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE shelves_replenishment  SET referal_date_time=now() , quantity=?, note=? WHERE item_code=?");

            updateStatement.setString(1, replenishmentQuantity);
            updateStatement.setString(2, note);
            updateStatement.setString(3, itemCode);
            updateStatement.executeUpdate();
            updateStatement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

        return "Replenishment Updated Successfully";
    }

    LinkedHashMap<String, Replenishment> getAllReplenishments() {
        oldestReplenishmentDateTime = LocalDateTime.now();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();
        LinkedHashMap<String, Replenishment> allReplenishments = new LinkedHashMap<String, Replenishment>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM shelves_replenishment;");

            while (resultSet.next()) {

                Replenishment item = new Replenishment();
                String itemCode = resultSet.getString("item_code");
                item.setCode(itemCode);
                item.setReplenishmentQuantity(resultSet.getInt("quantity"));
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
            Logger.getLogger(ReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allReplenishments;
    }

    public LinkedHashMap<String, Replenishment> addPet4uBasicData(LinkedHashMap<String, Replenishment> replenishments, StringBuilder inPartForSqlQuery) {
        StringBuilder query = new StringBuilder("SELECT * FROM WH1 WHERE  ALTERNATECODE IN ")
                .append(inPartForSqlQuery).append(";");
        //   System.out.println(query);
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                String referalAltercode = resultSet.getString("ALTERNATECODE").trim();
                String itemCode = resultSet.getString("ABBREVIATION").trim();

                if (replenishments.containsKey(referalAltercode)) {
                    Replenishment replenishment = replenishments.get(referalAltercode);
                    replenishment.setCode(itemCode);
                    replenishment.setDescription(resultSet.getString("NAME").trim());
                    String position = resultSet.getString("EXPR1").trim();

                    replenishment.setPosition(position);
                    replenishment.setQuantity(resultSet.getString("QTYBALANCE"));
                    String state = "";
                    if (resultSet.getString("EXPR2") != null) {
                        state = resultSet.getString("EXPR2").trim();
                    }
                    replenishment.setState(state);
                    replenishments.put(itemCode, replenishment);
                } else {
                    System.out.println("Something Wrong Here. Can't find referalAltercode in pet4u main database (WH1): " + referalAltercode);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replenishments;
    }

    LinkedHashMap<String, Replenishment> addSailsData(LinkedHashMap<String, Replenishment> replenishments, StringBuilder inPartForSqlQuery) {
        DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String oldestReplenishmentDateTimeString = oldestReplenishmentDateTime.format(CUSTOM_FORMATTER);  //2022-12-09 18:25:58

        //i need new linkedHashMap to set order for positions from pet4udatabase
        StringBuilder query
                = new StringBuilder("SELECT ABBREVIATION, DATE_TIME, QUANT1, DOCNAME FROM WH_SALES_DOCS WHERE  ABBREVIATION IN ")
                        .append(inPartForSqlQuery).append(" AND DATE_TIME >='" + oldestReplenishmentDateTimeString + "' ORDER BY DOCID;");
        System.out.println(query);
        ResultSet resultSet;
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                String itemCode = resultSet.getString("ABBREVIATION").trim();

                if (replenishments.containsKey(itemCode)) {
                    Replenishment replenishment = replenishments.get(itemCode);
                    LocalDateTime referralDateTime = replenishment.getDateTime();
                    String saleDateTimeStampString = resultSet.getString("DATE_TIME");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                    DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

                    LocalDateTime saleDateTime;
                    if (saleDateTimeStampString.length() == 23) {
                        saleDateTime = LocalDateTime.parse(saleDateTimeStampString, formatter2);
                    } else if (saleDateTimeStampString.length() == 22) {
                        saleDateTime = LocalDateTime.parse(saleDateTimeStampString, formatter3);
                    } else {
                        saleDateTime = LocalDateTime.parse(saleDateTimeStampString, formatter4);
                    }
                    if (saleDateTime.isAfter(referralDateTime) || saleDateTime.isEqual(referralDateTime)) {
                        int quantity = resultSet.getInt("QUANT1");
                        //   String creationUser = resultSet.getString("USER_");
                        String doctype = resultSet.getString("DOCNAME");
                        int sailsAfterReplenishment = replenishment.getSailsAfterReplenishment();

                        if (doctype.equals("ΚΑΠΔ") || doctype.equals("ΚΔΑΤ1")) {
                            sailsAfterReplenishment = sailsAfterReplenishment + quantity;
                        }
                        if (doctype.equals("ΚΑΕΛ") || doctype.equals("ΚΠΔΤ1")) {
                            sailsAfterReplenishment = sailsAfterReplenishment - quantity;
                        }
                        replenishment.setSailsAfterReplenishment(sailsAfterReplenishment);
                        replenishments.put(itemCode, replenishment);
                    }
                } else {
                    System.out.println("Something Wrong Here. Can't find item code  in pet4u main database (WH1): " + itemCode);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replenishments;
    }

    public String updateReplenishment(String itemCode, String minimalShelfStock) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE shelves_replenishment  SET minimal_stock=? WHERE item_code=?");

            updateStatement.setString(1, minimalShelfStock);
            updateStatement.setString(2, itemCode);
            updateStatement.executeUpdate();
            updateStatement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReplenishmentDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

        return "Replenishment Updated Successfully";
    }

    String deleteReplenishment(String itemCode) {
        String noteDeletionSql = "DELETE FROM shelves_replenishment WHERE itemCode='" + itemCode + "'";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(noteDeletionSql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        return "Replenishment Deleted Successfully";
    }

}
