/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExcelDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    public ArrayList<ExcelItem> getExcelItems() {
        ArrayList<ExcelItem> excelItems = new ArrayList();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        ExcelItem excelItem = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;

            resultSet = statement.executeQuery("select * from WH1;");
            int index = 0;
            while (resultSet.next()) {

                excelItem = new ExcelItem();
                excelItem.setCode(resultSet.getString("ABBREVIATION").trim());
                excelItem.setAltercode(resultSet.getString("ALTERNATECODE").trim());
                if (resultSet.getString("CODEDESCRIPTION") != null) {
                    excelItem.setAltercodeStatus(resultSet.getString("CODEDESCRIPTION").trim());
                } else {
                    excelItem.setAltercodeStatus("");
                }
                excelItem.setDescription(resultSet.getString("NAME").trim());
                if (resultSet.getString("EXPR1") != null) {
                    excelItem.setPosition(resultSet.getString("EXPR1").trim());
                } else {
                    excelItem.setPosition("");
                }
                if (resultSet.getString("EXPR2") != null) {
                    excelItem.setState(resultSet.getString("EXPR2").trim());
                } else {
                    excelItem.setState("");
                }
                excelItem.setQuantity(resultSet.getString("QTYBALANCE").trim());

                excelItems.add(excelItem);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExcelDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return excelItems;
    }
}
