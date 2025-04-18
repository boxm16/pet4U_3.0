package Notes;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Inventory.InventoryItem;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class NotesDao {

    public Item getItemForNote(String altercode) {
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

    String saveNote(String altercode, String note) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO notes (item_code, note) VALUES (?,?)");

            itemInsertStatement.setString(1, altercode);
            itemInsertStatement.setString(2, note);

            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Item Inventory Added Successfully";
    }

    ArrayList<InventoryItem> getAllNotes() {
        ArrayList<InventoryItem> inventories = new ArrayList<>();

        String sql = "SELECT * FROM notes ;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                InventoryItem inventoryItem = new InventoryItem();
                int id = resultSet.getInt("id");
                inventoryItem.setId(id);

                String itemCode = resultSet.getString("item_code");
                inventoryItem.setCode(itemCode.trim());
                inventoryItem.setNote(resultSet.getString("note"));

                inventories.add(inventoryItem);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventories;
    }

    public LinkedHashMap<String, Item> getpet4UItemsRowByRow() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select ABBREVIATION, ALTERNATECODE, NAME, EXPR1, EXPR2, QTYBALANCE   from WH1;");

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
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    void deleteNote(String id) {
        String noteDeletionSql = "DELETE FROM notes WHERE id='" + id + "'";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(noteDeletionSql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //-------------------------------------------------------
    public Item getCamelotItemForNote(String altercode) {
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

    public String saveCamelotNote(String altercode, String note) {
        try {

            String dateFormat = "yyyy-MM-dd HH:mm:ss";

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
            String formatedDateNow = formater.format(cal.getTime());

            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO camelot_notes (item_code, note, insertion_time) VALUES (?,?,?)");

            itemInsertStatement.setString(1, altercode);
            itemInsertStatement.setString(2, note);
            itemInsertStatement.setString(3, formatedDateNow);

            itemInsertStatement.execute();

            itemInsertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Camelot Note Added Successfully";
    }

    public ArrayList<InventoryItem> getAllCamelotNotes() {
        ArrayList<InventoryItem> inventories = new ArrayList<>();

        String sql = "SELECT * FROM camelot_notes WHERE deletion_time IS NULL;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                InventoryItem inventoryItem = new InventoryItem();
                int id = resultSet.getInt("id");
                inventoryItem.setId(id);

                String itemCode = resultSet.getString("item_code");
                inventoryItem.setCode(itemCode.trim());
                inventoryItem.setNote(resultSet.getString("note"));

                inventories.add(inventoryItem);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventories;
    }

    public void deleteCamelotNote(String id) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
        String formatedDateNow = formater.format(cal.getTime());

        String noteDeletionSql = "UPDATE camelot_notes SET deletion_time='" + formatedDateNow + "' WHERE id='" + id + "'";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(noteDeletionSql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAllCamelotNote() {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
        String formatedDateNow = formater.format(cal.getTime());

        String noteDeletionSql = "UPDATE camelot_notes SET deletion_time='" + formatedDateNow + "' WHERE deletion_time IS NULL";

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(noteDeletionSql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String addCamelotStockPosition(String itemCode, String position, String username) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO camelot_stock_positions (item_code, position, date_stamp, status, user) VALUES (?,?,?,?,?)");

            itemInsertStatement.setString(1, itemCode);
            itemInsertStatement.setString(2, position);
            itemInsertStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            itemInsertStatement.setString(4, "active");
            itemInsertStatement.setString(5, username);
            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Camelot Stock Position Added Successfully";

    }

    public LinkedHashMap<Integer, String> getStockPositions(Item item) {
        LinkedHashMap<Integer, String> stockPositions = new LinkedHashMap<>();

        String sql = "SELECT * FROM camelot_stock_positions WHERE item_code='" + item.getCode() + "' AND status='active' ;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");

                String position = resultSet.getString("position");
                stockPositions.put(id, position);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stockPositions;
    }

    public String deleteCamelotStockPosition(String id, String userName) {
        String sql = "UPDATE camelot_stock_positions SET status='deleted', user='" + userName + "'  WHERE id='" + id + "'";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Camelot Stock Position Deleted (Updated)";
    }

    public LinkedHashMap<String, LinkedHashMap<Integer, String>> getAllStockPositions() {
        LinkedHashMap<String, LinkedHashMap<Integer, String>> allPositions = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from camelot_stock_positions WHERE status='active';");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String itemCode = resultSet.getString("item_code");
                String position = resultSet.getString("position");
                if (allPositions.containsKey(itemCode)) {
                    LinkedHashMap<Integer, String> stockPosition = allPositions.get(itemCode);
                    stockPosition.put(id, position);
                    allPositions.put(itemCode, stockPosition);
                } else {
                    LinkedHashMap<Integer, String> stockPosition = new LinkedHashMap<>();
                    stockPosition.put(id, position);
                    allPositions.put(itemCode, stockPosition);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allPositions;
    }

    public LinkedHashMap<String, ArrayList<String>> getCamelotItemsByStockPositions() {
        LinkedHashMap<String, ArrayList<String>> allPositions = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from camelot_stock_positions WHERE status='active' ORDER BY position;");

            while (resultSet.next()) {
                //    int id = resultSet.getInt("id");
                String itemCode = resultSet.getString("item_code");
                String position = resultSet.getString("position");
                if (allPositions.containsKey(position)) {
                    ArrayList<String> itemCodes = allPositions.get(position);
                    itemCodes.add(itemCode);
                    allPositions.put(position, itemCodes);
                } else {
                    ArrayList<String> itemCodes = new ArrayList<>();
                    itemCodes.add(itemCode);
                    allPositions.put(position, itemCodes);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allPositions;
    }

    public LinkedHashMap<String, ArrayList<String>> camelotStockPositionsByItemCode() {
        LinkedHashMap<String, ArrayList<String>> allItems = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from camelot_stock_positions WHERE status='active' ORDER BY item_code;");

            while (resultSet.next()) {
                //    int id = resultSet.getInt("id");
                String itemCode = resultSet.getString("item_code");
                String position = resultSet.getString("position");
                if (allItems.containsKey(itemCode)) {
                    ArrayList<String> allPositions = allItems.get(itemCode);
                    allPositions.add(position);
                    allItems.put(itemCode, allPositions);
                } else {
                    ArrayList<String> allPositions = new ArrayList<>();
                    allPositions.add(position);
                    allItems.put(itemCode, allPositions);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allItems;
    }

    String saveNotForEndo(String altercode, String note) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO not_for_endo (item_code, note) VALUES (?,?)");

            itemInsertStatement.setString(1, altercode);
            itemInsertStatement.setString(2, note);

            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New 'Not For Endo' Saved Successfully";
    }

    public ArrayList<InventoryItem> getAllNotForEndo() {
        ArrayList<InventoryItem> inventories = new ArrayList<>();

        String sql = "SELECT * FROM not_for_endo ;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                InventoryItem inventoryItem = new InventoryItem();
                int id = resultSet.getInt("id");
                inventoryItem.setId(id);

                String itemCode = resultSet.getString("item_code");
                inventoryItem.setCode(itemCode.trim());
                inventoryItem.setNote(resultSet.getString("note"));

                inventories.add(inventoryItem);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventories;
    }

    void deleteNotForEndo(String id) {
        String noteDeletionSql = "DELETE FROM not_for_endo WHERE id='" + id + "'";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(noteDeletionSql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getAllNotForEndoIds() {
        ArrayList<String> ids = new ArrayList<>();

        String sql = "SELECT item_code FROM not_for_endo ;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                String itemCode = resultSet.getString("item_code");

                ids.add(itemCode);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ids;
    }

    public LinkedHashMap<String, Integer> getAllDeletedCamelotNotesBatches() {
        LinkedHashMap<String, Integer> allDeletedCamelotNotesBatches = new LinkedHashMap<>();

        String sql = "SELECT COUNT(deletion_time) AS count, deletion_time FROM camelot_notes WHERE  deletion_time IS NOT NULL  GROUP BY deletion_time ORDER BY deletion_time;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                int count = resultSet.getInt("count");
                String batch = resultSet.getString("deletion_time");

                allDeletedCamelotNotesBatches.put(batch, count);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allDeletedCamelotNotesBatches;

    }

    public ArrayList<InventoryItem> getDeletedCamelotNotesBatch(String batch) {

        ArrayList<InventoryItem> inventories = new ArrayList<>();

        String sql = "SELECT * FROM camelot_notes WHERE deletion_time ='" + batch + "';";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                InventoryItem inventoryItem = new InventoryItem();
                int id = resultSet.getInt("id");
                inventoryItem.setId(id);

                String itemCode = resultSet.getString("item_code");
                inventoryItem.setCode(itemCode.trim());
                inventoryItem.setNote(resultSet.getString("note"));
                //here i`ll just use those 2 field, because i dont want to create new fields in this class
                inventoryItem.setTimeStampString(resultSet.getString("insertion_time"));
                inventoryItem.setDateStampString(resultSet.getString("deletion_time"));

                inventories.add(inventoryItem);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(NotesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventories;

    }

}
