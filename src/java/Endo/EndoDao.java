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
        String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DOCDATE], [FROM_WH] FROM  [petworld].[dbo].[WH_ENDA]  WHERE  [DOCDATE] >= '" + nowDate + "' ORDER BY [DOCID];";
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
                sender = translateStoreName(sender);
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
        LocalDate nowDate = LocalDate.now();
        nowDate = nowDate.minusDays(days);
        System.out.println("NOW DATE: " + nowDate);
        LinkedHashMap<String, Endo> endoInvoices = new LinkedHashMap();
        String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DOCDATE]  FROM  [petworld].[dbo].[WH_ENDP]  WHERE  [DOCDATE] >= '" + nowDate + "';";
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

                Endo endo = new Endo();
                endo.setId(id);
                endo.setDateString(date);
                endo.setSender("ΒΑΡΙΜΠΟΜΠΗ");
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

                String storeName = translateStoreName(resultSet.getString("FROM_WH"));

                String number = resultSet.getString("DOCNUMBER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String quantity = resultSet.getString("QUANTITY");
                String price = resultSet.getString("PRICEBC");

                endo.setDateString(date);
                endo.setSender(storeName);
                endo.setNumber(number);

                endo.setDate(invoiceDate);

                Item item = pet4UItemsRowByRow.get(itemCode);
                item.setQuantity(quantity);
                System.out.println("DAO GET ENDO");
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

        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT  [DOCID], [DOCNUMBER],  [DOCDATE], [FROM_WH], [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDA]"
                + " WHERE [ABBREVIATION]='" + itemCode + "' AND");
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

                String date = resultSet.getString("DOCDATE");
                String[] splittedDate = date.split(" ");
                endo.setDateString(splittedDate[0]);

                endo.setNumber(resultSet.getString("DOCNUMBER"));

                String storeName = translateStoreName(resultSet.getString("FROM_WH"));
                endo.setSender(storeName);

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
                    DeliveryItem item = sentItems.get(itemCode);
                    String sentQuantiy = item.getSentQuantity();
                    double sentQuantiyDouble = Double.parseDouble(sentQuantiy);
                    sentQuantiyDouble = sentQuantiyDouble + quantity;
                    sentQuantiy = String.valueOf(sentQuantiyDouble);
                    item.setSentQuantity(sentQuantiy);
                    sentItems.put(itemCode, item);

                } else {
                    DeliveryItem deliveredItem = new DeliveryItem();
                    DeliveryItem di = pet4UItemsRowByRow.get(itemCode);
                    String description;
                    if (di == null) {
                        description = "NO DATA FOR THIS CODE";
                    } else {
                        description = di.getDescription();
                    }
                    deliveredItem.setDescription(description);
                    deliveredItem.setCode(itemCode);
                    deliveredItem.setSentQuantity(String.valueOf(quantity));
                    deliveredItem.setDeliveredQuantity("0");
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

    LinkedHashMap<String, BindedEndos> getAllBindedEndos() {
        LinkedHashMap<String, BindedEndos> allBindedEndos = new LinkedHashMap<>();

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

                if (allBindedEndos.containsKey(bindingEndoId)) {
                    BindedEndos bindedEndos = allBindedEndos.get(bindingEndoId);

                    Endo endo = new Endo();
                    endo.setId(bindedEndoId);
                    bindedEndos.addBindedEndo(bindedEndoId, endo);

                    allBindedEndos.put(bindingEndoId, bindedEndos);
                } else {
                    BindedEndos bindedEndos = new BindedEndos();

                    bindedEndos.setBindingReceivingEndoId(bindingEndoId);

                    Endo endo = new Endo();
                    endo.setId(bindedEndoId);
                    bindedEndos.addBindedEndo(bindedEndoId, endo);

                    allBindedEndos.put(bindingEndoId, bindedEndos);
                }

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

    private String translateStoreName(String name) {
        String translatedName = name;
        switch (name) {
            case "ΑΧ-ΜΕΝ":
                translatedName = "ΜΕΝΙΔΙ";
                break;
            case "ΑΧ-ΙΩΝ":
                translatedName = "Ν. ΙΩΝΙΑ";
                break;
            case "ΑΧ-ΚΑΛ":
                translatedName = "ΚΑΛΛΙΘΕΑ";
                break;
            case "ΑΧ-ΚΟΥ":
                translatedName = "ΚΟΥΚΑΚΙ";
                break;
            case "ΑΧ-ΠΤΡ":
                translatedName = "ΠΕΤΡΟΥΠΟΛΗ";
                break;
            case "ΑΧ-ΧΑΛ":
                translatedName = "ΧΑΛΚΗΔΟΝΑ";
                break;
            case "ΑΧ-ΠΕΡ":
                translatedName = "ΠΕΡΙΣΤΕΡΙ";
                break;
            case "ΑΧ-ΑΡΓ":
                translatedName = "ΑΡΓΥΡΟΥΠΟΛΗ";
                break;
            case "ΑΧ-ΠΦΑ":
                translatedName = "Π. ΦΑΛΗΡΟ";
                break;
            case "ΑΧ-ΑΛΙ":
                translatedName = "ΑΛΙΜΟΣ";
                break;
            case "ΑΧ-ΑΓΠ":
                translatedName = "ΑΓ. ΠΑΡΑΣΚΕΥΗ";
                break;
            case "ΑΧ-ΧΛΡ":
                translatedName = "ΧΑΛΑΝΔΡΙ";
                break;
            case "ΑΧ-ΔΑΦ":
                translatedName = "ΔΑΦΝΗ";
                break;
            case "ΑΧ-ΜΙΧ":
                translatedName = "ΜΙΧΑΛΑΚΟΠΟΥΛΟΥ";
                break;
        }
        return translatedName;
    }

    Endo getEndoParalavis(String endoId, LinkedHashMap<String, Item> pet4UItemsRowByRow) {
        String sql = "SELECT  [DOCID], [DOCNUMBER],  [DOCDATE], [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDP] WHERE [DOCID]='" + endoId + "' ;";
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

                String number = resultSet.getString("DOCNUMBER");
                String itemCode = resultSet.getString("ABBREVIATION");
                String quantity = resultSet.getString("QUANTITY");
                String price = resultSet.getString("PRICEBC");

                endo.setDateString(date);
                endo.setSender("ΒΑΡΙΜΠΟΜΠΗ");
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

    LinkedHashMap<String, DeliveryItem> getReceivedItems(ArrayList<String> receivingEndoIdsArray, LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow) {
        LinkedHashMap<String, DeliveryItem> sentItems = new LinkedHashMap<>();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT  [DOCID], [DOCNUMBER],  [DOCDATE],  [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDP] WHERE ");
        StringBuilder queryBuilderIdsPart = buildStringFromArrayList(receivingEndoIdsArray);
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
                    DeliveryItem item = sentItems.get(itemCode);
                    String deliveredQuantiy = item.getDeliveredQuantity();
                    double deliveredQuantiyDouble = Double.parseDouble(deliveredQuantiy);
                    deliveredQuantiyDouble = deliveredQuantiyDouble + quantity;
                    deliveredQuantiy = String.valueOf(deliveredQuantiyDouble);
                    item.setDeliveredQuantity(deliveredQuantiy);
                    sentItems.put(itemCode, item);

                } else {
                    DeliveryItem deliveredItem = new DeliveryItem();
                    DeliveryItem di = pet4UItemsRowByRow.get(itemCode);
                    if (di == null) {
                        deliveredItem.setDescription("NO DATA FOR THIS CODE");

                    } else {
                        deliveredItem.setDescription(pet4UItemsRowByRow.get(itemCode).getDescription());

                    }
                    deliveredItem.setCode(itemCode);
                    deliveredItem.setDeliveredQuantity(String.valueOf(quantity));
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

    String bindDeltiaApostolisKaiParalavis(ArrayList<String> endoIdsArray, String receivingEndoId) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO endo_binding (endo_id, binding_endo_id) VALUES (?,?)");

            for (String endoId : endoIdsArray) {

                itemInsertStatement.setString(1, endoId);
                itemInsertStatement.setString(2, receivingEndoId);

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
        return "Endos binding  EXECUTED SUCCESSFULLY.";
    }

    ArrayList<String> getBindedIds(String binderId) {
        ArrayList<String> bindedEndos = new ArrayList<>();

        String query = "SELECT * FROM endo_binding WHERE binding_endo_id='" + binderId + "';";

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                String bindedEndoId = resultSet.getString("endo_id");
                bindedEndos.add(bindedEndoId);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bindedEndos;
    }

    String unbindeEndos(String binderId) {

        String query = "DELETE FROM endo_binding WHERE binding_endo_id='" + binderId + "';";

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);

            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

        return "UNBINDED";
    }

    ArrayList<Endo> getEndosOfItem(String itemCode, String startDate, String endDate) {
        ArrayList<Endo> endos = new ArrayList<>();
        startDate = startDate + " 00:00:00.000";
        endDate = endDate + " 23:59:59.999";

        String query = "SELECT  [DOCID], [DOCNUMBER],  [DOCDATE], [FROM_WH], [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDA]"
                + " WHERE [ABBREVIATION]='" + itemCode + "' AND DATE_TIME >= '" + startDate + "' AND DATE_TIME <='" + endDate + "' ORDER BY DOCID;";

        ResultSet resultSet;

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Endo endo = new Endo();
                endo.setId(resultSet.getString("DOCID"));

                String date = resultSet.getString("DOCDATE");
                String[] splittedDate = date.split(" ");
                endo.setDateString(splittedDate[0]);

                endo.setNumber(resultSet.getString("DOCNUMBER"));

                String storeName = translateStoreName(resultSet.getString("FROM_WH"));
                endo.setSender(storeName);

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

    String saveEndoDeliveryChecking(String endoDeliveryId, ArrayList<DeliveryItem> deliveryItems) {

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement deliveredItemsInPreparedStatement = connection.prepareStatement("INSERT INTO endo_delivery (id, item_code, sent, delivered) VALUES (?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            for (DeliveryItem deliveryItem : deliveryItems) {
                System.out.println("ItemCode:"+deliveryItem.getCode());

                deliveredItemsInPreparedStatement.setString(1, endoDeliveryId);
                deliveredItemsInPreparedStatement.setString(2, deliveryItem.getCode());
                deliveredItemsInPreparedStatement.setString(3, deliveryItem.getSentQuantity());
                deliveredItemsInPreparedStatement.setString(4, deliveryItem.getDeliveredQuantity());

                deliveredItemsInPreparedStatement.addBatch();

            }

            //Executing the batch
            deliveredItemsInPreparedStatement.executeBatch();

            System.out.println(" Batch Insertion: DONE");

            //Saving the changes
            connection.commit();
            //  deleteTripPeriodPreparedStatement.close();
            // deleteTripVoucherPreparedStatement.close();

            deliveredItemsInPreparedStatement.close();
            connection.close();
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }
    }

}
