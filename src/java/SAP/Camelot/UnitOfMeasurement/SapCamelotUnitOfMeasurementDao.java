/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.UnitOfMeasurement;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import Service.DatabaseConnectionFactory;
import static Service.StaticsDispatcher.dbSchema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class SapCamelotUnitOfMeasurementDao {

    public LinkedHashMap<Short, SapUnitOfMeasurement> getAllUnitsOfMeasurement() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = new LinkedHashMap<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + dbSchema + ".OUOM";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                short uomEntry = resultSet.getShort("UomEntry");

                if (!allUnitsOfMeasurement.containsKey(uomEntry)) {
                    SapUnitOfMeasurement unitOfMeasurement = new SapUnitOfMeasurement();
                    unitOfMeasurement.setUomEntry(uomEntry);
                    unitOfMeasurement.setUomCode(resultSet.getString("UomCode"));
                    unitOfMeasurement.setUomName(resultSet.getString("UomName"));
                    unitOfMeasurement.setLocked(resultSet.getString("Locked").equals("Y"));
                    unitOfMeasurement.setDataSource(resultSet.getString("DataSource"));

                    // Add any additional OUOM fields you need
                    allUnitsOfMeasurement.put(uomEntry, unitOfMeasurement);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUnitsOfMeasurement;
    }

    public LinkedHashMap<Short, SapUnitOfMeasurementGroup> getAllUnitOfMeasurementGroups() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = new LinkedHashMap<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            String query = "SELECT * FROM " + dbSchema + ".OUGP "
                    + "INNER JOIN " + dbSchema + ".UGP1 "
                    + "ON " + dbSchema + ".OUGP.\"UgpEntry\" = " + dbSchema + ".UGP1.\"UgpEntry\" "
                    + "INNER JOIN " + dbSchema + ".OUOM "
                    + "ON " + dbSchema + ".UGP1.\"UomEntry\" = " + dbSchema + ".OUOM.\"UomEntry\"";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                short ugpEntry = resultSet.getShort("UgpEntry");
                if (!allUnitOfMeasurementGroups.containsKey(ugpEntry)) {
                    SapUnitOfMeasurementGroup unitOfMeasurementGroup = new SapUnitOfMeasurementGroup();
                    unitOfMeasurementGroup.setUgpEntry(ugpEntry);
                    unitOfMeasurementGroup.setUgpCode(resultSet.getString("UgpCode"));
                    unitOfMeasurementGroup.setUgpName(resultSet.getString("UgpName"));
                    allUnitOfMeasurementGroups.put(ugpEntry, unitOfMeasurementGroup);
                }
                SapUnitOfMeasurementGroup unitOfMeasurementGroup = allUnitOfMeasurementGroups.get(ugpEntry);

                SapUnitOfMeasurement unitOfMeasurement = new SapUnitOfMeasurement();
                unitOfMeasurement.setUomEntry(resultSet.getShort("UomEntry"));
                unitOfMeasurement.setUomCode(resultSet.getString("UomCode"));
                unitOfMeasurement.setUomName(resultSet.getString("UomName"));
                unitOfMeasurement.setBaseQuantity(resultSet.getDouble("BaseQty"));
                unitOfMeasurementGroup.getUnitOfMeasurements().put(resultSet.getString("UomCode"), unitOfMeasurement);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUnitOfMeasurementGroups;
    }

    public SapUnitOfMeasurementGroup getUnitOfMeasurementGroup(String ugpEntry) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        SapUnitOfMeasurementGroup unitOfMeasurementGroup = null;

        try {
            // Using prepared statement to prevent SQL injection
            String query = "SELECT * FROM " + dbSchema + ".OUGP "
                    + "INNER JOIN " + dbSchema + ".UGP1 "
                    + "ON OUGP.\"UgpEntry\" = UGP1.\"UgpEntry\" "
                    + "INNER JOIN " + dbSchema + ".OUOM "
                    + "ON UGP1.\"UomEntry\" = OUOM.\"UomEntry\" "
                    + "WHERE OUGP.\"UgpEntry\" = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setShort(1, Short.parseShort(ugpEntry)); // Convert String to short

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (unitOfMeasurementGroup == null) {
                    unitOfMeasurementGroup = new SapUnitOfMeasurementGroup();
                    unitOfMeasurementGroup.setUgpEntry(resultSet.getShort("UgpEntry"));
                    unitOfMeasurementGroup.setUgpCode(resultSet.getString("UgpCode"));
                    unitOfMeasurementGroup.setUgpName(resultSet.getString("UgpName"));
                }

                SapUnitOfMeasurement unitOfMeasurement = new SapUnitOfMeasurement();
                unitOfMeasurement.setUomEntry(resultSet.getShort("UomEntry"));
                unitOfMeasurement.setUomCode(resultSet.getString("UomCode"));
                unitOfMeasurement.setUomName(resultSet.getString("UomName"));
                unitOfMeasurement.setBaseQuantity(resultSet.getDouble("BaseQty"));

                unitOfMeasurementGroup.getUnitOfMeasurements().put(
                        resultSet.getString("UomCode"),
                        unitOfMeasurement
                );
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementDao.class.getName())
                    .log(Level.SEVERE, "Error retrieving unit of measurement group", ex);
        } catch (NumberFormatException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementDao.class.getName())
                    .log(Level.SEVERE, "Invalid UgpEntry format: " + ugpEntry, ex);
        }

        return unitOfMeasurementGroup;
    }

    public short getNextUomGroupId() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        short id = 1; // Default to 1 if no records exist
        try {
            Statement statement = connection.createStatement();
            // SAP HANA SQL to get max ID and increment
            String query = "SELECT COALESCE(MAX(\"UgpEntry\"), 0) + 1 FROM " + dbSchema + ".\"OUGP\"";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                id = resultSet.getShort(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementDao.class.getName()).log(Level.SEVERE, null, ex);
            // Consider rethrowing as a custom exception
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SapCamelotUnitOfMeasurementDao.class.getName()).log(Level.SEVERE, "Failed to close connection", ex);
            }
        }
        return id;
    }

}
