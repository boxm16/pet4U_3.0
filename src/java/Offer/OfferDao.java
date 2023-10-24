package Offer;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class OfferDao {

    String addOffer(String code, String title, String startDate) {
        try {

            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO offers (item_code, title, start_date) VALUES (?,?,?)");

            itemInsertStatement.setString(1, code);
            itemInsertStatement.setString(2, title);
            itemInsertStatement.setString(3, startDate);

            itemInsertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Offer Added Successfully";
    }

}
