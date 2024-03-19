package MonthSales;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class EksagogesDaoB {

    public LinkedHashMap<String, ItemEksagoges> getLastMonthsSales(int months) {

        LinkedHashMap<String, ItemEksagoges> allItemsEksagoges = new LinkedHashMap<>();
        String sql = "SELECT * FROM month_sales ORDER BY date DESC;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            String currentDate = "FakeDate";
            int currentMonth = 0;
            while (resultSet.next()) {
                String code = resultSet.getString("code");

                String date = resultSet.getString("date");
                if (!currentDate.equals(date)) {
                    if (currentMonth > months) {
                        return allItemsEksagoges;
                    }
                    currentMonth++;
                    currentDate = date;
                }
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate saleDate = LocalDate.parse(date, formatter2);

                int eshopSales = resultSet.getInt("eshop_sales");
                int shopsSupply = resultSet.getInt("shops_supply");

                if (allItemsEksagoges.containsKey(code)) {

                    ItemEksagoges itemEksagoges = allItemsEksagoges.get(code);

                    Eksagoges eksagoges = new Eksagoges();
                    eksagoges.setEshopSales(eshopSales);
                    eksagoges.setShopsSupply(shopsSupply);

                    itemEksagoges.addSales(saleDate, eksagoges);
                    allItemsEksagoges.put(code, itemEksagoges);
                } else {
                    ItemEksagoges itemEksagoges = new ItemEksagoges();
                    itemEksagoges.setCode(code);

                    Eksagoges eksagoges = new Eksagoges();
                    eksagoges.setEshopSales(eshopSales);
                    eksagoges.setShopsSupply(shopsSupply);
                    itemEksagoges.addSales(saleDate, eksagoges);
                    allItemsEksagoges.put(code, itemEksagoges);
                }

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(MonthSalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allItemsEksagoges;
    }
}
