/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockAnalysis;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class StockAnalysisDao {

    public StockAnalysis getStock(String itemCode) {
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
                        stock.setKallitheaStock(quantity);
                        break;
                    case "ΑΧ-ΑΛΙ":
                        stock.setAlimosStock(quantity);
                        break;
                    case "ΑΧ-ΑΓΠ":
                        stock.setAghiaParaskeviStock(quantity);
                        break;
                    case "ΑΧ-ΔΑΦ":
                        stock.setDafniStock(quantity);
                        break;
                    case "ΑΧ-ΚΟΥ":
                        stock.setKoukakiStock(quantity);
                        break;
                    case "ΑΧ-ΜΙΧ":
                        stock.setMixalakopoulouStock(quantity);
                        break;
                    case "ΑΧ-ΒΑΡ":
                        stock.setVaribobiStock(quantity);
                        break;
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
                    case "ΠΡΟΣ ΚΑΤ":
                        stock.setKatastrofi(quantity);
                        break;
                    case "ΕΝΔΟΔΙΑΚΙΝΗΣΗ":
                        stock.setEndo(quantity);
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

    HashMap<String, StockAnalysis> getTotalStock() {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        HashMap<String, StockAnalysis> totalStock = new HashMap();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_ALL;");

            while (resultSet.next()) {
                StockAnalysis stock = new StockAnalysis();
                String code = resultSet.getString("ABBREVIATION").trim();
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
                        stock.setKallitheaStock(quantity);
                        break;
                    case "ΑΧ-ΑΛΙ":
                        stock.setAlimosStock(quantity);
                        break;
                    case "ΑΧ-ΑΓΠ":
                        stock.setAghiaParaskeviStock(quantity);
                        break;
                    case "ΑΧ-ΔΑΦ":
                        stock.setDafniStock(quantity);
                        break;
                    case "ΑΧ-ΚΟΥ":
                        stock.setKoukakiStock(quantity);
                        break;
                    case "ΑΧ-ΜΙΧ":
                        stock.setMixalakopoulouStock(quantity);
                        break;
                    case "ΑΧ-ΒΑΡ":
                        stock.setVaribobiStock(quantity);
                        break;
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
                    case "ΠΡΟΣ ΚΑΤ":
                        stock.setKatastrofi(quantity);
                        break;
                    case "ΕΝΔΟΔΙΑΚΙΝΗΣΗ":
                        stock.setEndo(quantity);
                        break;
                }
                totalStock.put(code, stock);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalStock;
    }

    String insertPet4uTotalStockSnapshot(HashMap<String, StockAnalysis> totalStock) {
        try {
            DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
            Connection connection = databaseConnectionFactory.getMySQLConnection();

            connection.setAutoCommit(false);
            PreparedStatement incertionPreparedStatement = connection.prepareStatement(
                    "INSERT INTO pet4u_stock_snapshot "
                    + "(item_code, date_stamp, "
                    + "xalkidon, menidi, kallithea, alimos, "
                    + "aghia_paraskevi, dafni, koukaki, mixalopoulou,"
                    + " varibobi, xalandri, nea_ionia, arghiroupoli,"
                    + "peristeri, petroupoli, paleo_faliro, "
                    + "katastrofi, endo ) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            for (Map.Entry< String, StockAnalysis> stockAnalysisEntry : totalStock.entrySet()) {
                StockAnalysis stockAnalysis = stockAnalysisEntry.getValue();
                incertionPreparedStatement.setString(1, stockAnalysis.getCode());
                incertionPreparedStatement.setString(2, stockAnalysis.toString());
                incertionPreparedStatement.setString(3, String.valueOf(stockAnalysis.getXalkidonaStock()));
                incertionPreparedStatement.setString(4, String.valueOf(stockAnalysis.getMenidiStock()));
                incertionPreparedStatement.setString(5, String.valueOf(stockAnalysis.getKallitheaStock()));
                incertionPreparedStatement.setString(6, String.valueOf(stockAnalysis.getAlimosStock()));
                incertionPreparedStatement.setString(7, String.valueOf(stockAnalysis.getAghiaParaskeviStock()));
                incertionPreparedStatement.setString(8, String.valueOf(stockAnalysis.getDafniStock()));
                incertionPreparedStatement.setString(9, String.valueOf(stockAnalysis.getKoukakiStock()));
                incertionPreparedStatement.setString(10, String.valueOf(stockAnalysis.getMixalakopoulouStock()));
                incertionPreparedStatement.setString(11, String.valueOf(stockAnalysis.getVaribobiStock()));
                incertionPreparedStatement.setString(12, String.valueOf(stockAnalysis.getXalandriStock()));
                incertionPreparedStatement.setString(13, String.valueOf(stockAnalysis.getNeaIoniaStock()));
                incertionPreparedStatement.setString(14, String.valueOf(stockAnalysis.getArghiroupoliStock()));
                incertionPreparedStatement.setString(15, String.valueOf(stockAnalysis.getPeristeriStock()));
                incertionPreparedStatement.setString(16, String.valueOf(stockAnalysis.getPetroupoliStock()));
                incertionPreparedStatement.setString(17, String.valueOf(stockAnalysis.getPalioFaliroStock()));
                incertionPreparedStatement.setString(18, String.valueOf(stockAnalysis.getKatastrofi()));
                incertionPreparedStatement.setString(19, String.valueOf(stockAnalysis.getEndo()));
                incertionPreparedStatement.addBatch();

            }
            //Executing the batch
            incertionPreparedStatement.executeBatch();

            System.out.println(" Batch Insertion: DONE");

            //Saving the changes
            connection.commit();
            //  deleteTripPeriodPreparedStatement.close();
            // deleteTripVoucherPreparedStatement.close();
            incertionPreparedStatement.close();

            connection.close();
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(StockAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }
    }
}
