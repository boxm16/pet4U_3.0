/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TESTosteron;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Search.SearchDao;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class TESTosteronDao {

    private DatabaseConnectionFactory databaseConnectionFactory;

    public TESTosteronDao() {
        this.databaseConnectionFactory = new DatabaseConnectionFactory();

    }

    public LinkedHashMap<String, Item> getAllPet4UItemsWithDeepSearch() {

        LinkedHashMap<String, Item> items = new LinkedHashMap<>();

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
                String code = resultSet.getString("ABBREVIATION").trim();
                String altercode = resultSet.getString("ALTERNATECODE").trim();
                String altercodeStatus;
                if (resultSet.getString("CODEDESCRIPTION") == null) {
                    altercodeStatus = "";
                } else {
                    altercodeStatus = resultSet.getString("CODEDESCRIPTION").trim();
                }

                String description = resultSet.getString("NAME").trim();
                String position = resultSet.getString("EXPR1");
                String quantity = resultSet.getString("QTYBALANCE");
                String state = "";
                if (resultSet.getString("EXPR2") != null) {
                    state = resultSet.getString("EXPR2");
                }

                if (items.containsKey(code)) {
                    Item item = items.get(code);

                    item.setCode(code);

                    AltercodeContainer altercodeContainer = new AltercodeContainer();
                    altercodeContainer.setAltercode(altercode);
                    altercodeContainer.setStatus(altercodeStatus);
                    item.addAltercodeContainer(altercodeContainer);

                    items.put(code, item);
                } else {
                    Item item = new Item();

                    AltercodeContainer altercodeContainer = new AltercodeContainer();
                    altercodeContainer.setAltercode(altercode);
                    altercodeContainer.setStatus(altercodeStatus);
                    item.addAltercodeContainer(altercodeContainer);

                    item.setDescription(description);
                    item.setPosition(position);
                    item.setQuantity(quantity);
                    item.setState(state);
                    items.put(code, item);
                }
            }

            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SearchDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }
}
