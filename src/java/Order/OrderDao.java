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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.TreeMap;
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
            ResultSet resultSet = statement.executeQuery("select * from WH_SALES_DOCS WHERE ENTRYDATE > '2024-01-01 00:00:00.000' ORDER BY DOCID;");

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
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String type = resultSet.getString("DOCNAME");
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

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
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

    public LinkedHashMap<Integer, Order> getAllOrdersForPeriod(String startDate, String endDate) {
        LinkedHashMap<String, Item> pet4UItemsRowByRow = getPet4UItemsRowByRow();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        startDate = startDate + " 00:00:00.000";
        endDate = endDate + " 23:59:59.999";
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
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String type = resultSet.getString("DOCNAME");
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

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
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
            ResultSet resultSet = statement.executeQuery("select * from WH_SALES_DOCS WHERE ENTRYDATE = '" + date + "' ORDER BY DOCID;");

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
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");
                String creationUser = resultSet.getString("USER_");

                if (!orders.containsKey(id)) {
                    Order order = new Order();
                    order.setId(id);
                    order.setDateTimeStamp(dateTime);
                    order.setNumber(number);
                    order.setCreationDateTime(creationDateTime);
                    order.setCreationUser(creationUser);
                    orders.put(id, order);
                }
                Order order = orders.get(id);

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
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
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");
                String creationUser = resultSet.getString("USER_");

                order.setId(id);
                order.setDateTimeStamp(dateTime);
                order.setNumber(number);
                order.setCreationDateTime(creationDateTime);
                order.setCreationUser(creationUser);

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

    Order getOrderById(String orderId) {
        LinkedHashMap<String, Item> pet4UItemsRowByRow = getPet4UItemsRowByRow();

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        Order order = new Order();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_SALES_DOCS WHERE DOCID = '" + orderId + "';");

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
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String quantity = resultSet.getString("QUANT1");
                String creationUser = resultSet.getString("USER_");

                order.setId(id);
                order.setDateTimeStamp(dateTime);
                order.setNumber(number);
                order.setCreationDateTime(creationDateTime);
                order.setCreationUser(creationUser);

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
                }

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
                item.setQuantity(resultSet.getString("QTYBALANCE"));
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

    LinkedHashMap<LocalDateTime, Integer> countOrdersByDate2022() {
        LinkedHashMap<LocalDateTime, Integer> ordersByDate2022 = new LinkedHashMap<>();

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        int kaelCount = 0;
        int kapdCount = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT DOCID , ENTRYDATE , DOCNAME "
                    + "  FROM [petworld].[dbo].[WH_SALES_DOCS] WHERE (DOCNAME='ΚΑΠΔ' OR DOCNAME= 'ΚΑΕΛ') "
                    + "AND ENTRYDATE between  '2022-01-01' AND '2022-12-31' order by DOCID  ;;");

            while (resultSet.next()) {
                String dateTimeStampString = resultSet.getString("ENTRYDATE");
                dateTimeStampString = dateTimeStampString.replace(".0", "");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStampString, formatter);

                String docname = resultSet.getString("DOCNAME");
                if (docname.equals("ΚΑΕΛ")) {
                    kaelCount++;
                } else {
                    if (!ordersByDate2022.containsKey(dateTime)) {
                        ordersByDate2022.put(dateTime, 1);
                    } else {
                        Integer c = ordersByDate2022.get(dateTime);
                        c++;
                        kapdCount++;
                        ordersByDate2022.put(dateTime, c);
                    }
                }

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("KAPD COUNT: " + kapdCount);
        System.out.println("KAEL COUNT: " + kaelCount);
        return ordersByDate2022;
    }

    TreeMap<LocalDate, Integer> countOrdersByDate2023() {

        TreeMap<LocalDate, Integer> ordersByDate2023 = new TreeMap<>();

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT DOCID , DATE_TIME "
                    + "  FROM [petworld].[dbo].[WH_SALES_DOCS] WHERE  "
                    + " DATE_TIME >=  '2023-01-01 00:00:00.000 ' AND DATE_TIME <= '2023-12-31 23:59:59.999' order by DOCID  ;");

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

                if (!ordersByDate2023.containsKey(creationDateTime.toLocalDate())) {
                    ordersByDate2023.put(creationDateTime.toLocalDate(), 1);
                } else {
                    Integer c = ordersByDate2023.get(creationDateTime.toLocalDate());
                    c++;
                    ordersByDate2023.put(creationDateTime.toLocalDate(), c);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ordersByDate2023;
    }

    TreeMap<LocalDate, Integer> countOrdersByDate2024() {
        TreeMap<LocalDate, Integer> ordersByDate2024 = new TreeMap<>();

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT DOCID , DATE_TIME "
                    + "  FROM [petworld].[dbo].[WH_SALES_DOCS] WHERE  "
                    + " DATE_TIME >=  '2024-01-01 00:00:00.000 ' AND DATE_TIME <= '2024-12-31 23:59:59.999' order by DOCID  ;");

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

                if (!ordersByDate2024.containsKey(creationDateTime.toLocalDate())) {
                    ordersByDate2024.put(creationDateTime.toLocalDate(), 1);
                } else {
                    Integer c = ordersByDate2024.get(creationDateTime.toLocalDate());
                    c++;
                    ordersByDate2024.put(creationDateTime.toLocalDate(), c);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ordersByDate2024;
    }

    TreeMap<LocalDate, Integer> countOrdersByDate2025() {
        TreeMap<LocalDate, Integer> ordersByDate2025 = new TreeMap<>();

        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT DOCID , DATE_TIME "
                    + "  FROM [petworld].[dbo].[WH_SALES_DOCS] WHERE  "
                    + " DATE_TIME >=  '2025-01-01 00:00:00.000 ' AND DATE_TIME <= '2025-12-31 23:59:59.999' order by DOCID  ;");

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

                if (!ordersByDate2025.containsKey(creationDateTime.toLocalDate())) {
                    ordersByDate2025.put(creationDateTime.toLocalDate(), 1);
                } else {
                    Integer c = ordersByDate2025.get(creationDateTime.toLocalDate());
                    c++;
                    ordersByDate2025.put(creationDateTime.toLocalDate(), c);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ordersByDate2025;
    }

    LinkedHashMap<Integer, Order> getOrdersOfDate(String date) {
        System.out.println("DATE: " + date);
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

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
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

    LinkedHashMap<Integer, Order> getAllSalesDocsOfDate(String date) {
        System.out.println("DATE: " + date);
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
            String query = "select * from WH_SALES_DOCS WHERE  DATE_TIME >= '" + startDate + "' AND DATE_TIME <='" + endDate + "' ORDER BY DOCID;";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);

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

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
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

    LinkedHashMap<Integer, Order> getAllDocs(String startDate, String endDate) {
        LinkedHashMap<String, Item> pet4UItemsRowByRow = getPet4UItemsRowByRow();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        startDate = startDate + " 00:00:00.000";
        endDate = endDate + " 23:59:59.999";
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
                String itemCode = resultSet.getString("ABBREVIATION");
                String description = resultSet.getString("NAME");
                String type = resultSet.getString("DOCNAME");
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

                LinkedHashMap<String, Item> items = order.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));

                    order.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    Item it = pet4UItemsRowByRow.get(itemCode);
                    if (it == null) {
                        System.out.println("NO SUCH ITEM: OrderDao");
                    } else {
                        item.setPosition(it.getPosition());
                    }
                    order.getItems().put(itemCode, item);
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

}
