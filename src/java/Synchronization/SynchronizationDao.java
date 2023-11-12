/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Synchronization;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class SynchronizationDao {

    String getCamelotItemPosition(String code) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();
        String position = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select EXPR1, EXPR2  from WH1 WHERE ABBREVIATION='" + code + "';");

            while (resultSet.next()) {
                position = resultSet.getString("EXPR1").trim() + resultSet.getString("EXPR2").trim();
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SynchronizationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return position;
    }

    int getPet4UPositionId(String camelotPosition) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        int positionId = 0;
        String sql = "SELECT * FROM [petworld].[EliteUser].[IR1] WHERE [NAME]='" + camelotPosition + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                positionId = resultSet.getInt("ID");
            }
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SynchronizationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return positionId;
    }

    String updatePet4UItemPosition(String code, int positionId) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        try {

            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE [petworld].[EliteUser].[INI] SET [IF1ID]=? WHERE [ABBREVIATION]=?;");

            itemInsertStatement.setInt(1, positionId);
            itemInsertStatement.setString(2, code);

            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SynchronizationController.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Position Edited Successfully";
    }

}
