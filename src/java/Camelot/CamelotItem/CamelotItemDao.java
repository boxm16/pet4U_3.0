/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camelot.CamelotItem;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
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

}
