
package Order;

import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao_V_3_1 {

    @Autowired
    private OrderDao orderDao_V_3_1;
    
 public   LinkedHashMap<Integer, Order> getOrdersOfDate(String date) {
      
        LinkedHashMap<String, Item> pet4UItemsRowByRow = getPet4UItemsRowByRow();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        String startDate = date + " 00:00:00.000";
        String endDate = date + " 23:59:59.999";
        System.out.println("START DATE: " + startDate);
        System.out.println("END DATE: " + endDate);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_SALES_DOCS WHERE DATE_TIME >= '" + startDate + "' AND DATE_TIME <='" + endDate + "' ORDER BY DOCID;");

            while (resultSet.next()) {
                String dateTimeStampString = resultSet.getString("ENTRYDATE");
                dateTimeStampString = dateTimeStampString.replace(".0", "");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStampString, formatter);

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

                int id = resultSet.getInt("DOCID");
                String number = resultSet.getString("DOCNUMBER");
                String type = resultSet.getString("DOCNAME");

                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");
                String creationUser = resultSet.getString("USER_");

                if (!orders.containsKey(id)) {
                    Order order = new Order();
                    order.setId(id);
                    order.setDateTimeStamp(dateTime);
                    order.setNumber(number);
                    order.setType(type);
                    order.setCreationDateTime(creationDateTime);
                    order.setCreationUser(creationUser);
                    orders.put(id, order);
                }
                Order order = orders.get(id);

                Item item = new Item();
                item.setCode(itemCode);
                item.setDescription(description);
                item.setQuantity(quantity);
                order.getItems().put(itemCode, item);

                Item it = pet4UItemsRowByRow.get(itemCode);
                if (it == null) {
                    System.out.println("NO SUCH ITEM: OrderDao");
                } else {
                    item.setPosition(it.getPosition());
                }
                orders.put(id, order);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao_V_3_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }
 
 public LinkedHashMap<String, Item> getPet4UItemsRowByRow() {
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                Item item = new Item();
                item.setCode(resultSet.getString("ABBREVIATION").trim());
                String description = resultSet.getString("NAME").trim();
                description = description.replace("\"", "'");//replaces all occurrences of ' `  
                item.setDescription(description);

                if (resultSet.getString("EXPR1") != null) {
                    item.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    item.setPosition("");
                }
                item.setQuantity(resultSet.getString("QTYBALANCE").trim());
                items.put(altercode, item);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao_V_3_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    LinkedHashMap<Integer, Order> getOrdersForDate(String date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
