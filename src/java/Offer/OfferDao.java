package Offer;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class OfferDao {

    String addOffer(String code, String title, String startDate, String offerPartCode) {
        try {

            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO offers (item_code, title, start_date, offer_part) VALUES (?,?,?,?)");

            itemInsertStatement.setString(1, code);
            itemInsertStatement.setString(2, title);
            itemInsertStatement.setString(3, startDate);
            itemInsertStatement.setString(3, offerPartCode);

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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(startDateString, formatter);
                offer.setStartDate(startDate);

                String endDateString = resultSet.getString("end_date");

                if (endDateString == null) {
                    //do nothing
                } else {
                    LocalDate endDate = LocalDate.parse(endDateString, formatter);
                    offer.setEndDate(endDate);
                }

                String offerPart = resultSet.getString("offer_part");
                if (offerPart == null) {
                } else {
                    offer.setOfferPart(offerPart);
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

        String sql = "SELECT * FROM offers WHERE id='" + id + "'";
        ResultSet resultSet;
        Offer offer = new Offer();
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                offer.setId(resultSet.getInt("id"));
                offer.setItemCode(resultSet.getString("item_code"));
                offer.setTitle(resultSet.getString("title"));

                String startDateString = resultSet.getString("start_date");
                LocalDate startDate = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = LocalDate.parse(startDateString, formatter);
                offer.setStartDate(startDate);

                String endDateString = resultSet.getString("end_date");
                LocalDate endDate = null;
                if (endDateString == null) {
                    //do nothing
                } else {

                    endDate = LocalDate.parse(endDateString, formatter);
                    offer.setEndDate(endDate);

                }

                String offerPart = resultSet.getString("offer_part");
                if (offerPart == null) {
                } else {
                    offer.setOfferPart(offerPart);
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

    public String endOffer(String id, String endDate) {
        String sql = "UPDATE offers SET end_date='" + endDate + "' WHERE id=" + id;
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "success";
    }

    ArrayList<Offer> getAllActiveOffers() {
        ArrayList<Offer> offers = new ArrayList<>();

        String sql = "SELECT * FROM offers ORDER BY start_date";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                Offer offer = new Offer();

                offer.setId(resultSet.getInt("id"));
                offer.setItemCode(resultSet.getString("item_code"));
                offer.setTitle(resultSet.getString("title"));

                String startDateString = resultSet.getString("start_date");
                LocalDate startDate = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = LocalDate.parse(startDateString, formatter);
                offer.setStartDate(startDate);

                String offerPart = resultSet.getString("offer_part");
                if (offerPart == null) {
                } else {
                    offer.setOfferPart(offerPart);
                }

                String endDateString = resultSet.getString("end_date");
                if (endDateString == null) {
                    offers.add(offer);
                } else {

                }
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return offers;
    }

    ArrayList<Offer> getAllOffers() {
        ArrayList<Offer> offers = new ArrayList<>();

        String sql = "SELECT * FROM offers ORDER BY start_date";
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                Offer offer = new Offer();

                offer.setId(resultSet.getInt("id"));
                offer.setItemCode(resultSet.getString("item_code"));
                offer.setTitle(resultSet.getString("title"));

                String startDateString = resultSet.getString("start_date");
                LocalDate startDate = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = LocalDate.parse(startDateString, formatter);
                offer.setStartDate(startDate);
                String endDateString = resultSet.getString("end_date");
                if (endDateString == null) {
                } else {
                    LocalDate endDate = LocalDate.parse(endDateString, formatter);
                    offer.setEndDate(endDate);
                }

                String offerPart = resultSet.getString("offer_part");
                if (offerPart == null) {
                } else {
                    offer.setOfferPart(offerPart);
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

    String editOffer(String id, String code, String title, String startDate, String endDate, String offerPart) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            PreparedStatement itemInsertStatement = connection.prepareStatement("UPDATE offers SET  title=?, start_date=?, end_date=?, offr_part=? WHERE id=?;");

            itemInsertStatement.setString(1, title);
            itemInsertStatement.setString(2, startDate);
            itemInsertStatement.setString(3, endDate);
            itemInsertStatement.setString(4, offerPart);
            itemInsertStatement.setString(5, id);
            itemInsertStatement.execute();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(OfferDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "success";
    }

}
