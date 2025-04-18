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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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

                String sender = resultSet.getString("FROM_WH");
                String storeCode = getStoreCode(sender);
                String number = storeCode + "-" + resultSet.getString("DOCNUMBER");

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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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

    private String getStoreCode(String name) {
        String storeCode = name;
        switch (name) {
            case "ΑΧ-ΜΕΝ":
                storeCode = "03";
                break;
            case "ΑΧ-ΙΩΝ":
                storeCode = "24";
                break;
            case "ΑΧ-ΚΑΛ":
                storeCode = "04";
                break;
            case "ΑΧ-ΚΟΥ":
                storeCode = "11";
                break;
            case "ΑΧ-ΠΤΡ":
                storeCode = "31";
                break;
            case "ΑΧ-ΧΑΛ":
                storeCode = "01";
                break;
            case "ΑΧ-ΠΕΡ":
                storeCode = "29";
                break;
            case "ΑΧ-ΑΡΓ":
                storeCode = "26";
                break;
            case "ΑΧ-ΠΦΑ":
                storeCode = "33";
                break;
            case "ΑΧ-ΑΛΙ":
                storeCode = "05";
                break;
            case "ΑΧ-ΑΓΠ":
                storeCode = "06";
                break;
            case "ΑΧ-ΧΛΡ":
                storeCode = "22";
                break;
            case "ΑΧ-ΔΑΦ":
                storeCode = "07";
                break;
            case "ΑΧ-ΜΙΧ":
                storeCode = "12";
                break;
        }
        return storeCode;
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            PreparedStatement orderedItemsInsetionPreparedStatement = connection.prepareStatement("INSERT INTO endo_order_data (order_id, item_code, item_barcode, item_description, ordered_quantity, sent_quantity, price, amount, comment) VALUES (?,?,?,?,?,?,?,?,?);");

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
                    orderedItemsInsetionPreparedStatement.setString(3, orderedItemsEntry.getValue().getItemBarcode());
                    orderedItemsInsetionPreparedStatement.setString(4, orderedItemsEntry.getValue().getDescription());
                    orderedItemsInsetionPreparedStatement.setDouble(5, orderedItemsEntry.getValue().getOrderedQuantity());
                    orderedItemsInsetionPreparedStatement.setDouble(6, orderedItemsEntry.getValue().getSentQuantity());
                    orderedItemsInsetionPreparedStatement.setDouble(7, orderedItemsEntry.getValue().getPrice());
                    orderedItemsInsetionPreparedStatement.setDouble(8, orderedItemsEntry.getValue().getAmount());
                    orderedItemsInsetionPreparedStatement.setString(9, orderedItemsEntry.getValue().getComment());
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

    }

    LinkedHashMap<String, EndoOrder> getEndoOrdersTitles(String date) {

        LinkedHashMap<String, EndoOrder> endoOrders = new LinkedHashMap<>();

        String query = "SELECT * FROM endo_order_title  WHERE  date >='" + date + "' ORDER BY date;";

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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
                    String dateString = resultSet.getString("date");
                    endoOrder.setDateString(dateString);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    endoOrder.setDate(date);

                    endoOrder.setNote(resultSet.getString("note"));
                }

                String itemCode = resultSet.getString("item_code");
                String itemBarcode = resultSet.getString("item_barcode");
                String comment = resultSet.getString("comment");

                Item itemFromRowByRow = pet4UItemsRowByRow.get(itemCode);
                if (itemFromRowByRow == null) {
                    itemFromRowByRow = pet4UItemsRowByRow.get(itemBarcode);
                    if (itemFromRowByRow != null) {
                        comment = comment + ":" + "Item Found By Barcode";
                    }
                }

                if (itemFromRowByRow != null) {
                    String code = itemFromRowByRow.getCode();

                    EndoOrderItem endoOrderItem = new EndoOrderItem();

                    endoOrderItem.setOrderedAltercode(itemCode);

                    endoOrderItem.setCode(code);
                    endoOrderItem.setItemBarcode(itemBarcode);

                    endoOrderItem.setDescription(resultSet.getString("item_description"));
                    endoOrderItem.setOrderedQuantity(resultSet.getDouble("ordered_quantity"));
                    endoOrderItem.setSentQuantity(resultSet.getDouble("sent_quantity"));
                    endoOrderItem.setPrice(resultSet.getDouble("price"));
                    endoOrderItem.setAmount(resultSet.getDouble("amount"));
                    endoOrderItem.setComment(comment);
                    endoOrder.addOrderItem(endoOrderItem.getCode(), endoOrderItem);
                } else {
                    comment = comment + ":" + "Item Can Not Be Found In Database View";

                    EndoOrderItem endoOrderItem = new EndoOrderItem();

                    endoOrderItem.setOrderedAltercode(itemCode);

                    endoOrderItem.setCode(itemCode);
                    endoOrderItem.setItemBarcode(itemBarcode);

                    endoOrderItem.setDescription(resultSet.getString("item_description"));
                    endoOrderItem.setOrderedQuantity(resultSet.getDouble("ordered_quantity"));
                    endoOrderItem.setSentQuantity(resultSet.getDouble("sent_quantity"));
                    endoOrderItem.setPrice(resultSet.getDouble("price"));
                    endoOrderItem.setAmount(resultSet.getDouble("amount"));
                    endoOrderItem.setComment(comment);
                    endoOrder.addOrderItem(endoOrderItem.getCode(), endoOrderItem);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
                    endoApostolis.setId(id);
                    String number = resultSet.getString("DOCNUMBER");
                    //  String destination = resultSet.getString("DESTINATION");
                    String destination = translateStoreNameV(resultSet.getString("DESTINATION"));
                    String dateString = resultSet.getString("DOCDATE");
                    String[] splittedDate = dateString.split(" ");

                    dateString = splittedDate[0];
                    endoApostolis.setDateString(dateString);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    endoApostolis.setDate(date);

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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Endos binding  EXECUTED SUCCESSFULLY.";
    }

    String copyEndoApostolis(EndoApostolis endoApostolisVaribobis) {

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement titelInsertionPreparedStatement = connection.prepareStatement("INSERT INTO endo_locker_title (id, date, number, locked_time_stamp, destination) VALUES(?,?,?,?,?);");
            PreparedStatement itemsInsetionPreparedStatement = connection.prepareStatement("INSERT INTO endo_locker_data (id, item_code, quantity) VALUES (?,?,?);");

            System.out.println("Starting INSERTION: ....");

            titelInsertionPreparedStatement.setString(1, endoApostolisVaribobis.getId());
            titelInsertionPreparedStatement.setString(2, endoApostolisVaribobis.getDateString());
            titelInsertionPreparedStatement.setString(3, endoApostolisVaribobis.getNumber());
            titelInsertionPreparedStatement.setString(4, LocalDateTime.now().toString());
            titelInsertionPreparedStatement.setString(5, endoApostolisVaribobis.getReceiver());
            titelInsertionPreparedStatement.addBatch();

            LinkedHashMap<String, Item> items = endoApostolisVaribobis.getItems();
            for (Map.Entry<String, Item> itemEntry : items.entrySet()) {
                itemsInsetionPreparedStatement.setString(1, endoApostolisVaribobis.getId());
                itemsInsetionPreparedStatement.setString(2, itemEntry.getValue().getCode());
                itemsInsetionPreparedStatement.setString(3, itemEntry.getValue().getQuantity());
                itemsInsetionPreparedStatement.addBatch();

                //Executing the batch
            }
            titelInsertionPreparedStatement.executeBatch();
            itemsInsetionPreparedStatement.executeBatch();

            System.out.println(" Batch Insertion: DONE");

            connection.commit();

            titelInsertionPreparedStatement.close();
            itemsInsetionPreparedStatement.close();
            connection.close();
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }
    }

    boolean endoApostolisIsLocked(String outgoingEndoId) {
        String query = "SELECT id FROM endo_locker_title WHERE id=" + outgoingEndoId + ";";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next() == false) {
                resultSet.close();
                statement.close();
                connection.close();
                return false;
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    ArrayList<String> getAllLockedOutgoingDeltiaApostolisIds(String date) {
        ArrayList<String> allLockedOutgoingDeltiaApostolisIds = new ArrayList<>();
        String query = "SELECT id FROM endo_locker_title WHERE date >= '" + date + "'";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                allLockedOutgoingDeltiaApostolisIds.add(resultSet.getString("id"));

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allLockedOutgoingDeltiaApostolisIds;
    }

    ArrayList<String> getAllChangedOutgoingDeltiaApostolisIds(ArrayList<String> lockedOutgoingDeltiaApostolis) {
        ArrayList<String> changed = new ArrayList<>();
        LinkedHashMap<String, EndoApostolis> endoApostolissVaribobis = getEndoApostolissVaribobis(lockedOutgoingDeltiaApostolis);
        LinkedHashMap<String, EndoApostolis> lockedEndos = getLockedEndos();
        for (Map.Entry<String, EndoApostolis> endoApostolisVaribobis : endoApostolissVaribobis.entrySet()) {
            EndoApostolis lockedEndo = lockedEndos.get(endoApostolisVaribobis.getKey());
            boolean isChanged = endoIsChanged(endoApostolisVaribobis.getValue(), lockedEndo);
            if (isChanged) {
                changed.add(endoApostolisVaribobis.getKey());
            }
        }
        return changed;
    }

    private LinkedHashMap<String, EndoApostolis> getEndoApostolissVaribobis(ArrayList<String> lockedOutgoingDeltiaApostolis) {
        LinkedHashMap<String, EndoApostolis> endoApostoliss = new LinkedHashMap();

        StringBuilder inPartForSqlQuery = buildStringFromArrayList(lockedOutgoingDeltiaApostolis);
        StringBuilder query
                = new StringBuilder("SELECT * FROM  [petworld].[dbo].[WH_ENDA_VAR]  WHERE  [DOCID] IN ")
                        .append(inPartForSqlQuery).append(" ;");
        //   System.out.println(query);

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String id = resultSet.getString("DOCID");
                if (!endoApostoliss.containsKey(id)) {
                    EndoApostolis endoApostolis = new EndoApostolis();
                    endoApostolis.setId(id);
                    endoApostoliss.put(id, endoApostolis);

                }
                EndoApostolis endoApostolis = endoApostoliss.get(id);
                String itemCode = resultSet.getString("ABBREVIATION");
                String quantity = resultSet.getString("QUANTITY");

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
                    endoApostolis.getItems().put(itemCode, item);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return endoApostoliss;
    }

    private LinkedHashMap<String, EndoApostolis> getLockedEndos() {
        LinkedHashMap<String, EndoApostolis> endoApostoliss = new LinkedHashMap();

        String query = "SELECT * FROM endo_locker_data";
        System.out.println(query);

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getMySQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                if (!endoApostoliss.containsKey(id)) {
                    EndoApostolis endoApostolis = new EndoApostolis();
                    endoApostolis.setId(id);
                    endoApostoliss.put(id, endoApostolis);

                }
                EndoApostolis endoApostolis = endoApostoliss.get(id);
                String itemCode = resultSet.getString("item_code");
                String quantity = resultSet.getString("quantity");

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
                    endoApostolis.getItems().put(itemCode, item);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return endoApostoliss;
    }

    private boolean endoIsChanged(EndoApostolis endoApostolis, EndoApostolis lockedEndo) {

        LinkedHashMap<String, Item> items = endoApostolis.getItems();
        LinkedHashMap<String, Item> items1 = lockedEndo.getItems();
        if (items.size() != items1.size()) {
            System.out.println("Endo Apostolis ID: " + endoApostolis.getId() + " items size: " + items.size() + "--" + "items size: " + items1.size());

            return true;
        }
        for (Map.Entry<String, Item> itemsEntry : items.entrySet()) {
            String key = itemsEntry.getKey();
            Item removedItem = items1.remove(key);
            if (removedItem == null) {
                System.out.println("Endo Apostolis ID: " + endoApostolis.getId() + " Item Code Changed for item with Item Code : " + key);
                return true;
            }
            String quantity1 = itemsEntry.getValue().getQuantity();
            String quantity = removedItem.getQuantity();
            if (!quantity.equals(quantity1)) {
                System.out.println("Endo Apostolis ID: " + endoApostolis.getId() + " Quantity changed for item code: " + key);
                return true;
            }

        }
        return false;
    }

    String deleteEndoApostolisVaribobis(String outgoingEndoId) {
        String query1 = "DELETE FROM endo_order_title WHERE id='" + outgoingEndoId + "'";
        String query2 = "DELETE FROM endo_order_data WHERE order_id='" + outgoingEndoId + "'";

        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);
            statement.execute(query2);
            statement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Order with id: " + outgoingEndoId + "hase been deleted.";
    }

    LinkedHashMap<String, EndoOrder> getEndoOrders(LinkedHashMap<String, EndoOrder> endoOrdersTitles) {
        LinkedHashMap<String, EndoOrder> endoOrders = new LinkedHashMap();
        ArrayList<String> keys = new ArrayList<>(endoOrdersTitles.keySet());
        StringBuilder inPart = buildStringFromArrayList(keys);

        String query = "SELECT * FROM endo_order_title INNER JOIN endo_order_data ON endo_order_title.id=endo_order_data.order_id WHERE id IN " + inPart + ";";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String orderId = resultSet.getString("id");
                String itemCode = resultSet.getString("item_code");

                if (endoOrders.containsKey(orderId)) {
                    EndoOrderItem endoOrderItem = new EndoOrderItem();

                    endoOrderItem.setCode(itemCode);
                    endoOrderItem.setDescription(resultSet.getString("item_description"));
                    endoOrders.get(orderId).addOrderItem(itemCode, endoOrderItem);

                } else {
                    EndoOrder endoOrder = new EndoOrder();

                    endoOrder.setId(orderId);
                    endoOrder.setDestination(resultSet.getString("destination"));
                    endoOrder.setDateString(resultSet.getString("date"));
                    EndoOrderItem endoOrderItem = new EndoOrderItem();

                    endoOrderItem.setCode(itemCode);
                    endoOrderItem.setDescription(resultSet.getString("item_description"));
                    endoOrder.addOrderItem(itemCode, endoOrderItem);
                    endoOrders.put(orderId, endoOrder);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endoOrders;
    }

    public LinkedHashMap<String, EndoPackaging> getAllEndoPackaging() {

        LinkedHashMap<String, EndoPackaging> allEndoPackaging = new LinkedHashMap();

        String query = "SELECT * FROM endo_packaging;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String itemCode = resultSet.getString("item_code");
                int item = resultSet.getInt("item");
                int label = resultSet.getInt("label");

                EndoPackaging endoPackaging = new EndoPackaging();

                endoPackaging.setId(id);
                endoPackaging.setItemCode(itemCode);
                endoPackaging.setItem(item);
                endoPackaging.setLabel(label);
                allEndoPackaging.put(itemCode, endoPackaging);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allEndoPackaging;
    }

    EndoPackaging getEndoPackaging(String itemCode) {
        EndoPackaging endoPackaging = null;
        String query = "SELECT * FROM endo_packaging WHERE item_code='" + itemCode + "';";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");

                int item = resultSet.getInt("item");
                int label = resultSet.getInt("label");

                endoPackaging = new EndoPackaging();

                endoPackaging.setId(id);
                endoPackaging.setItemCode(itemCode);
                endoPackaging.setItem(item);
                endoPackaging.setLabel(label);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return endoPackaging;
    }

    String insertEndoPackaging(EndoPackaging endoPackaging) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO endo_packaging (item_code,  item, label) VALUES (?,?,?);");

            insertStatement.setString(1, endoPackaging.getItemCode());
            insertStatement.setInt(2, endoPackaging.getItem());
            insertStatement.setInt(3, endoPackaging.getLabel());

            insertStatement.executeUpdate();

            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

        return "Endo Packaging SAVED SUCCESSFULLY.";
    }

    String editEndoPackaging(EndoPackaging endoPackaging) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();

        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE endo_packaging  SET item=? , label=? WHERE item_code=?");

            updateStatement.setInt(1, endoPackaging.getItem());
            updateStatement.setInt(2, endoPackaging.getLabel());
            updateStatement.setString(3, endoPackaging.getItemCode());
            updateStatement.executeUpdate();
            updateStatement.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

        return "Endo Packaging EDITED SUCCESSFULLY.";
    }

}
