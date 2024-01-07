/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;
import Delivery.DeliveryItem;
import Search.SearchDao;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EndoDao {

    private DatabaseConnectionFactory databaseConnectionFactory;

    public EndoDao() {
        this.databaseConnectionFactory = new DatabaseConnectionFactory();

    }

    public ArrayList<DeliveryItem> getAllPet4UItemsRowByRowWithDeepSearch() {

        ArrayList<DeliveryItem> items = new ArrayList();

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            String sql = "SELECT [petworld].[EliteUser].[INI].[ID]  AS ID, "
                    + "ABBREVIATION, "
                    + "[petworld].[EliteUser].[INI].[NAME]  AS  NAME, "
                    + "ALTERNATECODE,  "
                    + "CODEDESCRIPTION, "
                    + "[petworld].[EliteUser].[IR1].[NAME]  AS  EXPR1, "
                    + "QTYBALANCE, "
                    + "[petworld].[EliteUser].[IR2].NAME  AS  EXPR2 "
                    + "FROM [petworld].[EliteUser].[INI] "
                    + "INNER JOIN [petworld].[EliteUser].[AIC] ON [petworld].[EliteUser].[AIC].INIID=[petworld].[EliteUser].[INI].ID "
                    + "LEFT JOIN [petworld].[EliteUser].[IR1] ON [petworld].[EliteUser].[IR1].ID= [petworld].[EliteUser].[INI].IF1ID "
                    + "INNER JOIN [petworld].[EliteUser].[ICV] ON [petworld].[EliteUser].[ICV].INIID=[petworld].[EliteUser].[INI].ID "
                    + "LEFT JOIN [petworld].[EliteUser].[IR2] ON [petworld].[EliteUser].[IR2].ID=[petworld].[EliteUser].[INI].IF2ID "
                    + "WHERE [petworld].[EliteUser].[ICV].FYEID=18 "
                    + "AND [petworld].[EliteUser].[ICV].SCOID=13 "
                    + "AND [petworld].[EliteUser].[ICV].BRAID=10 "
                    + "AND [petworld].[EliteUser].[ICV].WARID=11 "
                    //  + " AND ALTERNATECODE LIKE '" + altercodeMask + "'"
                    + "ORDER BY EXPR1;";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                DeliveryItem item = new DeliveryItem();
                item.setCode(resultSet.getString("ABBREVIATION").trim());
                String description = resultSet.getString("NAME").trim();

                description = description.replace("\"", "'");//replaces all occurrences of ' `  
                item.setDescription(description);

                if (resultSet.getString("EXPR1") != null) {
                    item.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    item.setPosition("");
                }
                item.setAltercode(altercode);
                items.add(item);

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SearchDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    String saveDeltioApostolis(Endo endo) {

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO endo (id, date,  type, sender, receiver, item_code, quantity) VALUES (?,?,?,?,?,?,?)");

            LinkedHashMap<String, Item> items = endo.getItems();
            for (Map.Entry<String, Item> itemsEntry : items.entrySet()) {

                itemInsertStatement.setString(1, endo.getId());
                itemInsertStatement.setString(2, endo.getDateString());
                itemInsertStatement.setString(3, endo.getType());
                itemInsertStatement.setString(4, endo.getSender());
                itemInsertStatement.setString(5, endo.getReceiver());
                itemInsertStatement.setString(6, itemsEntry.getValue().getCode());
                itemInsertStatement.setString(7, itemsEntry.getValue().getQuantity());

                itemInsertStatement.addBatch();

            }

            itemInsertStatement.executeBatch();
            connection.commit();
            itemInsertStatement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "DELTIO APOSTOLIS SAVED SUCCESSFULLY.";
    }

    LinkedHashMap<String, Endo> getLastIncomingEndos(int days) {
        LocalDate nowDate = LocalDate.now();
        nowDate = nowDate.minusDays(days);
        System.out.println("NOW DATE: " + nowDate);
        LinkedHashMap<String, Endo> endoInvoices = new LinkedHashMap();
        String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DOCDATE], [FROM_WH] FROM  [petworld].[dbo].[WH_ENDA]  WHERE  [DOCDATE] >= '" + nowDate + "';";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String id = resultSet.getString("DOCID");

                String date = resultSet.getString("DOCDATE");
                String[] splittedDate = date.split(" ");
                date = splittedDate[0];
                String number = resultSet.getString("DOCNUMBER");

                String sender = resultSet.getString("FROM_WH");

                Endo endo = new Endo();
                endo.setId(id);
                endo.setDateString(date);
                endo.setSender(sender);
                endo.setNumber(number);

                endoInvoices.put(id, endo);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endoInvoices;
    }

    LinkedHashMap<String, Endo> getLastReceivingEndos(int days) {
        LinkedHashMap<String, Endo> endoInvoices = new LinkedHashMap();
        String sql = "SELECT DISTINCT  id, date, sender FROM endo WHERE type='PARALAVI' ORDER BY date DESC;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            String currentDate = "FakeDate";
            int currentDay = 0;
            while (resultSet.next()) {

                String id = resultSet.getString("id");

                String date = resultSet.getString("date");
                if (!currentDate.equals(date)) {
                    if (currentDay > days) {
                        return endoInvoices;
                    }
                    currentDay++;
                    currentDate = date;
                }
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate invoiceDate = LocalDate.parse(date, formatter2);

                String sender = resultSet.getString("sender");

                Endo endo = new Endo();
                id = "355648";
                endo.setId(id);
                endo.setDateString(date);
                endo.setSender(sender);

                endoInvoices.put(id, endo);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endoInvoices;
    }

    Endo getEndo(String endoId, LinkedHashMap<String, Item> pet4UItemsRowByRow) {
        String sql = "SELECT  [DOCID], [DOCNUMBER],  [DOCDATE], [FROM_WH], [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDA] WHERE [DOCID]='" + endoId + "' ;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        Endo endo = new Endo();
        endo.setId(endoId);
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String date = resultSet.getString("DOCDATE");
                String[] splittedDate = date.split(" ");
                date = splittedDate[0];
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate invoiceDate = LocalDate.parse(date, formatter2);

                String sender = resultSet.getString("FROM_WH");

                String number = resultSet.getString("DOCNUMBER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String quantity = resultSet.getString("QUANTITY");
                String price = resultSet.getString("PRICEBC");

                endo.setDateString(date);
                endo.setSender(sender);
                endo.setNumber(number);

                endo.setDate(invoiceDate);

                Item item = pet4UItemsRowByRow.get(itemCode);
                item.setQuantity(quantity);

                endo.getItems().put(itemCode, item);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endo;
    }

    ArrayList<Endo> getEndosOfItem(String itemCode, ArrayList<String> endoIdsArray) {
        ArrayList<Endo> endos = new ArrayList<>();

        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT  [DOCID], [DOCNUMBER],  [DOCDATE], [FROM_WH], [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDA] WHERE [ABBREVIATION]=" + itemCode + " AND");
        StringBuilder queryBuilderIdsPart = buildStringFromArrayList(endoIdsArray);
        StringBuilder query = queryBuilderInitialPart.append(" [DOCID] IN  ").append(queryBuilderIdsPart);

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                Endo endo = new Endo();
                endo.setId(resultSet.getString("DOCID"));

                endo.setNumber(resultSet.getString("DOCNUMBER"));
                endo.setDateString(resultSet.getString("DOCDATE"));
                endo.setSender(resultSet.getString("FROM_WH"));
                Item item = new Item();
                item.setCode(resultSet.getString("ABBREVIATION"));
                item.setQuantity(resultSet.getString("QUANTITY"));
                LinkedHashMap<String, Item> items = new LinkedHashMap<>();
                items.put(itemCode, item);
                endo.setItems(items);
                endos.add(endo);

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endos;
    }

    LinkedHashMap<String, DeliveryItem> getSentItems(ArrayList<String> endoIdsArray, LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow) {
        LinkedHashMap<String, DeliveryItem> sentItems = new LinkedHashMap<>();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT  [DOCID], [DOCNUMBER],  [DOCDATE], [FROM_WH], [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDA] WHERE ");
        StringBuilder queryBuilderIdsPart = buildStringFromArrayList(endoIdsArray);
        StringBuilder query = queryBuilderInitialPart.append(" [DOCID] IN  ").append(queryBuilderIdsPart);

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {

                String itemCode = resultSet.getString("ABBREVIATION");
                Double quantity = resultSet.getDouble("QUANTITY");
                Double price = resultSet.getDouble("PRICEBC");
                if (sentItems.containsKey(itemCode)) {
                    DeliveryItem deliveredItem = sentItems.get(itemCode);
                    String sentQuantiy = deliveredItem.getSentQuantity();
                    double sentQuantiyDouble = Double.parseDouble(sentQuantiy);
                    sentQuantiyDouble = sentQuantiyDouble + quantity;
                    sentQuantiy = String.valueOf(sentQuantiyDouble);
                    deliveredItem.setDeliveredQuantity(sentQuantiy);
                    sentItems.put(itemCode, deliveredItem);

                } else {
                    DeliveryItem deliveredItem = new DeliveryItem();
                    deliveredItem.setDescription(pet4UItemsRowByRow.get(itemCode).getDescription());
                    deliveredItem.setCode(itemCode);
                    deliveredItem.setSentQuantity(String.valueOf(quantity));
                    sentItems.put(itemCode, deliveredItem);
                }

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sentItems;
    }

    private StringBuilder buildStringFromArrayList(ArrayList<String> arrayList) {

        StringBuilder stringBuilder = new StringBuilder("(");
        if (arrayList.isEmpty()) {
            stringBuilder.append(")");
            return stringBuilder;
        }
        int x = 0;
        for (String entry : arrayList) {
            if (x == 0) {
                stringBuilder.append("'").append(entry).append("'");
            } else {
                stringBuilder.append(",'").append(entry).append("'");
            }
            if (x == arrayList.size() - 1) {
                stringBuilder.append(")");
            }
            x++;
        }
        return stringBuilder;
    }

    LinkedHashMap<String, DeliveryItem> getDeliveredItems() {
        LinkedHashMap<String, DeliveryItem> deliveredItems = new LinkedHashMap<>();

        String query = "SELECT * FROM endo WHERE id=4323435";

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {

                String itemCode = resultSet.getString("item_code");
                int quantity = resultSet.getInt("quantity");
                if (deliveredItems.containsKey(itemCode)) {
                    DeliveryItem deliveredItem = deliveredItems.get(itemCode);
                    String deliveredQuantity = deliveredItem.getDeliveredQuantity();
                    int deliveredQuantityInt = Integer.parseInt(deliveredQuantity);
                    deliveredQuantityInt = deliveredQuantityInt + quantity;
                    deliveredQuantity = String.valueOf(deliveredQuantityInt);
                    deliveredItem.setDeliveredQuantity(deliveredQuantity);
                    deliveredItems.put(itemCode, deliveredItem);

                } else {
                    DeliveryItem deliveredItem = new DeliveryItem();
                    deliveredItem.setCode(itemCode);
                    deliveredItem.setDeliveredQuantity(String.valueOf(quantity));
                    deliveredItems.put(itemCode, deliveredItem);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deliveredItems;
    }

    LinkedHashMap<String, String> getAllBindedEndos() {
        LinkedHashMap<String, String> allBindedEndos = new LinkedHashMap<>();

        String query = "SELECT * FROM endo_binding;";

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                String bindedEndoId = resultSet.getString("endo_id");
                String bindingEndoId = resultSet.getString("binding_endo_id");
                allBindedEndos.put(bindedEndoId, bindingEndoId);

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allBindedEndos;
    }

    LinkedHashMap<String, DeliveryItem> getPet4UItemsRowByRow() {
        LinkedHashMap<String, DeliveryItem> items = new LinkedHashMap();
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH1;");

            while (resultSet.next()) {
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                DeliveryItem item = new DeliveryItem();
                item.setCode(resultSet.getString("ABBREVIATION").trim());
                String description = resultSet.getString("NAME").trim();

                description = description.replace("\"", "'");//replaces all occurrences of ' `  
                item.setDescription(description);

                if (resultSet.getString("EXPR1") != null) {
                    item.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    item.setPosition("");
                }
                item.setAltercode(altercode);
                items.put(altercode, item);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }
}
