package Delivery;

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
public class DeliveryDao_V_3_1 {

    LinkedHashMap<String, DeliveryInvoice> getDeliveryInvoices(String date) {
        LinkedHashMap<String, DeliveryInvoice> deliveryInvoices = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DOCDATE],  [SUPPLIER] FROM  [petworld].[dbo].[WH_DEPA]  WHERE  [DOCDATE] = '" + date + "' ORDER BY [DOCID];";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String id = resultSet.getString("DOCID");

                //  String date = resultSet.getString("DOCDATE");
                // String[] splittedDate = date.split(" ");
                //date = splittedDate[0];
                String number = resultSet.getString("DOCNUMBER");

                String supplier = resultSet.getString("SUPPLIER");

                DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
                deliveryInvoice.setId(id);
                deliveryInvoice.setInsertionDate(date);
                deliveryInvoice.setSupplier(supplier);
                deliveryInvoice.setNumber(number);

                deliveryInvoices.put(id, deliveryInvoice);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deliveryInvoices;
    }

}
