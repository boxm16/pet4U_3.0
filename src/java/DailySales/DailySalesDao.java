package DailySales;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DailySalesDao {

    public LinkedHashMap<LocalDate, DailySale> getLast30DaysSales(String itemCode) {
        LinkedHashMap<LocalDate, DailySale> dailySales = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        LocalDate date = LocalDate.now();
        //  LocalDate firstDate = date.minusDays(30);
        //  LocalDate lastDate = date.minusDays(1);
        for (int x = 30; x > 0; x--) {

            //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //  String formattedString = date.format(formatter);
            //  String formattedString = date.format(formatter) + " 00:00:00.0";
            //   System.out.println(formattedString);
            dailySales.put(date, new DailySale());
            date = date.minusDays(1);
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM [petworld].[dbo].[WH_SALES_DOCS] WHERE ABBREVIATION='" + itemCode + "' "
                    + " AND DATE_TIME >= '" + date + "' ORDER BY DATE_TIME;");

            LocalDate creationDate;
            while (resultSet.next()) {
                String creationDateTimeStampString = resultSet.getString("DATE_TIME");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

                LocalDateTime creationDateTime;
                if (creationDateTimeStampString.length() == 23) {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter2);
                } else if (creationDateTimeStampString.length() == 22) {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter3);
                } else {
                    creationDateTime = LocalDateTime.parse(creationDateTimeStampString, formatter4);
                }

                creationDate = creationDateTime.toLocalDate();

                DailySale dailySale = dailySales.get(creationDate);
                if (dailySale == null) {
                    System.out.println("Something Wrong. DailySalesDao. dailySale=null");
                } else {
                    String number = resultSet.getString("DOCNUMBER");
                    String doctype = resultSet.getString("DOCNAME");
                    double quantity = resultSet.getDouble("QUANT1");
                    if (number.equals("0")) {
                        dailySale.setPresoldQuantiy(quantity + dailySale.getPresoldQuantiy());
                        System.out.println("DOCNUMBER");
                    }
                    if (doctype.equals("ΚΑΠΔ") || doctype.equals("ΚΔΑΤ1")) {
                        dailySale.setSoldQuantiy(quantity + dailySale.getSoldQuantiy());
                    }
                    if (doctype.equals("ΚΑΕΛ") || doctype.equals("ΚΠΔΤ1")) {
                        dailySale.setSoldQuantiy(dailySale.getSoldQuantiy() - quantity);
                    }
                }

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailySalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dailySales;
    }

}
