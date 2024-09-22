/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemCodeChanging;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class ItemCodeChangingDao {

    String changeItemCodeIn(String tableName, String oldItemCode, String newItemCode) {

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE " + tableName + " SET item_code=?  WHERE item_code=?");

            itemInsertStatement.setString(1, newItemCode);
            itemInsertStatement.setString(2, oldItemCode);

            itemInsertStatement.execute();
           
            itemInsertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(ItemCodeChangingDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Table: " + tableName + ". Item Code Changed Successfully";
    }

    String changeItemIn(String tableName, String oldItemCode, String newItemCode) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE " + tableName + " SET code=?  WHERE code=?");

            itemInsertStatement.setString(1, newItemCode);
            itemInsertStatement.setString(2, oldItemCode);

            itemInsertStatement.execute();

            itemInsertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(ItemCodeChangingDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Table: " + tableName + ".  Code Changed Successfully";
    }

}
