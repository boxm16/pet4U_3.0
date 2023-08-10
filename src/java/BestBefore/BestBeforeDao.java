package BestBefore;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BestBeforeDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    HashMap<LocalDate, BestBeforeStatement> getBestBeforeStatements(String altercode) {
        HashMap<LocalDate, BestBeforeStatement> statements = new HashMap();
        return statements;
    }

    String saveStatement(BestBeforeStatement bestBeforeStatement) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO best_before (altercode, best_before_date_stamp, alert_date_stamp, note) VALUES (?,?,?,?)");

            insertStatement.setString(1, bestBeforeStatement.getAltercode());
            insertStatement.setString(2, bestBeforeStatement.getBestBefore());
            insertStatement.setString(3, bestBeforeStatement.getAlert());
            insertStatement.setString(4, bestBeforeStatement.getNote());

            insertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(BestBeforeDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Best Before Statement Added Successfully";
    }

    public ArrayList< BestBeforeStatement> getAllBestBeforeStatements() {

        ArrayList< BestBeforeStatement> statements = new ArrayList();
        String sql = "SELECT * FROM best_before ORDER BY alert_date_stamp;";
        ResultSet resultSet;
        try {
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                BestBeforeStatement bestBeforeStatement = new BestBeforeStatement();

                bestBeforeStatement.setId(resultSet.getInt("id"));
                bestBeforeStatement.setAltercode(resultSet.getString("altercode"));
                bestBeforeStatement.setBestBefore(resultSet.getString("best_before_date_stamp"));
                bestBeforeStatement.setAlert(resultSet.getString("alert_date_stamp"));
                bestBeforeStatement.setNote(resultSet.getString("note"));

                statements.add(bestBeforeStatement);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(BestBeforeDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return statements;
    }

    public void deleteStatement(String id) {

        String sql = "DELETE FROM best_before WHERE id='" + id + "'";
        try {
            databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(BestBeforeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String editStatement(String id, String bestBefore, String alert) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement st = connection.createStatement();
            String sql = "UPDATE best_before SET best_before_date_stamp='" + bestBefore + "', alert_date_stamp='" + alert + "' WHERE id=" + id + " ;";
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(BestBeforeDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Item Of Interest Edited Successfully---------------";
    }

}
