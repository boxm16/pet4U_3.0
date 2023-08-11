/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Search.SearchDao;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class InventoryDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    public Item getItemForInventory(String altercode) {
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
            Logger.getLogger(SearchDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public String saveItemInventory(String altercode, String currentDate, String currentTime, String systemStock, String realStock, String note) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO inventory (item_code, date_stamp, time_stamp, system_stock, real_stock, note) VALUES (?,?,?,?,?,?)");

            itemInsertStatement.setString(1, altercode);
            itemInsertStatement.setString(2, currentDate);
            itemInsertStatement.setString(3, currentTime);
            itemInsertStatement.setString(4, systemStock);
            itemInsertStatement.setString(5, realStock);
            itemInsertStatement.setString(6, note);

            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Item Inventory Added Successfully";
    }

    ArrayList<InventoryItem> getAllInventories() {
        ArrayList<InventoryItem> inventories = new ArrayList<>();

        String sql = "SELECT * FROM inventory ;";
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

                inventoryItem.setDateStampString(resultSet.getString("date_stamp"));
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("date_stamp"));
                } catch (ParseException ex) {
                    Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                inventoryItem.setDateStamp(date);

                inventoryItem.setTimeStampString(resultSet.getString("time_stamp"));

                SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("HH:mm:ss");

                Date dateTime = null;
                try {
                    dateTime = dateTimeFormatter.parse(resultSet.getString("time_stamp"));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                inventoryItem.setTimeStamp(dateTime);
                inventoryItem.setSystemStock(resultSet.getString("system_stock"));
                inventoryItem.setRealStock(resultSet.getString("real_stock"));
                inventoryItem.setNote(resultSet.getString("note"));
                inventoryItem.setInventarizationState(resultSet.getString("state"));
                inventories.add(inventoryItem);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventories;

    }

    ArrayList<InventoryItem> getAllActiveInventories() {
        ArrayList<InventoryItem> inventories = new ArrayList<>();

        String sql = "SELECT * FROM inventory";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String state = resultSet.getString("state");
                if (state == null) {

                    InventoryItem inventoryItem = new InventoryItem();
                    int id = resultSet.getInt("id");
                    inventoryItem.setId(id);

                    String itemCode = resultSet.getString("item_code");
                    inventoryItem.setCode(itemCode.trim());

                    inventoryItem.setDateStampString(resultSet.getString("date_stamp"));
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("date_stamp"));
                    } catch (ParseException ex) {
                        Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    inventoryItem.setDateStamp(date);

                    inventoryItem.setTimeStampString(resultSet.getString("time_stamp"));

                    SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("HH:mm:ss");

                    Date dateTime = null;
                    try {
                        dateTime = dateTimeFormatter.parse(resultSet.getString("time_stamp"));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    inventoryItem.setTimeStamp(dateTime);
                    inventoryItem.setSystemStock(resultSet.getString("system_stock"));
                    inventoryItem.setRealStock(resultSet.getString("real_stock"));
                    inventoryItem.setNote(resultSet.getString("note"));

                    inventoryItem.setInventarizationState(state);
                    inventories.add(inventoryItem);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventories;

    }

    public LinkedHashMap<String, Item> getpet4UItemsRowByRow() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

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
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    void deleteInventoryItem(String id) {

        String inventoryItemDeletionSql = "DELETE FROM inventory WHERE id='" + id + "'";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(inventoryItemDeletionSql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void archivizeInventoryItem(String id) {
        String inventoryItemArchivizationSql = "UPDATE inventory SET state='archive' WHERE id='" + id + "'";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(inventoryItemArchivizationSql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<InventoryItem> getInventories(ArrayList<String> inventoryItemsIdsArray) {
        ArrayList<InventoryItem> inventories = new ArrayList<>();

        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT * FROM inventory WHERE ");
        StringBuilder queryBuilderIdsPart = buildStringFromArrayList(inventoryItemsIdsArray);
        StringBuilder query = queryBuilderInitialPart.append(" id IN ").append(queryBuilderIdsPart);

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                String state = resultSet.getString("state");
                if (state == null) {

                    InventoryItem inventoryItem = new InventoryItem();
                    int id = resultSet.getInt("id");
                    inventoryItem.setId(id);

                    String itemCode = resultSet.getString("item_code");
                    inventoryItem.setCode(itemCode.trim());

                    inventoryItem.setDateStampString(resultSet.getString("date_stamp"));
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("date_stamp"));
                    } catch (ParseException ex) {
                        Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    inventoryItem.setDateStamp(date);

                    inventoryItem.setTimeStampString(resultSet.getString("time_stamp"));

                    SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("HH:mm:ss");

                    Date dateTime = null;
                    try {
                        dateTime = dateTimeFormatter.parse(resultSet.getString("time_stamp"));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    inventoryItem.setTimeStamp(dateTime);
                    inventoryItem.setSystemStock(resultSet.getString("system_stock"));
                    inventoryItem.setRealStock(resultSet.getString("real_stock"));
                    inventoryItem.setNote(resultSet.getString("note"));

                    inventoryItem.setInventarizationState(state);
                    inventories.add(inventoryItem);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventories;
    }

    private StringBuilder buildStringFromArrayList(ArrayList<String> arrayList) {

        StringBuilder stringBuilder = new StringBuilder("(");
        if (arrayList.isEmpty()) {
            stringBuilder.append(")");
            return stringBuilder;
        }
        int x = 0;
        for (String entry : arrayList) {
            if (x == 0) {
                stringBuilder.append("'").append(entry).append("'");
            } else {
                stringBuilder.append(",'").append(entry).append("'");
            }
            if (x == arrayList.size() - 1) {
                stringBuilder.append(")");
            }
            x++;
        }
        return stringBuilder;
    }

    public String archivizeItems(ArrayList<String> itemsIdsArray) {

        StringBuilder queryBuilderInitialPart = new StringBuilder("UPDATE inventory SET state='archive' WHERE ");
        StringBuilder queryBuilderIdsPart = buildStringFromArrayList(itemsIdsArray);
        StringBuilder query = queryBuilderInitialPart.append(" id IN ").append(queryBuilderIdsPart);

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(query.toString());

            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(InventoryDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Update Completed";
    }

}
