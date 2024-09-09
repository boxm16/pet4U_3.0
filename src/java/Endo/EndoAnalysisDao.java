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
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class EndoAnalysisDao {

    private DatabaseConnectionFactory databaseConnectionFactory;

    public EndoAnalysisDao() {
        this.databaseConnectionFactory = new DatabaseConnectionFactory();

    }

    public LinkedHashMap<String, EndoApostolis> getEndoApostolissVaribobis() {
        LinkedHashMap<String, EndoApostolis> endoApostoliss = new LinkedHashMap();

        String query = "SELECT * FROM  [petworld].[dbo].[WH_ENDA_VAR] ;";
        //   System.out.println(query);

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            connection = this.databaseConnectionFactory.getPet4UMicrosoftSQLConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("DOCID");
                if (!endoApostoliss.containsKey(id)) {
                    EndoApostolis endoApostolis = new EndoApostolis();
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
            Logger.getLogger(EndoAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return endoApostoliss;
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

}
