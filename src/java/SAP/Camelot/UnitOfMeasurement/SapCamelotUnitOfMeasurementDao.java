/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.UnitOfMeasurement;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
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
public class SapCamelotUnitOfMeasurementDao {

    public LinkedHashMap<Short, SapUnitOfMeasurement> getAllUnitsOfMeasurement() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = new LinkedHashMap<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM PETCAMELOT_UAT2.OUOM";
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
            String query = "SELECT * FROM  PETCAMELOT_UAT2.OUGP "
                    + "INNER JOIN  PETCAMELOT_UAT2.UGP1 "
                    + "ON  PETCAMELOT_UAT2.OUGP.\"UgpEntry\" = PETCAMELOT_UAT2.UGP1.\"UgpEntry\" "
                    + "INNER JOIN  PETCAMELOT_UAT2.OUOM  "
                    + "ON  PETCAMELOT_UAT2.UGP1.\"UomEntry\" = PETCAMELOT_UAT2.OUOM.\"UomEntry\" ; ";

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

}
