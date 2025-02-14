/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camelot.CamelotItem;

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
public class CamelotItemDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    ArrayList<String> getCamelotPickingPositions() {
        ArrayList<String> camelotPickingPositions = new ArrayList<>();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT \"Name\" FROM \"PETCAMELOT_UAT2\".\"BYT_V_PICKLOCATIONDETAILS\" ORDER BY \"PETCAMELOT_UAT2\".\"BYT_V_PICKLOCATIONDETAILS\".\"Name\" ;");

            while (resultSet.next()) {
                String position = resultSet.getString("Name");
                camelotPickingPositions.add(position);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotPickingPositions;
    }

    LinkedHashMap<Integer, String> getCamelotPickingPositionsXA() {
        LinkedHashMap<Integer, String> camelotPickingPositionsXA = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from [fotiou].[EliteUser].[IR1]  ORDER BY NAME;");
            while (resultSet.next()) {
                String position = resultSet.getString("NAME");
                int id = resultSet.getInt("ID");
                camelotPickingPositionsXA.put(id, position);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return camelotPickingPositionsXA;
    }

    LinkedHashMap<Integer, String> getCamelotPickingPositionsXB() {
        LinkedHashMap<Integer, String> camelotPickingPositionsXB = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from [fotiou].[EliteUser].[IR2]  ORDER BY NAME;");
            while (resultSet.next()) {
                String position = resultSet.getString("NAME");
                int id = resultSet.getInt("ID");
                camelotPickingPositionsXB.put(id, position);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return camelotPickingPositionsXB;
    }

    String updateCamelotItemPosition(String itemId, String newPositionIdXA, String newPositionIdXB) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = null;
        PreparedStatement updateStatementXA = null;
        PreparedStatement updateStatementXB = null;

        try {
            // 1. Get database connection
            connection = databaseConnectionFactory.getCamelotMicrosoftSQLConnection();
            if (connection == null) {
                throw new SQLException("Failed to establish database connection.");
            }

            // 2. Disable auto-commit for transaction safety
            connection.setAutoCommit(false);

            // 3. Prepare the first update statement
            String sqlXA = "UPDATE [fotiou].[EliteUser].[INI] SET IF1ID = ? WHERE ID = ?";
            updateStatementXA = connection.prepareStatement(sqlXA);
            updateStatementXA.setString(1, newPositionIdXA);
            updateStatementXA.setString(2, itemId);

            // 4. Prepare the second update statement
            String sqlXB = "UPDATE [fotiou].[EliteUser].[INI] SET IF2ID = ? WHERE ID = ?";
            updateStatementXB = connection.prepareStatement(sqlXB);
            updateStatementXB.setString(1, newPositionIdXB);
            updateStatementXB.setString(2, itemId);

            // 5. Execute both updates
            int rowsUpdatedXA = updateStatementXA.executeUpdate();
            int rowsUpdatedXB = updateStatementXB.executeUpdate();

            // 6. Commit only if both updates succeed
            if (rowsUpdatedXA > 0 && rowsUpdatedXB > 0) {
                connection.commit();
                return "DONE";
            } else {
                connection.rollback();  // Rollback changes if any update fails
                return "Transaction rolled back due to incomplete update.";
            }

        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback on error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            Logger.getLogger(CamelotItemDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Error: " + ex.getMessage();
        } finally {
            // 7. Close resources safely
            try {
                if (updateStatementXA != null) {
                    updateStatementXA.close();
                }
                if (updateStatementXB != null) {
                    updateStatementXB.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

}
