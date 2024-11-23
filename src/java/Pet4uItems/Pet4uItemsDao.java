package Pet4uItems;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemOfInterest;
import CamelotItemsOfInterest.ItemSnapshot;
import Service.DatabaseConnectionFactory;
import TechMan.TechManDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            ResultSet resultSet = statement.executeQuery("select * from WH1  ORDER BY EXPR1;");

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
                    item.setQuantity(resultSet.getString("QTYBALANCE"));
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

    public LinkedHashMap<String, Item> getAllActiveItems() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1  ORDER BY EXPR1;");

            while (resultSet.next()) {
                if (resultSet.getString("DISABLED").equals("1")) {
                    continue;
                }
                //diklida asfalias
                if (resultSet.getString("QTYBALANCE") == null) {
                    continue;
                }

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
                    item.setQuantity(resultSet.getString("QTYBALANCE"));
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
                    item.setQuantity(resultSet.getString("QTYBALANCE"));
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

    public String insertPet4uItemsSnapshot(LinkedHashMap<String, Item> pet4uAllItems) {

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement incertionPreparedStatement = connection.prepareStatement("INSERT INTO item_state (item_code, date_stamp, state, position, site_state, item_stock ) VALUES(?,?,?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            for (Map.Entry< String, Item> itemEntry : pet4uAllItems.entrySet()) {
                LocalDate nowDate = LocalDate.now().minusDays(1);

                incertionPreparedStatement.setString(1, itemEntry.getValue().getCode());
                incertionPreparedStatement.setString(2, nowDate.toString());
                incertionPreparedStatement.setString(3, itemEntry.getValue().getState());
                incertionPreparedStatement.setString(4, itemEntry.getValue().getPosition());
                incertionPreparedStatement.setString(5, null);
                incertionPreparedStatement.setString(6, itemEntry.getValue().getQuantity());

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

    String createPet4uItemStateDatabaseTables() {
        String query = "CREATE TABLE item_state("
                + "item_code VARCHAR (100) NOT NULL, "
                + "state VARCHAR (30)  NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();

            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'item_state' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'item_state' could not be created:" + ex;
        }
    }

    public String deletePet4uItemStateDatabaseTables() {
        String query = "DROP TABLE item_state";

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();

            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'item_state' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'item_state' could not be deleted:" + ex;
        }
    }

    LinkedHashMap<String, String> getItemsStateSnapshot() {
        LinkedHashMap<String, String> itemsStateSnapshot = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select item_code, state from item_state;");

            while (resultSet.next()) {
                String code = resultSet.getString("item_code");
                String state = resultSet.getString("state");

                itemsStateSnapshot.put(code, state);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemsStateSnapshot;
    }

    public LinkedHashMap<String, Item> getOnlyProsfores() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 WHERE EXPR2='ΠΡΟΣΦΟΡΑ' ORDER BY EXPR1;");

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

    LinkedHashMap<String, Item> getOffSiteItems() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 WHERE EXPR2='OFF SITE' ORDER BY EXPR1;");

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

    public ArrayList<ItemSnapshot> getItemSnapshots(String code) {
        ArrayList<ItemSnapshot> itemSnapshots = new ArrayList<>();

        String sql = "SELECT * FROM item_state_full_version WHERE item_code='" + code + "' ORDER BY date_stamp;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ItemSnapshot item = new ItemSnapshot();

                String dateStamp = resultSet.getString("date_stamp");
                String quantity = resultSet.getString("item_stock");
                String state = resultSet.getString("state");

                item.setDateStamp(dateStamp);
                item.setState(state);
                item.setQuantity(quantity);

                itemSnapshots.add(item);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemSnapshots;
    }

    public LinkedHashMap<LocalDate, ItemSnapshot> getLast100DaysSnapshots(String code) {
        LinkedHashMap<LocalDate, ItemSnapshot> last100DaysSnapshots = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        LocalDate startDate = LocalDate.now();
        //  LocalDate firstDate = date.minusDays(30);
        //  LocalDate lastDate = date.minusDays(1);
        LocalDate endDate = LocalDate.now();
        for (int x = 100; x > 0; x--) {

            last100DaysSnapshots.put(endDate, null);
            endDate = endDate.minusDays(1);
        }

        String sql = "SELECT * FROM item_state WHERE item_code='" + code + "' and date_stamp between '" + endDate + "' AND '" + startDate + "' ORDER BY date_stamp DESC;";
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

                last100DaysSnapshots.put(date1, itemSnapshot);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return last100DaysSnapshots;
    }

    public LinkedHashMap<String, Item> getPet4UItemsRowByRow() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                Item item = new Item();
                item.setCode(resultSet.getString("ABBREVIATION").trim());
                String description = resultSet.getString("NAME").trim();
                description = description.replace("\"", "'");//replaces all occurrences of ' `  
                item.setDescription(description);

                if (resultSet.getString("EXPR1") != null) {
                    item.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    item.setPosition("");
                }
                item.setQuantity(resultSet.getString("QTYBALANCE"));
                String state = "";
                if (resultSet.getString("EXPR2") != null) {
                    state = resultSet.getString("EXPR2").trim();
                }
                item.setState(state);
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

    public LinkedHashMap<LocalDate, ItemSnapshot> getItemSnapshotsFullVersion(String code) {
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

        String sql = "SELECT * FROM item_state_full_version WHERE item_code='" + code + "' and date_stamp between '2023-09-11' AND '" + nowDate + "' ORDER BY date_stamp DESC;";
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

                snapshots.put(date1, itemSnapshot);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return snapshots;
    }

    public String insertPet4uItemsSnapshotFullVersion(LinkedHashMap<String, Item> pet4uAllItems) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement incertionPreparedStatement = connection.prepareStatement("INSERT INTO item_state_full_version (item_code, date_stamp, state, position, site_state, item_stock ) VALUES(?,?,?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            for (Map.Entry< String, Item> itemEntry : pet4uAllItems.entrySet()) {
                LocalDate nowDate = LocalDate.now().minusDays(1);

                incertionPreparedStatement.setString(1, itemEntry.getValue().getCode());
                incertionPreparedStatement.setString(2, nowDate.toString());
                incertionPreparedStatement.setString(3, itemEntry.getValue().getState());
                incertionPreparedStatement.setString(4, itemEntry.getValue().getPosition());
                incertionPreparedStatement.setString(5, null);
                incertionPreparedStatement.setString(6, itemEntry.getValue().getQuantity());

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
