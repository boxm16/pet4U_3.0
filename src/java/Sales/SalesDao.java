package Sales;

import BasicModel.AltercodeContainer;
import Pet4uItems.Pet4uItemsDao;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SalesDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    public void deleteSoldItems() {
        System.out.println("STARTING ITEM DELETION");
        String salesDeletionSql = "DELETE FROM sales";
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(salesDeletionSql);
            statement.close();

            connection.close();
            System.out.println("Sales DELETED");
        } catch (SQLException ex) {
            Logger.getLogger(SalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String insertNewUpload(ArrayList<SoldItem> soldItems) {

        System.out.println("STARTING INSERTING UPLOADED DATA");
        System.out.println(" STARTING ADDING ITEMS TO 'sales' INSERTION BATCH");
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO sales (code, description, measure_unit, eshop_sales, shops_supply, total_sales, coeficient,total_sales_in_pieces) VALUES (?,?,?,?,?,?,?,?)");
            int index = 0;
            for (SoldItem soldItem : soldItems) {

                itemInsertStatement.setString(1, soldItem.getCode());
                itemInsertStatement.setString(2, soldItem.getDescription());
                itemInsertStatement.setString(3, soldItem.getMeasureUnit());
                itemInsertStatement.setInt(4, soldItem.getEshopSales());
                itemInsertStatement.setInt(5, soldItem.getShopsSupply());
                itemInsertStatement.setInt(6, soldItem.getTotalSales());
                itemInsertStatement.setInt(7, soldItem.getCoeficient());
                itemInsertStatement.setInt(8, soldItem.getTotalSalesInPieces());
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
            Logger.getLogger(SalesDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "SALES UPLOAD  EXECUTED SUCCESSFULLY.";
    }

    public HashMap<String, SoldItem> getSixMonthsSales() {
        HashMap<String, SoldItem> allSales = new HashMap<>();
        String sql = "SELECT * FROM sales";
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
                String measureUnit = resultSet.getString("measure_unit");
                int eshopSales = resultSet.getInt("eshop_sales");
                int shopsSupply = resultSet.getInt("shops_supply");
                int totalSales = resultSet.getInt("total_sales");
                int coeficient = resultSet.getInt("coeficient");
                int totalSalesInPiecies = resultSet.getInt("total_sales_in_pieces");

                if (coeficient == 0) {
                    //do nothing
                } else {
                    eshopSales = eshopSales / coeficient;
                    shopsSupply = shopsSupply / coeficient;
                }
                SoldItem item = new SoldItem();
                item.setCode(code);
                item.setDescription(description);
                item.setMeasureUnit(measureUnit);
                item.setEshopSales(eshopSales);
                item.setShopsSupply(shopsSupply);
                item.setTotalSales(totalSales);
                item.setCoeficient(coeficient);
                item.setTotalSalesInPieces(totalSalesInPiecies);

                allSales.put(code, item);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allSales;
    }

    LinkedHashMap<String, SoldItem> getAllPet4uItems() {
        LinkedHashMap<String, SoldItem> soldItems = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1 ORDER BY EXPR1;");

            while (resultSet.next()) {
                String code = resultSet.getString("ABBREVIATION").trim();
                SoldItem soldItem = null;
                if (!soldItems.containsKey(code)) {
                    soldItem = new SoldItem();
                    soldItem.setCode(resultSet.getString("ABBREVIATION").trim());
                    soldItem.setDescription(resultSet.getString("NAME").trim());
                    String position = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position = resultSet.getString("EXPR1").trim();
                    }
                    soldItem.setPosition(position);
                    soldItem.setQuantity(resultSet.getString("QTYBALANCE").trim());
                    String state = "";
                    if (resultSet.getString("EXPR2") != null) {
                        state = resultSet.getString("EXPR2").trim();
                    }
                    soldItem.setState(state);
                    soldItems.put(code, soldItem);
                }
                AltercodeContainer altercodeContainer = new AltercodeContainer();
                altercodeContainer.setAltercode(resultSet.getString("ALTERNATECODE").trim());
                if (resultSet.getString("CODEDESCRIPTION") == null) {
                    altercodeContainer.setStatus("");
                } else {
                    altercodeContainer.setStatus(resultSet.getString("CODEDESCRIPTION").trim());
                }
                soldItems.get(code).addAltercodeContainer(altercodeContainer);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pet4uItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soldItems;
    }

    public HashMap<String, SoldItem> getSixMonthsSalesX() {
        HashMap<String, SoldItem> allSales = new HashMap<>();
        String sql = "SELECT * FROM sales";
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
                String measureUnit = resultSet.getString("measure_unit");
                int eshopSales = resultSet.getInt("eshop_sales");
                int shopsSupply = resultSet.getInt("shops_supply");
                int totalSales = resultSet.getInt("total_sales");
                int coeficient = resultSet.getInt("coeficient");
                int totalSalesInPiecies = resultSet.getInt("total_sales_in_pieces");

                SoldItem item = new SoldItem();
                item.setCode(code);
                item.setDescription(description);
                item.setMeasureUnit(measureUnit);
                item.setEshopSales(eshopSales);
                item.setShopsSupply(shopsSupply);
                item.setTotalSales(totalSales);
                item.setTts(resultSet.getInt("total_sales"));
                item.setCoeficient(coeficient);
                item.setTotalSalesInPieces(totalSalesInPiecies);

                allSales.put(code, item);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allSales;
    }
}
