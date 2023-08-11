package SalesX;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class SalesDaoX {

    public void deleteSoldItems() {
        System.out.println("STARTING ITEM DELETION");
        String salesDeletionSql = "DELETE FROM sales_X";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(salesDeletionSql);
            statement.close();

            connection.close();
            System.out.println("Sales DELETED");
        } catch (SQLException ex) {
            Logger.getLogger(SalesDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String insertNewUpload(ArrayList<SoldItem> soldItems) {

        System.out.println("STARTING INSERTING UPLOADED DATA");
        System.out.println(" STARTING ADDING ITEMS TO 'sales' INSERTION BATCH");
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO sales_X (code, description, eshop_sales, shops_supply) VALUES (?,?,?,?)");
            int index = 0;
            for (SoldItem soldItem : soldItems) {

                itemInsertStatement.setString(1, soldItem.getCode());
                itemInsertStatement.setString(2, soldItem.getDescription());
                itemInsertStatement.setDouble(3, soldItem.getEshopSales());
                itemInsertStatement.setDouble(4, soldItem.getShopsSupply());

                itemInsertStatement.addBatch();
                index++;
                if (index % 1000 == 0) {
                    System.out.println(index + " soldItems added to batch");
                }

            }

            itemInsertStatement.executeBatch();
            connection.commit();
            itemInsertStatement.close();

            connection.close();

            System.out.println("'sales' INSERTION BATCH EXECUTED.");
            System.out.println("-----------------------------------");
        } catch (SQLException ex) {
            Logger.getLogger(SalesDaoX.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "SALES UPLOAD  EXECUTED SUCCESSFULLY.";
    }

    public HashMap<String, SoldItem> getSixMonthsSalesX() {
        HashMap<String, SoldItem> allSales = new HashMap<>();
        String sql = "SELECT * FROM sales_x";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String description = resultSet.getString("description");
                int eshopSales = resultSet.getInt("eshop_sales");
                int shopsSupply = resultSet.getInt("shops_supply");

                SoldItem item = new SoldItem();
                item.setCode(code);
                item.setDescription(description);
                item.setEshopSales(eshopSales);
                item.setShopsSupply(shopsSupply);

                allSales.put(code, item);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SalesDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allSales;
    }

}
