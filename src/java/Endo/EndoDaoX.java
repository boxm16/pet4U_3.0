/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class EndoDaoX {

    private DatabaseConnectionFactory databaseConnectionFactory;

    public EndoDaoX() {
        this.databaseConnectionFactory = new DatabaseConnectionFactory();
    }

    public LinkedHashMap<String, EndoBinder> getAllEndoBinders() {
        LinkedHashMap<String, EndoBinder> allEndoBinders = new LinkedHashMap<>();

        String query = "SELECT * FROM endo_binding;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                String bindedEndoId = resultSet.getString("endo_id");
                String bindingEndoId = resultSet.getString("binding_endo_id");

                if (allEndoBinders.containsKey(bindingEndoId)) {
                    EndoBinder endoBinder = allEndoBinders.get(bindingEndoId);

                    EndoApostolis endoApostolis = new EndoApostolis();
                    endoApostolis.setId(bindedEndoId);
                    endoBinder.addEndoApostolis(bindedEndoId, endoApostolis);

                    allEndoBinders.put(bindingEndoId, endoBinder);
                } else {
                    EndoBinder endoBinder = new EndoBinder();

                    EndoParalavis endoParalavis = new EndoParalavis();
                    endoParalavis.setId(bindingEndoId);
                    endoBinder.setEndoParalavis(endoParalavis);

                    EndoApostolis endoApostolis = new EndoApostolis();
                    endoApostolis.setId(bindedEndoId);
                    endoBinder.addEndoApostolis(bindedEndoId, endoApostolis);

                    allEndoBinders.put(bindingEndoId, endoBinder);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allEndoBinders;
    }

    public LinkedHashMap<String, EndoApostolis> getLastIncomingEndoApostoliss(int lastDays) {
        LocalDate nowDate = LocalDate.now();
        nowDate = nowDate.minusDays(lastDays);
        //System.out.println("NOW DATE: " + nowDate);
        LinkedHashMap<String, EndoApostolis> endoInvoices = new LinkedHashMap();
        String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DOCDATE], [FROM_WH] FROM  [petworld].[dbo].[WH_ENDA]  WHERE  [DOCDATE] >= '" + nowDate + "' ORDER BY [DOCID];";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

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
                EndoApostolis endoApostolis = new EndoApostolis();
                endoApostolis.setId(id);
                endoApostolis.setDateString(date);
                endoApostolis.setSender(sender);
                endoApostolis.setNumber(number);

                endoInvoices.put(id, endoApostolis);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endoInvoices;
    }

    public LinkedHashMap<String, EndoParalavis> getLastEndoParalaviss(int lastDays) {
        LocalDate nowDate = LocalDate.now();
        nowDate = nowDate.minusDays(lastDays);
        // System.out.println("NOW DATE: " + nowDate);
        LinkedHashMap<String, EndoParalavis> endoInvoices = new LinkedHashMap();
        String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DOCDATE]  FROM  [petworld].[dbo].[WH_ENDP]  WHERE  [DOCDATE] >= '" + nowDate + "';";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String id = resultSet.getString("DOCID");

                String date = resultSet.getString("DOCDATE");
                String[] splittedDate = date.split(" ");
                date = splittedDate[0];
                String number = resultSet.getString("DOCNUMBER");

                EndoParalavis endo = new EndoParalavis();
                endo.setId(id);
                endo.setDateString(date);
                endo.setNumber(number);
                endo.setNumberAsArrayList(number);

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

    public EndoBinder fillEndoBinder(EndoBinder proEndoBinder) {
        String sqlParalavis = "SELECT  [DOCID], [ABBREVIATION], [QUANTITY], [PRICEBC] FROM [petworld].[dbo].[WH_ENDP] WHERE [DOCID]='" + proEndoBinder.getEndoParalavis().getId() + "' ;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

        try {

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sqlParalavis);

            while (resultSet.next()) {

                String itemCode = resultSet.getString("ABBREVIATION");
                String quantity = resultSet.getString("QUANTITY");

                Item item = new Item();
                item.setQuantity(quantity);

                if (proEndoBinder.getEndoParalavis().getItems().containsKey(itemCode)) {
                    String quantity1 = proEndoBinder.getEndoParalavis().getItems().get(itemCode).getQuantity();
                    Double sum = Double.valueOf(quantity) + Double.valueOf(quantity1);
                    item.setQuantity(sum.toString());
                    proEndoBinder.getEndoParalavis().getItems().put(itemCode, item);
                } else {
                    proEndoBinder.getEndoParalavis().getItems().put(itemCode, item);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        LinkedHashMap<String, EndoApostolis> endosApostolis = proEndoBinder.getEndoApostoliss();
        ArrayList endoApostolissIds = new ArrayList(endosApostolis.keySet());

        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT  [DOCID], [ABBREVIATION], [QUANTITY]  FROM [petworld].[dbo].[WH_ENDA] WHERE ");
        StringBuilder queryBuilderIdsPart = buildStringFromArrayList(endoApostolissIds);
        StringBuilder sqlApostolis = queryBuilderInitialPart.append(" [DOCID] IN  ").append(queryBuilderIdsPart);
        LinkedHashMap<String, Double> totalSentItems = new LinkedHashMap<>();

        try {

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sqlApostolis.toString());

            while (resultSet.next()) {

                String sentItemCode = resultSet.getString("ABBREVIATION");
                Double sentQuantity = resultSet.getDouble("QUANTITY");

                if (totalSentItems.containsKey(sentItemCode)) {
                    sentQuantity = totalSentItems.get(sentItemCode) + sentQuantity;
                    totalSentItems.put(sentItemCode, sentQuantity);
                } else {

                    totalSentItems.put(sentItemCode, sentQuantity);
                }
            }

            proEndoBinder.setTotalSentItems(totalSentItems);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return proEndoBinder;
    }
//--------------------------------------------------

    private String translateStoreName(String name) {
        String translatedName = name;
        switch (name) {
            case "ΑΧ-ΜΕΝ":
                translatedName = "MΕΝΙΔΙ";
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
                translatedName = "ΑΓ_ΠΑΡΑΣΚΕΥΗ";
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

    String saveBinder(EndoBinder proEndoBinder) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO endo_binding (endo_id, binding_endo_id) VALUES (?,?)");
            LinkedHashMap<String, EndoApostolis> endoApostoliss = proEndoBinder.getEndoApostoliss();
            for (Map.Entry<String, EndoApostolis> endoApostolissEntry : endoApostoliss.entrySet()) {
                itemInsertStatement.setString(1, endoApostolissEntry.getKey());
                itemInsertStatement.setString(2, proEndoBinder.getEndoParalavis().getId());
                itemInsertStatement.addBatch();
            }

            itemInsertStatement.executeBatch();
            connection.commit();
            itemInsertStatement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Endos binding  EXECUTED SUCCESSFULLY.";
    }

    String insertNewOrdersUpload(String date, LinkedHashMap<String, EndoOrder> endoOrders) {

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement orderTitelInsertionPreparedStatement = connection.prepareStatement("INSERT INTO endo_order_title (id, date, destination, note) VALUES(?,?,?,?);");
            PreparedStatement orderedItemsInsetionPreparedStatement = connection.prepareStatement("INSERT INTO endo_order_data (order_id, item_code, item_description, ordered_quantity, sent_quantity, price, amount, comment) VALUES (?,?,?,?,?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            for (Map.Entry<String, EndoOrder> endoOrdersEntry : endoOrders.entrySet()) {

                orderTitelInsertionPreparedStatement.setString(1, endoOrdersEntry.getValue().getId());
                orderTitelInsertionPreparedStatement.setString(2, date);
                orderTitelInsertionPreparedStatement.setString(3, endoOrdersEntry.getValue().getDestination());
                orderTitelInsertionPreparedStatement.setString(4, endoOrdersEntry.getValue().getNote());

                orderTitelInsertionPreparedStatement.addBatch();

                LinkedHashMap<String, EndoOrderItem> orderedItems = endoOrdersEntry.getValue().getOrderedItems();
                for (Map.Entry<String, EndoOrderItem> orderedItemsEntry : orderedItems.entrySet()) {
                    orderedItemsInsetionPreparedStatement.setString(1, endoOrdersEntry.getValue().getId());
                    orderedItemsInsetionPreparedStatement.setString(2, orderedItemsEntry.getValue().getCode());
                    orderedItemsInsetionPreparedStatement.setString(3, orderedItemsEntry.getValue().getDescription());
                    orderedItemsInsetionPreparedStatement.setDouble(4, orderedItemsEntry.getValue().getOrderedQuantity());
                    orderedItemsInsetionPreparedStatement.setDouble(5, orderedItemsEntry.getValue().getSentQuantity());
                    orderedItemsInsetionPreparedStatement.setDouble(6, orderedItemsEntry.getValue().getPrice());
                    orderedItemsInsetionPreparedStatement.setDouble(7, orderedItemsEntry.getValue().getAmount());
                    orderedItemsInsetionPreparedStatement.setString(8, orderedItemsEntry.getValue().getComment());
                    orderedItemsInsetionPreparedStatement.addBatch();

                }

                //Executing the batch
            }
            orderTitelInsertionPreparedStatement.executeBatch();
            orderedItemsInsetionPreparedStatement.executeBatch();

            System.out.println(" Batch Insertion: DONE");

            connection.commit();

            orderTitelInsertionPreparedStatement.close();
            orderedItemsInsetionPreparedStatement.close();
            connection.close();
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class
                    .getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

    }

    LinkedHashMap<String, EndoOrder> getEndoOrdersTitles(String date) {

        LinkedHashMap<String, EndoOrder> endoOrders = new LinkedHashMap<>();

        String query = "SELECT * FROM endo_order_title  WHERE  date >=" + date + "' ORDER BY date;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                EndoOrder endoOrder = new EndoOrder();

                endoOrder.setId(resultSet.getString("id"));
                endoOrder.setDestination(resultSet.getString("destination"));
                endoOrder.setNote(resultSet.getString("note"));
                endoOrders.put(resultSet.getString("id"), endoOrder);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return endoOrders;
    }

    EndoOrder getEndoOrder(String id, LinkedHashMap<String, Item> pet4UItemsRowByRow) {
        EndoOrder endoOrder = new EndoOrder();

        String query = "SELECT * FROM endo_order_title INNER JOIN endo_order_data ON endo_order_title.id=endo_order_data.order_id WHERE id='" + id + "';";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            int rowIndex = 0;
            while (resultSet.next()) {
                if (rowIndex == 0) {
                    endoOrder.setId(resultSet.getString("id"));
                    endoOrder.setDestination(resultSet.getString("destination"));
                    endoOrder.setDateString(resultSet.getString("date"));
                    endoOrder.setNote(resultSet.getString("note"));
                }

                String itemCode = resultSet.getString("item_code");
                Item itemFromRowByRow = pet4UItemsRowByRow.get(itemCode);
                String code = itemFromRowByRow.getCode();

                LinkedHashMap<String, EndoOrderItem> orderedItems = endoOrder.getOrderedItems();
                if (orderedItems.containsKey(code)) {
                    System.out.println("ITEM CODE " + code);
                    EndoOrderItem endoOrderItem = orderedItems.get(code);

                    double orderedQuantity = endoOrderItem.getOrderedQuantity();
                    endoOrderItem.setOrderedQuantity(orderedQuantity + resultSet.getDouble("ordered_quantity"));

                    endoOrderItem.setSentQuantity(endoOrderItem.getSentQuantity() + resultSet.getDouble("sent_quantity"));

                    endoOrder.addOrderItem(code, endoOrderItem);
                } else {
                    EndoOrderItem endoOrderItem = new EndoOrderItem();

                    endoOrderItem.setOrderedAltercode(itemCode);

                    endoOrderItem.setCode(code);

                    endoOrderItem.setDescription(resultSet.getString("item_description"));
                    endoOrderItem.setOrderedQuantity(resultSet.getDouble("ordered_quantity"));
                    endoOrderItem.setSentQuantity(resultSet.getDouble("sent_quantity"));
                    endoOrderItem.setPrice(resultSet.getDouble("price"));
                    endoOrderItem.setAmount(resultSet.getDouble("amount"));
                    endoOrderItem.setComment(resultSet.getString("comment"));
                    endoOrder.addOrderItem(endoOrderItem.getCode(), endoOrderItem);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return endoOrder;
    }

    LinkedHashMap<String, EndoApostolis> getOutgoingDeltioApostolisTitles(String date) {

        //System.out.println("NOW DATE: " + nowDate);
        LinkedHashMap<String, EndoApostolis> endoInvoices = new LinkedHashMap();
        String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER], [DOCDATE], [SHLIDDESTINATION], [DESTINATION] FROM  [petworld].[dbo].[WH_ENDA_VAR]  WHERE  [DOCDATE] >='" + date + "' ORDER BY [DOCDATE];";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String id = resultSet.getString("DOCID");
                String dbDate = resultSet.getString("DOCDATE");
                String[] splittedDate = dbDate.split(" ");
                String number = resultSet.getString("DOCNUMBER");
                String destination = translateStoreNameV(resultSet.getString("DESTINATION"));
                EndoApostolis endoApostolis = new EndoApostolis();
                endoApostolis.setId(id);
                endoApostolis.setDateString(splittedDate[0]);
                endoApostolis.setReceiver(destination);
                endoApostolis.setNumber(number);

                endoInvoices.put(id, endoApostolis);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return endoInvoices;
    }

    EndoApostolis getEndoApostolisVaribobis(String id) {
        //System.out.println("NOW DATE: " + nowDate);
        EndoApostolis endoApostolis = new EndoApostolis();
        LinkedHashMap<String, EndoApostolis> endoInvoices = new LinkedHashMap();
        String sql = "SELECT * FROM  [petworld].[dbo].[WH_ENDA_VAR]  WHERE  [DOCID] = '" + id + "' ;";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            int rowIndex = 0;

            while (resultSet.next()) {
                if (rowIndex == 0) {
                    String number = resultSet.getString("DOCNUMBER");
                    String destination = resultSet.getString("DESTINATION");
                    String date = resultSet.getString("DOCDATE");
                    endoApostolis.setId(id);
                    endoApostolis.setDateString(date);
                    endoApostolis.setReceiver(destination);
                    endoApostolis.setNumber(number);
                    rowIndex++;
                }

                String itemCode = resultSet.getString("ABBREVIATION");
                String quantity = resultSet.getString("QUANTITY");
                String price = resultSet.getString("PRICEBC");
                String description = translateStoreNameV(resultSet.getString("NAME"));

                endoApostolis.setSender("ΒΑΡΙΜΠΟΜΠΗ");

                LinkedHashMap<String, Item> items = endoApostolis.getItems();
                if (items.containsKey(itemCode)) {
                    Item item = items.get(itemCode);
                    String quantity1 = item.getQuantity();
                    double sum = Double.valueOf(quantity1) + Double.valueOf(quantity);
                    item.setQuantity(String.valueOf(sum));
                    endoApostolis.getItems().put(itemCode, item);
                } else {
                    Item item = new Item();
                    item.setCode(itemCode);
                    item.setQuantity(quantity);
                    item.setDescription(description);
                    endoApostolis.getItems().put(itemCode, item);
                }

            }
            endoInvoices.put(id, endoApostolis);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return endoApostolis;
    }

    private String translateStoreNameV(String name) {
        String translatedName = name;
        switch (name) {
            case "ΥΠ: (ΜΕΝΙΔΙ) Λ. ΚΑΡΑΜΑΝΛΗ 80, ΑΧΑΡΝΑΙ":
                translatedName = "MΕΝΙΔΙ";
                break;
            case "ΥΠ. (Ν. ΙΩΝΙΑ) ΙΦΙΓΕΝΕΙΑΣ 36, ΗΡΑΚΛΕΙΟ":
                translatedName = "Ν. ΙΩΝΙΑ";
                break;
            case "ΥΠ: (ΚΑΛΛΙΘΕΑ) ΕΛ. ΒΕΝΙΖΕΛΟΥ 288, ΚΑΛΛΙΘΕΑ":
                translatedName = "ΚΑΛΛΙΘΕΑ";
                break;
            case "ΥΠ: (KOYKAKI) ΕΛ. ΒΕΝΙΖΕΛΟΥ 46, ΚΑΛΛΙΘΕΑ":
                translatedName = "ΚΟΥΚΑΚΙ";
                break;
            case "ΥΠ. (ΠΕΤΡΟΥΠΟΛΗ) 25ης ΜΑΡΤΙΟΥ 172, ΠΕΤΡΟΥΠΟΛΗ":
                translatedName = "ΠΕΤΡΟΥΠΟΛΗ";
                break;
            case "ΕΔΡΑ: ΑΧΑΡΝΩΝ 471, Ν. ΧΑΛΚΗΔΟΝΑ":
                translatedName = "ΧΑΛΚΗΔΟΝΑ";
                break;
            case "ΥΠ. (ΠΕΡΙΣΤΕΡΙ) ΒΑΣ. ΑΛΕΞΑΝΔΡΟΥ 27, ΠΕΡΙΣΤΕΡΙ":
                translatedName = "ΠΕΡΙΣΤΕΡΙ";
                break;
            case "ΥΠ. (ΑΡΓΥΡΟΥΠΟΛΗ) ΚΥΠΡΟΥ 8, ΑΡΓΥΡΟΥΠΟΛΗ":
                translatedName = "ΑΡΓΥΡΟΥΠΟΛΗ";
                break;
            case "ΥΠ. (Π. ΦΑΛΗΡΟ) ΕΛ. ΒΕΝΙΖΕΛΟΥ 198, Π. ΦΑΛΗΡΟ":
                translatedName = "Π. ΦΑΛΗΡΟ";
                break;
            case "ΥΠ: (ΑΛΙΜΟΣ) Λ. ΚΑΛΑΜΑΚΙΟΥ 89, ΑΛΙΜΟΣ":
                translatedName = "ΑΛΙΜΟΣ";
                break;
            case "ΥΠ: (ΑΓΙΑ ΠΑΡ.) ΧΑΛΑΝΔΡΙΟΥ 6, ΑΓ. ΠΑΡΑΣΚΕΥΗ":
                translatedName = "ΑΓ_ΠΑΡΑΣΚΕΥΗ";
                break;
            case "ΥΠ: (ΧΑΛΑΝΔΡΙ) ΠΕΝΤΕΛΗΣ 31, ΧΑΛΑΝΔΡΙ":
                translatedName = "ΧΑΛΑΝΔΡΙ";
                break;
            case "ΥΠ: (ΔΑΦΝΗ) ΑΛ. ΠΑΠΑΝΑΣΤΑΣΙΟΥ 5, ΔΑΦΝΗ":
                translatedName = "ΔΑΦΝΗ";
                break;
            case "ΥΠ: ΜΙΧΑΛΑΚΟΠΟΥΛΟΥ 175, ΑΜΠΕΛΟΚΗΠΟΙ":
                translatedName = "ΜΙΧΑΛΑΚΟΠΟΥΛΟΥ";
                break;
        }
        return translatedName;
    }

    String bindOrderWithEndo(String orderId, String outgoingEndoId) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();

            PreparedStatement itemInsertStatement = connection.prepareStatement("INSERT INTO endo_binding_varibobi (order_id, binding_endo_id) VALUES (?,?)");
            itemInsertStatement.setString(1, orderId);
            itemInsertStatement.setString(2, outgoingEndoId);

            itemInsertStatement.execute();

            itemInsertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Endos binding  EXECUTED SUCCESSFULLY.";
    }

    LinkedHashMap<String, String> getAllBindedOrdersTitles() {
        LinkedHashMap<String, String> allBindedOrders = new LinkedHashMap<>();

        String query = "SELECT * FROM endo_binding_varibobi;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                String orderId = resultSet.getString("order_id");
                String endoId = resultSet.getString("binding_endo_id");

                allBindedOrders.put(orderId, endoId);

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allBindedOrders;
    }

    String getBindedOrderIdByEndoApostolis(String outgoingEndoId) {

        String query = "SELECT order_id FROM endo_binding_varibobi WHERE binding_endo_id=" + outgoingEndoId + ";";
        String orderId = "";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                orderId = resultSet.getString("order_id");

                return orderId;
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderId;
    }

    String unbindOrderWithEndo(String orderId, String outgoingEndoId) {

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();

            PreparedStatement deletionStatement = connection.prepareStatement("DELETE FROM endo_binding_varibobi WHERE order_id=? AND  binding_endo_id=?");
            deletionStatement.setString(1, orderId);
            deletionStatement.setString(2, outgoingEndoId);

            deletionStatement.executeUpdate();

            deletionStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class
                    .getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Endos binding  EXECUTED SUCCESSFULLY.";
    }
}
