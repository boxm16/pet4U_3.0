/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotItemsOfOurInterest_V_3_1;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CamelotItemsOfOurInterestDao {

    LinkedHashMap<String, CamelotItemOfInterest> getCamelotItemsOfOurInterset() {
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
            Logger.getLogger(CamelotItemsOfOurInterestDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return camelotItemsOfInterest;
    }

}
