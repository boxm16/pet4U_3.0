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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        LocalDate today = LocalDate.now();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from WH_ALL;");

            while (resultSet.next()) {
                StockAnalysis stock = new StockAnalysis();
                String code = resultSet.getString("ABBREVIATION").trim();
                String wh = resultSet.getString("WH");
                double quantity = resultSet.getDouble("QTYBALANCE");

                stock.setCode(code);
                stock.setDate(today);
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
                    + "xalkidona, menidi, kallithea, alimos, "
                    + "aghia_paraskevi, dafni, koukaki, mixalakopoulou, "
                    + "varibobi, xalandri, nea_ionia, arghiroupoli, "
                    + "peristeri, petroupoli, paleo_faliro, "
                    + "katastrofi, endo) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

            System.out.println("Starting INSERTION: ....");

            for (Map.Entry< String, StockAnalysis> stockAnalysisEntry : totalStock.entrySet()) {
                StockAnalysis stockAnalysis = stockAnalysisEntry.getValue();
                incertionPreparedStatement.setString(1, stockAnalysis.getCode());
                incertionPreparedStatement.setString(2, stockAnalysis.getDate().toString());
                incertionPreparedStatement.setDouble(3, stockAnalysis.getXalkidonaStock());
                incertionPreparedStatement.setDouble(4, stockAnalysis.getMenidiStock());
                incertionPreparedStatement.setDouble(5, stockAnalysis.getKallitheaStock());
                incertionPreparedStatement.setDouble(6, stockAnalysis.getAlimosStock());
                incertionPreparedStatement.setDouble(7, stockAnalysis.getAghiaParaskeviStock());
                incertionPreparedStatement.setDouble(8, stockAnalysis.getDafniStock());
                incertionPreparedStatement.setDouble(9, stockAnalysis.getKoukakiStock());
                incertionPreparedStatement.setDouble(10, stockAnalysis.getMixalakopoulouStock());
                incertionPreparedStatement.setDouble(11, stockAnalysis.getVaribobiStock());
                incertionPreparedStatement.setDouble(12, stockAnalysis.getXalandriStock());
                incertionPreparedStatement.setDouble(13, stockAnalysis.getNeaIoniaStock());
                incertionPreparedStatement.setDouble(14, stockAnalysis.getArghiroupoliStock());
                incertionPreparedStatement.setDouble(15, stockAnalysis.getPeristeriStock());
                incertionPreparedStatement.setDouble(16, stockAnalysis.getPetroupoliStock());
                incertionPreparedStatement.setDouble(17, stockAnalysis.getPalioFaliroStock());
                incertionPreparedStatement.setDouble(18, stockAnalysis.getKatastrofi());
                incertionPreparedStatement.setDouble(19, stockAnalysis.getEndo());
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
            return "SUCCESS";
        } catch (SQLException ex) {
            Logger.getLogger(StockAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }
    }

    public LinkedHashMap<String, StockAnalysis> getTotalStockAnalysis(String itemCode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getMySQLConnection();
        LinkedHashMap<String, StockAnalysis> totalStock = new LinkedHashMap();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from pet4u_stock_snapshot WHERE item_code='" + itemCode + "';");

            while (resultSet.next()) {

                String date = resultSet.getString("date_stamp");
                StockAnalysis stockAnalysis = totalStock.get(date);
                if (stockAnalysis == null) {
                    stockAnalysis = new StockAnalysis();

                    stockAnalysis.setXalkidonaStock(resultSet.getDouble("xalkidona"));
                    stockAnalysis.setMenidiStock(resultSet.getDouble("menidi"));
                    stockAnalysis.setKallitheaStock(resultSet.getDouble("kallithea"));
                    stockAnalysis.setAlimosStock(resultSet.getDouble("alimos"));
                    stockAnalysis.setAghiaParaskeviStock(resultSet.getDouble("aghia_paraskevi"));
                    stockAnalysis.setDafniStock(resultSet.getDouble("dafni"));
                    stockAnalysis.setMixalakopoulouStock(resultSet.getDouble("mixalakopoulou"));
                    stockAnalysis.setArghiroupoliStock(resultSet.getDouble("arghiroupoli"));
                    stockAnalysis.setPeristeriStock(resultSet.getDouble("peristeri"));
                    stockAnalysis.setPetroupoliStock(resultSet.getDouble("petroupoli"));
                    stockAnalysis.setPalioFaliroStock(resultSet.getDouble("paleo_faliro"));
                    stockAnalysis.setKatastrofi(resultSet.getDouble("katastrofi"));
                    stockAnalysis.setEndo(resultSet.getDouble("endo"));
                    totalStock.put(date, stockAnalysis);
                } else {
                    stockAnalysis.setXalkidonaStock(resultSet.getDouble("xalkidona"));
                    stockAnalysis.setMenidiStock(resultSet.getDouble("menidi"));
                    stockAnalysis.setKallitheaStock(resultSet.getDouble("kallithea"));
                    stockAnalysis.setAlimosStock(resultSet.getDouble("alimos"));
                    stockAnalysis.setAghiaParaskeviStock(resultSet.getDouble("aghia_paraskevi"));
                    stockAnalysis.setDafniStock(resultSet.getDouble("dafni"));
                    stockAnalysis.setMixalakopoulouStock(resultSet.getDouble("mixalakopoulou"));
                    stockAnalysis.setArghiroupoliStock(resultSet.getDouble("arghiroupoli"));
                    stockAnalysis.setPeristeriStock(resultSet.getDouble("peristeri"));
                    stockAnalysis.setPetroupoliStock(resultSet.getDouble("petroupoli"));
                    stockAnalysis.setPalioFaliroStock(resultSet.getDouble("paleo_faliro"));
                    stockAnalysis.setKatastrofi(resultSet.getDouble("katastrofi"));
                    stockAnalysis.setEndo(resultSet.getDouble("endo"));
                    totalStock.put(date, stockAnalysis);
                }

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockAnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalStock;
    }
}
