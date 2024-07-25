/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    public LinkedHashMap<Integer, Order> getAllOrders() {
        LinkedHashMap<String, Item> pet4UItemsRowByRow = getPet4UItemsRowByRow();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_SALES_DOCS WHERE ENTRYDATE > '2024-01-01 00:00:00.000' ;");

            while (resultSet.next()) {
                String dateTimeStampString = resultSet.getString("ENTRYDATE");

                dateTimeStampString = dateTimeStampString.replace(".0", "");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStampString, formatter);

                int id = resultSet.getInt("DOCID");
                String number = resultSet.getString("DOCNUMBER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");

                if (!orders.containsKey(id)) {
                    Order order = new Order();
                    order.setId(id);
                    order.setDateTimeStamp(dateTime);
                    order.setNumber(number);
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
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    LinkedHashMap<Integer, Order> getOrdersForDate(String date) {
        LinkedHashMap<String, Item> pet4UItemsRowByRow = getPet4UItemsRowByRow();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_SALES_DOCS WHERE ENTRYDATE = '" + date + "';");

            while (resultSet.next()) {
                String dateTimeStampString = resultSet.getString("ENTRYDATE");
                dateTimeStampString = dateTimeStampString.replace(".0", "");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStampString, formatter);

                int id = resultSet.getInt("DOCID");
                String number = resultSet.getString("DOCNUMBER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");

                if (!orders.containsKey(id)) {
                    Order order = new Order();
                    order.setId(id);
                    order.setDateTimeStamp(dateTime);
                    order.setNumber(number);
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
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    Order getOrder(String orderNumber) {

        LinkedHashMap<String, Item> pet4UItemsRowByRow = getPet4UItemsRowByRow();

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        Order order = new Order();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_SALES_DOCS WHERE DOCNUMBER = '" + orderNumber + "';");

            while (resultSet.next()) {
                String dateTimeStampString = resultSet.getString("ENTRYDATE");
                dateTimeStampString = dateTimeStampString.replace(".0", "");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStampString, formatter);

                int id = resultSet.getInt("DOCID");
                String number = resultSet.getString("DOCNUMBER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");

                order.setId(id);
                order.setDateTimeStamp(dateTime);
                order.setNumber(number);

                Item item = new Item();
                item.setCode(itemCode);
                item.setDescription(description);
                item.setQuantity(quantity);
                Item it = pet4UItemsRowByRow.get(itemCode);
                if (it == null) {
                    System.out.println("NO SUCH ITEM: OrderDao");
                } else {
                    item.setPosition(it.getPosition());
                }
                order.getItems().put(itemCode, item);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
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
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

}
