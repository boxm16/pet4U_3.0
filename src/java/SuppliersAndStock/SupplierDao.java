package SuppliersAndStock;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO stock_management (supplier_id, item_code, minimal_stock,  order_unit, order_unit_capacity) VALUES (?,?,?,?,?)");

            insertStatement.setInt(1, item.getSupplierId());
            insertStatement.setString(2, item.getCode());
            insertStatement.setInt(3, item.getMinimalStock());
            insertStatement.setString(4, item.getOrderUnit());
            insertStatement.setInt(5, item.getOrderUnitCapacity());

            insertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Item Added To Supplier Successfully";
    }

    LinkedHashMap<String, SuppliersItem> getAllItemsOfSupplier(String supplierId) {
        LinkedHashMap<String, SuppliersItem> items = new LinkedHashMap<>();
        String sql = "SELECT * FROM stock_management;";
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
                item.setMinimalStock(resultSet.getInt("minimal_stock"));
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

    SuppliersItem getSuppliersItem(String supplierId, String code) {
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
                item.setCode(itemCode.trim());
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

}
