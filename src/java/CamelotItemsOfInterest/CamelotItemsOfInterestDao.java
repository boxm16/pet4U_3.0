package CamelotItemsOfInterest;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CamelotItemsOfInterestDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    public ArrayList<String> getCamelotAltercodes() {

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
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO camelot_interest (item_code, owner, minimal_stock, weight_coefficient, order_unit, order_quantity, order_total_items) VALUES (?,?,?,?,?,?,?)");

            itemInsertStatement.setString(1, camelotItemOfInterest.getCode());
            itemInsertStatement.setString(2, camelotItemOfInterest.getOwner());
            itemInsertStatement.setInt(3, camelotItemOfInterest.getMinimalStock());
            itemInsertStatement.setInt(4, camelotItemOfInterest.getWeightCoefficient());
            itemInsertStatement.setString(5, camelotItemOfInterest.getOrderUnit());
            itemInsertStatement.setInt(6, camelotItemOfInterest.getOrderQuantity());
            itemInsertStatement.setInt(7, camelotItemOfInterest.getOrderTotalItems());

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
                camelotItemOfInterest.setOrderTotalItems(resultSet.getInt("order_total_items"));
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
        String sql = "SELECT camelot_interest.item_code, item.description, owner, "
                + "minimal_stock, weight_coefficient, order_unit, order_quantity, order_total_items, "
                + "item.position, camelot_item.position, item.quantity, camelot_item.quantity, "
                + "camelot_item.binded FROM camelot_interest "
                + "left join camelot_altercode on camelot_interest.item_code=camelot_altercode.altercode "
                + "inner join camelot_item on camelot_altercode.item_id=camelot_item.id "
                + "inner join altercode on camelot_interest.item_code=altercode.altercode "
                + "inner join item on altercode.item_id=item.id"
                + " WHERE camelot_interest.item_code='" + code + "' "
                + " ORDER BY item.position;";
        ResultSet resultSet;

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                camelotItemOfInterest.setCode(resultSet.getString("camelot_interest.item_code"));
                camelotItemOfInterest.setDescription(resultSet.getString("item.description"));
                camelotItemOfInterest.setOwner(resultSet.getString("owner"));
                camelotItemOfInterest.setMinimalStock(resultSet.getInt("minimal_stock"));
                camelotItemOfInterest.setWeightCoefficient(resultSet.getInt("weight_coefficient"));
                camelotItemOfInterest.setOrderUnit(resultSet.getString("order_unit"));
                camelotItemOfInterest.setOrderQuantity(resultSet.getInt("order_quantity"));
                camelotItemOfInterest.setOrderTotalItems(resultSet.getInt("order_total_items"));
                camelotItemOfInterest.setPosition(resultSet.getString("item.position"));
                camelotItemOfInterest.setCamelotPosition(resultSet.getString("camelot_item.position"));
                camelotItemOfInterest.setPet4uStock(resultSet.getInt("item.quantity"));
                camelotItemOfInterest.setCamelotStock(resultSet.getInt("camelot_item.quantity"));
                camelotItemOfInterest.setCamelotBinded(resultSet.getInt("camelot_item.binded"));

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
            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE camelot_interest SET item_code=?, owner=?, minimal_stock=?, weight_coefficient=?, order_unit=?, order_quantity=?,  order_total_items=? WHERE item_code=?");

            itemInsertStatement.setString(1, camelotItemOfInterest.getCode());
            itemInsertStatement.setString(2, camelotItemOfInterest.getOwner());
            itemInsertStatement.setInt(3, camelotItemOfInterest.getMinimalStock());
            itemInsertStatement.setInt(4, camelotItemOfInterest.getWeightCoefficient());
            itemInsertStatement.setString(5, camelotItemOfInterest.getOrderUnit());
            itemInsertStatement.setInt(6, camelotItemOfInterest.getOrderQuantity());
            itemInsertStatement.setInt(7, camelotItemOfInterest.getOrderTotalItems());
            itemInsertStatement.setString(8, camelotItemOfInterest.getCode());
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
}
