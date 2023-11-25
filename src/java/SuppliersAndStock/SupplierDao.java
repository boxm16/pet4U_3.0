package SuppliersAndStock;

import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    String addSupplier(Supplier supplier) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO suppliers (name, afm) VALUES (?,?)");

            insertStatement.setString(1, supplier.getName());
            insertStatement.setString(2, supplier.getAfm());

            insertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Supplier Added Successfully";
    }

    ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();

        String sql = "SELECT * FROM suppliers ;";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                int id = resultSet.getInt("id");
                supplier.setId(id);

                supplier.setName(resultSet.getString("name"));
                supplier.setAfm(resultSet.getString("afm"));

                suppliers.add(supplier);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return suppliers;
    }

    Supplier getSupplier(String supplierId) {

        String sql = "SELECT * FROM suppliers where id=" + supplierId;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                int id = resultSet.getInt("id");
                supplier.setId(id);

                supplier.setName(resultSet.getString("name"));
                supplier.setAfm(resultSet.getString("afm"));

                return supplier;
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    String addItemToSupplier(SuppliersItem item) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO stock_management (supplier_id, item_code, minimal_stock,  order_unit, order_unit_capacity, note) VALUES (?,?,?,?,?,?)");

            insertStatement.setInt(1, item.getSupplierId());
            insertStatement.setString(2, item.getCode());
            insertStatement.setInt(3, item.getMinimalStock());
            insertStatement.setString(4, item.getOrderUnit());
            insertStatement.setInt(5, item.getOrderUnitCapacity());
            insertStatement.setString(6, item.getNote());
            insertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Item Added To Supplier Successfully";
    }

    LinkedHashMap<String, SuppliersItem> getAllItemsOfSupplier(String supplierId) {
        LinkedHashMap<String, SuppliersItem> items = new LinkedHashMap<>();
        String sql = "SELECT * FROM stock_management WHERE supplier_id=" + supplierId + ";";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                SuppliersItem item = new SuppliersItem();
                String itemCode = resultSet.getString("item_code");
                item.setCode(itemCode.trim());

                int objectiveSales = resultSet.getInt("objective_sales");
                String objectiveSalesExpirationDateString = resultSet.getString("objective_sales_expiration_date");
                LocalDate objectiveSalesExpirationDate = null;

                if (objectiveSalesExpirationDateString != null) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    objectiveSalesExpirationDate = LocalDate.parse(objectiveSalesExpirationDateString, dateTimeFormatter);

                    LocalDate nowDate = LocalDate.now();
                    if (objectiveSalesExpirationDate.isBefore(nowDate)) {
                        objectiveSalesExpirationDate = null;
                        item.setObjectiveSales(0);
                    }
                }
                item.setObjectiveSales(objectiveSales);
                item.setObjectiveSalesExpirationDate(objectiveSalesExpirationDate);
                item.setMinimalStock(resultSet.getInt("minimal_stock"));
                item.setOrderHorizon(resultSet.getInt("order_horizon"));
                item.setOrderUnit(resultSet.getString("order_unit"));
                item.setOrderUnitCapacity(resultSet.getInt("order_unit_capacity"));
                String note = resultSet.getString("note");
                if (note == null) {
                    note = "";
                }
                item.setNote(note);
                items.put(itemCode, item);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
    }

    public SuppliersItem getSuppliersItem(String supplierId, String code) {
        SuppliersItem item = new SuppliersItem();
        String sql = "SELECT * FROM stock_management WHERE supplier_id=" + supplierId + " AND item_code='" + code + "';";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                String itemCode = resultSet.getString("item_code");
                item.setSupplierId(resultSet.getInt("supplier_id"));
                item.setCode(itemCode.trim());
                item.setObjectiveSales(resultSet.getDouble("objective_sales"));
                String string = resultSet.getString("objective_sales_expiration_date");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if (string == null) {
                    item.setObjectiveSalesExpirationDate(null);

                } else {
                    LocalDate expirationDate = LocalDate.parse(string, formatter);

                    LocalDate nowDate = LocalDate.now();
                    if (expirationDate.isAfter(nowDate)) {
                        item.setObjectiveSalesExpirationDate(expirationDate);
                        item.setObjectiveSales(0);
                    } else {
                        item.setObjectiveSalesExpirationDate(null);
                    }
                }

                item.setOrderHorizon(resultSet.getInt("order_horizon"));

                item.setMinimalStock(resultSet.getInt("minimal_stock"));
                item.setOrderUnit(resultSet.getString("order_unit"));
                item.setOrderUnitCapacity(resultSet.getInt("order_unit_capacity"));
                String note = resultSet.getString("note");
                if (note == null) {
                    note = "";
                }
                item.setNote(note);

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return item;
    }

    String editItemOfSupplier(SuppliersItem item) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE stock_management SET  minimal_stock=?, order_unit=?, order_unit_capacity=?, note=? WHERE supplier_id=? AND item_code=?");

            itemInsertStatement.setInt(1, item.getMinimalStock());
            itemInsertStatement.setString(2, item.getOrderUnit());
            itemInsertStatement.setInt(3, item.getOrderUnitCapacity());
            itemInsertStatement.setString(4, item.getNote());
            itemInsertStatement.setInt(5, item.getSupplierId());
            itemInsertStatement.setString(6, item.getCode());
            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return " Supplier`s Item  Edited Successfully";
    }

    LinkedHashMap<String, SuppliersItem> getItems(ArrayList<String> itemsIdsArray) {
        LinkedHashMap<String, SuppliersItem> items = new LinkedHashMap<>();

        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT * FROM stock_management WHERE ");
        StringBuilder queryBuilderIdsPart = buildStringFromArrayList(itemsIdsArray);
        StringBuilder query = queryBuilderInitialPart.append(" item_code IN ").append(queryBuilderIdsPart);
        System.out.println(query);
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                SuppliersItem item = new SuppliersItem();
                String itemCode = resultSet.getString("item_code").trim();
                item.setCode(itemCode);
                item.setMinimalStock(resultSet.getInt("minimal_stock"));
                item.setOrderUnit(resultSet.getString("order_unit"));
                item.setOrderUnitCapacity(resultSet.getInt("order_unit_capacity"));
                String note = resultSet.getString("note");
                item.setNote(note == null ? "" : note);

                items.put(itemCode, item);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
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
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    String deleteItemOfSupplier(String supplierId, String code) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement deletionStatement = connection.prepareStatement("DELETE FROM stock_management WHERE supplier_id=" + supplierId + " AND item_code='" + code + "'");

            deletionStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Item Deleted Successfully";
    }

    String updateObjectiveSales(String supplierId, String itemCode, String objectiveSales, String expirationDate, String orderHorizon) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE stock_management SET  objective_sales=?, objective_sales_expiration_date=?, order_horizon=? WHERE supplier_id=? AND item_code=?");

            itemInsertStatement.setString(1, objectiveSales);
            itemInsertStatement.setString(2, expirationDate);
            itemInsertStatement.setString(3, orderHorizon);
            itemInsertStatement.setString(4, supplierId);
            itemInsertStatement.setString(5, itemCode);
            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return " Objective Sales Updated Successfully";
    }
}
