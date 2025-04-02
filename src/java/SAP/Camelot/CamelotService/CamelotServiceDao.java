package SAP.Camelot.CamelotService;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CamelotServiceDao {

    public short getNextUomGroupId() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getSapHanaConnection();
        short id = 1; // Default to 1 if no records exist
        try {
            Statement statement = connection.createStatement();
            // SAP HANA SQL to get max ID and increment
            String query = "SELECT COALESCE(MAX(UgpEntry), 0) + 1 FROM \"UOGP\"";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                id = resultSet.getShort(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CamelotServiceDao.class.getName()).log(Level.SEVERE, null, ex);
            // Consider rethrowing as a custom exception
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CamelotServiceDao.class.getName()).log(Level.SEVERE, "Failed to close connection", ex);
            }
        }
        return id;
    }

}
