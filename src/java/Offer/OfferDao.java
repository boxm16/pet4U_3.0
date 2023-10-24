package Offer;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public ArrayList<Offer> getOffers(String code) {
        ArrayList<Offer> offers = new ArrayList<>();

        String sql = "SELECT * FROM offers WHERE item_code='" + code + "' ORDER BY start_date";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                Offer offer = new Offer();

                offer.setId(resultSet.getInt("id"));

                offer.setTitle(resultSet.getString("title"));

                String startDateString = resultSet.getString("start_date");
                Date startDate = null;
                try {
                    startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
                } catch (ParseException ex) {
                    Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                offer.setStartDate(startDate);

                String endDateString = resultSet.getString("end_date");
                Date endDate = null;
                if (endDateString == null) {
                    //do nothing
                } else {
                    try {
                        endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
                    } catch (ParseException ex) {
                        Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    offer.setStartDate(endDate);
                }

                offers.add(offer);

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return offers;
    }

    public Offer getOffer(String id) {

        String sql = "SELECT * FROM offers WHERE id='" + id+"'";
        ResultSet resultSet;
        Offer offer = new Offer();
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                offer.setId(resultSet.getInt("id"));

                offer.setTitle(resultSet.getString("title"));

                String startDateString = resultSet.getString("start_date");
                Date startDate = null;
                try {
                    startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
                } catch (ParseException ex) {
                    Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                offer.setStartDate(startDate);

                String endDateString = resultSet.getString("end_date");
                Date endDate = null;
                if (endDateString == null) {
                    //do nothing
                } else {
                    try {
                        endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
                    } catch (ParseException ex) {
                        Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    offer.setStartDate(endDate);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return offer;
    }

}
