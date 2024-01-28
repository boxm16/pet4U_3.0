/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
}
