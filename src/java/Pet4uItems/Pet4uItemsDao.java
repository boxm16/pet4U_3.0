package Pet4uItems;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemOfInterest;
import Service.DatabaseConnectionFactory;
import TechMan.TechManDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository

public class Pet4uItemsDao {

    public LinkedHashMap<String, Item> getAllItems() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 ORDER BY EXPR1;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                Item item = null;
                if (!items.containsKey(code)) {
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
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    LinkedHashMap<String, Item> getWeightAllItems() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();

        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        String sql = "SELECT * FROM WH1 WHERE ALTERNATECODE LIKE '%WE%'";
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                Item item = new Item();
                item.setCode(resultSet.getString("ABBREVIATION").trim());
                item.setDescription(resultSet.getString("NAME").trim());

                if (resultSet.getString("EXPR1") != null) {
                    item.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    item.setPosition("");
                }
                item.setQuantity(resultSet.getString("QTYBALANCE").trim());
                items.put(altercode, item);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;

    }

    LinkedHashMap<String, CamelotItemOfInterest> getNegativeItems() {
        LinkedHashMap<String, CamelotItemOfInterest> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 WHERE QTYBALANCE<0 ORDER BY EXPR1;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                CamelotItemOfInterest item;
                String description = resultSet.getString("NAME").trim();
                if (description.equals("ΕΥΡΩΠΑΛΕΤΕΣ")
                        || description.equals("Περιβ. Τέλος Πλαστ. Σακούλα(16μ)")
                        || description.equals("ΜΕΤΑΦΟΡΙΚΑ ΕΞΟΔΑ 23%")
                        || description.equals("ΠΑΛΕΤΕΣ EURO")
                        || description.equals("ΠΑΛΕΤΕΣ CHEP")
                        || description.equals("ΜΕΤΑΦΟΡΙΚΑ ΕΞΟΔΑ")
                        || description.equals("ΠΑΛΕΤΕΣ ΜΠΛΕ ( RERFECT PET )")
                        || description.contains("ΠΑΛΕΤΕΣ")) {
                    //do nothing
                } else {
                    if (!items.containsKey(code)) {
                        item = new CamelotItemOfInterest();
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
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    LinkedHashMap<String, Item> getAllItemsWithPosition() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 ORDER BY EXPR1;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                Item item = null;
                if (!items.containsKey(code)) {
                    item = new Item();
                    item.setCode(resultSet.getString("ABBREVIATION").trim());
                    item.setDescription(resultSet.getString("NAME").trim());
                    String position = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position = resultSet.getString("EXPR1").trim();
                    }
                    if (position.isEmpty()) {
                        continue;
                    }
                    item.setPosition(position);
                    item.setQuantity(resultSet.getString("QTYBALANCE").trim());
                    String state = "";
                    if (resultSet.getString("EXPR2") != null) {
                        state = resultSet.getString("EXPR2").trim();
                    }
                    item.setState(state);
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
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    String updateItemsState(LinkedHashMap<String, Item> pet4uAllItems) {

        TechManDao techManDao = new TechManDao();
        techManDao.deletePet4uItemStateDatabaseTables();
        techManDao.createPet4uItemStateDatabaseTables();

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement incertionPreparedStatement = connection.prepareStatement("INSERT INTO delivery_title (id, number,supplier, note) VALUES(?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            for (Map.Entry< String, Item> itemEntry : pet4uAllItems.entrySet()) {

                incertionPreparedStatement.setString(1, itemEntry.getValue().getCode());
                incertionPreparedStatement.setString(2, itemEntry.getValue().getState());

                incertionPreparedStatement.addBatch();

            }

            //Executing the batch
            incertionPreparedStatement.executeBatch();

            System.out.println(" Batch Insertion: DONE");

            //Saving the changes
            connection.commit();
            //  deleteTripPeriodPreparedStatement.close();
            // deleteTripVoucherPreparedStatement.close();
            incertionPreparedStatement.close();

            connection.close();
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }
    }

}
