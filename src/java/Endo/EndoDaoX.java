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

                proEndoBinder.getEndoParalavis().getItems().put(itemCode, item);

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
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndoDaoX.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }

    }

    LinkedHashMap<String, EndoOrder> getEndoOrdersTitles(String date) {

        LinkedHashMap<String, EndoOrder> endoOrders = new LinkedHashMap<>();

        String query = "SELECT * FROM endo_order_title;";

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
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endoOrders;
    }

    EndoOrder getEndoOrder(String id) {
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
                EndoOrderItem endoOrderItem = new EndoOrderItem();
                endoOrderItem.setCode(resultSet.getString("item_code"));
                endoOrderItem.setDescription(resultSet.getString("item_description"));
                endoOrderItem.setOrderedQuantity(resultSet.getDouble("ordered_quantity"));
                endoOrderItem.setSentQuantity(resultSet.getDouble("sent_quantity"));
                endoOrderItem.setPrice(resultSet.getDouble("price"));
                endoOrderItem.setAmount(resultSet.getDouble("amount"));
                endoOrderItem.setComment(resultSet.getString("comment"));
                endoOrder.addOrderItem(resultSet.getString("item_code"), endoOrderItem);

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endoOrder;
    }

    LinkedHashMap<String, EndoApostolis> getOutgoingDeltioApostolisTitles(String date) {

        //System.out.println("NOW DATE: " + nowDate);
        LinkedHashMap<String, EndoApostolis> endoInvoices = new LinkedHashMap();
        String sql = "SELECT DISTINCT  [DOCID], [DOCNUMBER],[DOCDATE], [SHLIDDESTINATION], [DESTINATION] FROM  [petworld].[dbo].[WH_ENDA_VAR]  WHERE  [DOCDATE] = '" + date + "' ORDER BY [DOCID];";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String id = resultSet.getString("DOCID");
                String number = resultSet.getString("DOCNUMBER");
                String destination = resultSet.getString("DESTINATION");
                EndoApostolis endoApostolis = new EndoApostolis();
                endoApostolis.setId(id);
                endoApostolis.setDateString(date);
                endoApostolis.setReceiver(destination);
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

}
