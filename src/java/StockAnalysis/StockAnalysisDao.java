/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockAnalysis;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class StockAnalysisDao {

  public  StockAnalysis getStock(String itemCode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        StockAnalysis stock = new StockAnalysis();
        stock.setCode(itemCode);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_ALL WHERE ABBREVIATION='" + itemCode + "';");

            while (resultSet.next()) {
                // String code = resultSet.getString("ABBREVIATION").trim();
                String wh = resultSet.getString("WH");
                double quantity = resultSet.getDouble("QTYBALANCE");

                switch (wh) {
                    case "ΑΧ-ΧΑΛ":
                        stock.setXalkidonaStock(quantity);
                        break;
                    case "ΑΧ-ΜΕΝ":
                        stock.setMenidiStock(quantity);
                        break;
                    case "ΑΧ-ΚΑΛ":
                        System.out.println("Wednesday");
                        break;
                    case "ΑΧ-ΑΛΙ":
                        stock.setAlimosStock(quantity);
                        break;
                    case "ΑΧ-ΑΓΠ":
                        stock.setAghiaParaskeviStock(quantity);
                        break;
                    case "ΑΧ-ΔΑΦ":
                        System.out.println("Saturday");
                        break;
                    case "ΑΧ-ΚΟΥ":
                        stock.setDafniStock(quantity);
                        break;
                    case "ΑΧ-ΜΙΧ":
                        stock.setMixalakopoulouStock(quantity);
                        break;
                    case "ΑΧ-ΒΑΡ":
                        stock.setVaribobiStock(quantity);
                    case "ΑΧ-ΧΛΡ":
                        stock.setXalandriStock(quantity);
                        break;
                    case "ΑΧ-ΙΩΝ":
                        stock.setNeaIoniaStock(quantity);
                        break;
                    case "ΑΧ-ΑΡΓ":
                        stock.setArghiroupoliStock(quantity);
                        break;
                    case "ΑΧ-ΠΕΡ":
                        stock.setPeristeriStock(quantity);
                        break;
                    case "ΑΧ-ΠΤΡ":
                        stock.setPetroupoliStock(quantity);
                        break;
                    case "ΑΧ-ΠΦΑ":
                        stock.setPalioFaliroStock(quantity);
                        break;
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stock;
    }
}
