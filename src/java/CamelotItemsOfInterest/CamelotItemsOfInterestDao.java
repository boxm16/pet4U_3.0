package CamelotItemsOfInterest;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CamelotItemsOfInterestDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    public ArrayList<String> getCamelotAltercodes1() {

        ArrayList<String> camelotAltercodes = new ArrayList();
        String sql = "SELECT altercode FROM camelot_altercode";

        ResultSet resultSet;

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                camelotAltercodes.add(resultSet.getString("altercode"));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotAltercodes;
    }

    public String addItem(CamelotItemOfInterest camelotItemOfInterest) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO camelot_interest (item_code, owner, minimal_stock, weight_coefficient, order_unit, order_quantity, camelot_minimal_stock, note) VALUES (?,?,?,?,?,?,?,?)");

            itemInsertStatement.setString(1, camelotItemOfInterest.getCode());
            itemInsertStatement.setString(2, camelotItemOfInterest.getOwner());
            itemInsertStatement.setInt(3, camelotItemOfInterest.getMinimalStock());
            itemInsertStatement.setInt(4, camelotItemOfInterest.getWeightCoefficient());
            itemInsertStatement.setString(5, camelotItemOfInterest.getOrderUnit());
            itemInsertStatement.setInt(6, camelotItemOfInterest.getOrderQuantity());
            itemInsertStatement.setInt(7, camelotItemOfInterest.getCamelotMinimalStock());
            itemInsertStatement.setString(8, camelotItemOfInterest.getNote());

            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Item Of Interest Added Successfully";
    }

    public ArrayList<CamelotItemOfInterest> getItemsOfInterest() {
        ArrayList<CamelotItemOfInterest> camelotItemsOfInterest = new ArrayList<>();
        String sql = "SELECT camelot_interest.item_code, item.description, owner, "
                + "minimal_stock, weight_coefficient, order_unit, order_quantity, order_total_items, "
                + "item.position, camelot_item.position, item.quantity, camelot_item.quantity, "
                + "camelot_item.binded FROM camelot_interest "
                + "left join camelot_altercode on camelot_interest.item_code=camelot_altercode.altercode "
                + "inner join camelot_item on camelot_altercode.item_id=camelot_item.id "
                + "inner join altercode on camelot_interest.item_code=altercode.altercode "
                + "inner join item on altercode.item_id=item.id "
                + " ORDER BY item.position;";
        ResultSet resultSet;

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
                camelotItemOfInterest.setCode(resultSet.getString("camelot_interest.item_code"));
                camelotItemOfInterest.setDescription(resultSet.getString("item.description"));
                camelotItemOfInterest.setOwner(resultSet.getString("owner"));
                camelotItemOfInterest.setMinimalStock(resultSet.getInt("minimal_stock"));
                camelotItemOfInterest.setWeightCoefficient(resultSet.getInt("weight_coefficient"));
                camelotItemOfInterest.setOrderUnit(resultSet.getString("order_unit"));
                camelotItemOfInterest.setOrderQuantity(resultSet.getInt("order_quantity"));
                camelotItemOfInterest.setCamelotMinimalStock(resultSet.getInt("camelot_minimal_stock"));
                camelotItemOfInterest.setPosition(resultSet.getString("item.position"));
                camelotItemOfInterest.setCamelotPosition(resultSet.getString("camelot_item.position"));
                camelotItemOfInterest.setPet4uStock(resultSet.getInt("item.quantity"));
                camelotItemOfInterest.setCamelotStock(resultSet.getInt("camelot_item.quantity"));
                camelotItemOfInterest.setCamelotBinded(resultSet.getInt("camelot_item.binded"));
                camelotItemsOfInterest.add(camelotItemOfInterest);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return camelotItemsOfInterest;
    }

    public CamelotItemOfInterest getItemOfInterest(String code) {
        CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
        String sql = "SELECT * FROM camelot_interest WHERE item_code='" + code + "';";
        ResultSet resultSet;

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                String itemCode = resultSet.getString("item_code");
                camelotItemOfInterest.setCode(itemCode);
                camelotItemOfInterest.setOwner(resultSet.getString("owner"));
                camelotItemOfInterest.setMinimalStock(resultSet.getInt("minimal_stock"));
                camelotItemOfInterest.setWeightCoefficient(resultSet.getInt("weight_coefficient"));
                camelotItemOfInterest.setOrderUnit(resultSet.getString("order_unit"));
                camelotItemOfInterest.setOrderQuantity(resultSet.getInt("order_quantity"));
                camelotItemOfInterest.setCamelotMinimalStock(resultSet.getInt("camelot_minimal_stock"));
                camelotItemOfInterest.setNote(resultSet.getString("note"));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotItemOfInterest;
    }

    public String editItem(CamelotItemOfInterest camelotItemOfInterest) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE camelot_interest SET item_code=?, owner=?, minimal_stock=?, weight_coefficient=?, order_unit=?, order_quantity=?,  camelot_minimal_stock=?, note=? WHERE item_code=?");

            itemInsertStatement.setString(1, camelotItemOfInterest.getCode());
            itemInsertStatement.setString(2, camelotItemOfInterest.getOwner());
            itemInsertStatement.setInt(3, camelotItemOfInterest.getMinimalStock());
            itemInsertStatement.setInt(4, camelotItemOfInterest.getWeightCoefficient());
            itemInsertStatement.setString(5, camelotItemOfInterest.getOrderUnit());
            itemInsertStatement.setInt(6, camelotItemOfInterest.getOrderQuantity());
            itemInsertStatement.setInt(7, camelotItemOfInterest.getCamelotMinimalStock());
            itemInsertStatement.setString(8, camelotItemOfInterest.getNote());
            itemInsertStatement.setString(9, camelotItemOfInterest.getCode());
            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Item Of Interest Edited Successfully";
    }

    public String deleteItemOfInterest(String code) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM camelot_interest WHERE item_code='" + code + "'");
            statement.close();
            connection.close();
            return "DONE";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    ArrayList<String> getPet4UAltercodes() {
        ArrayList<String> pet4UAltercodes = new ArrayList();
        String sql = "SELECT altercode FROM altercode";

        ResultSet resultSet;

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                pet4UAltercodes.add(resultSet.getString("altercode"));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pet4UAltercodes;
    }

    public ArrayList<String> getCamelotAltercodesFromMicrosoftDB() {

        ArrayList<String> camelotAltercodes = new ArrayList();
        String sql = "SELECT ALTERNATECODE FROM WH1";

        ResultSet resultSet;

        try {
            Connection connection = this.databaseConnectionFactory.getCamelotMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                camelotAltercodes.add(resultSet.getString("ALTERNATECODE"));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotAltercodes;
    }

    ArrayList<String> getPet4UAltercodesFromMicrosoftDB() {

        ArrayList<String> pet4UAltercodes = new ArrayList();
        String sql = "SELECT ALTERNATECODE FROM WH1";

        ResultSet resultSet;

        try {
            Connection connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                pet4UAltercodes.add(resultSet.getString("ALTERNATECODE"));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pet4UAltercodes;
    }

    LinkedHashMap<String, Item> getpet4UItems() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                Item item = null;
                if (!items.containsKey(code)) {
                    item = new Item();
                    item.setCode(resultSet.getString("ABBREVIATION").trim());
                    item.setDescription(resultSet.getString("NAME").trim());

                    if (resultSet.getString("EXPR1") != null) {
                        item.setPosition(resultSet.getString("EXPR1").trim());
                    } else {
                        item.setPosition("");
                    }

                    item.setQuantity(resultSet.getString("QTYBALANCE").trim());
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
            Logger.getLogger(CamelotDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    LinkedHashMap<String, CamelotItemOfInterest> getCamelotItemsOfInterset() {
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
                String itemCode = resultSet.getString("item_code");
                camelotItemOfInterest.setCode(itemCode.trim());
                camelotItemOfInterest.setOwner(resultSet.getString("owner"));
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
                camelotItemsOfInterest.put(itemCode, camelotItemOfInterest);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return camelotItemsOfInterest;
    }

    String insertDayRestSnapshot(LocalDate nowDate, LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            connection.setAutoCommit(false);
            PreparedStatement itemsInsertStatement = connection.prepareStatement("INSERT INTO camelot_day_rest (item_code, date_stamp, item_rest) VALUES (?,?,?)");
            for (Map.Entry<String, CamelotItemOfInterest> itemEntry : camelotItemsOfInterest.entrySet()) {
                CamelotItemOfInterest itemOfInterest = itemEntry.getValue();

                itemsInsertStatement.setString(1, itemOfInterest.getCode());
                itemsInsertStatement.setString(2, nowDate.toString());

                if (itemOfInterest.getQuantity() == null) {
                    itemOfInterest.setQuantity("");
                }
                itemsInsertStatement.setString(3, itemOfInterest.getQuantity());

                itemsInsertStatement.addBatch();

            }
            itemsInsertStatement.executeBatch();
            connection.commit();
            itemsInsertStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Snapshot Of Items Of Our Interest Added Successfully";
    }

    public String getLastCamelotSnapshotDate() {
        String sql = "SELECT date_stamp FROM camelot_day_rest ORDER BY date_stamp DESC LIMIT 1; ;";
        ResultSet resultSet;
        String last_date_stamp = null;
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                last_date_stamp = resultSet.getString("date_stamp");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return last_date_stamp;
    }

    public LinkedHashMap<String, Item> getCamelotItemsRowByRow() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                Item item = new Item();
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
                items.put(altercode, item);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    public LinkedHashMap<String, Item> getPet4UItemsRowByRow() {
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
            Logger.getLogger(CamelotDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    public ArrayList<ItemSnapshot> getItemSnapshots(String code) {
        ArrayList<ItemSnapshot> itemSnapshots = new ArrayList<>();

        String sql = "SELECT * FROM camelot_day_rest WHERE item_code='" + code + "' ORDER BY date_stamp DESC;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ItemSnapshot itemSnapshot = new ItemSnapshot();

                String dateStamp = resultSet.getString("date_stamp");
                String quantity = resultSet.getString("item_rest");
                itemSnapshot.setDateStamp(dateStamp);
                itemSnapshot.setQuantity(quantity);
                itemSnapshots.add(itemSnapshot);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemsOfInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemSnapshots;
    }

}
