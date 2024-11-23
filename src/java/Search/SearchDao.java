package Search;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class SearchDao {
    
    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;
    
    public Item getItemByAltercode(String altercode) {
        DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();
        Connection connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
        Item item = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
//maybe thre is some join, or other clause for things like this, but i dnt know yet, no time for search
            resultSet = statement.executeQuery("select ABBREVIATION from WH1 WHERE ALTERNATECODE='" + altercode + "';");
            String code = "";
            while (resultSet.next()) {
                code = resultSet.getString("ABBREVIATION");
            }
            resultSet = statement.executeQuery("select * from WH1 WHERE ABBREVIATION='" + code + "';");
            int index = 0;
            while (resultSet.next()) {
                if (index == 0) {
                    item = new Item();
                    item.setCode(resultSet.getString("ABBREVIATION").trim());
                    item.setDescription(resultSet.getString("NAME").trim());
                    String position = "";
                    if (resultSet.getString("EXPR1") != null) {
                        position = resultSet.getString("EXPR1").trim();
                    }
                    item.setPosition(position);
                    item.setQuantity(resultSet.getString("QTYBALANCE"));
                    String state = "";
                    if (resultSet.getString("EXPR2") != null) {
                        state = resultSet.getString("EXPR2").trim();
                    }
                    item.setState(state);
                    index++;
                }
                AltercodeContainer altercodeContainer = new AltercodeContainer();
                altercodeContainer.setAltercode(resultSet.getString("ALTERNATECODE").trim());
                if (resultSet.getString("CODEDESCRIPTION") == null) {
                    altercodeContainer.setStatus("");
                } else {
                    altercodeContainer.setStatus(resultSet.getString("CODEDESCRIPTION").trim());
                }
                if (resultSet.getString("MAIN_BARCODE") == null) {
                    //do nothing
                } else {
                    if (resultSet.getString("MAIN_BARCODE").equals(resultSet.getString("ALTERNATECODE"))) {
                        altercodeContainer.setMainBarcode(true);
                      //  item.setMainBarcode(resultSet.getString("ALTERNATECODE"));// HERE ALTERNATECODE AND MAIN_CODE IS THE SAME
                        item.setMainBarcode(resultSet.getString("ALTERNATECODE"));//
                    } else {
                        altercodeContainer.setMainBarcode(false);
                    }
                }
                
                if (resultSet.getShort("IS_PACK_BC") == 0) {
                    //do nothing
                } else {
                    altercodeContainer.setPackageBarcode(true);
                    altercodeContainer.setItemsInPackage(resultSet.getDouble("PACK_QTY"));
                }
                if (item != null) {//It should never be, i mean, if there is an altercode, there is an item. But, just in any case
                    item.addAltercodeContainer(altercodeContainer);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SearchDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }
    
    public LinkedHashMap<String, Item> getItemsByAltercodeMask(String altercodeMask) {
        
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        
        try {
            connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            
            statement = connection.createStatement();

//maybe thre is some join, or other clause for things like this, but i dnt know yet, no time for search
            String sql = "SELECT ABBREVIATION FROM WH1 WHERE ALTERNATECODE LIKE '" + altercodeMask + "'";
            
            resultSet = statement.executeQuery(sql);
            ArrayList<String> itemCodes = new ArrayList<>();
            String code;
            while (resultSet.next()) {
                code = resultSet.getString("ABBREVIATION").trim();
                if (itemCodes.contains(code)) {
                } else {
                    itemCodes.add(code);
                }
            }
            
            if (itemCodes.isEmpty()) {
                statement.close();
                connection.close();
                return items;
            }
            StringBuilder query = new StringBuilder();
            StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT * FROM WH1  WHERE ABBREVIATION IN");
            StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(itemCodes);
            
            query = queryBuilderInitialPart.append(queryBuilderDateStampPart);
            
            System.out.println(query);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.toString());
            
            while (resultSet.next()) {
                code = resultSet.getString("ABBREVIATION").trim();
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
                    
                    AltercodeContainer altercodeContainer = new AltercodeContainer();
                    altercodeContainer.setAltercode(altercode);
                    altercodeContainer.setStatus(altercodeStatus);
                    item.addAltercodeContainer(altercodeContainer);
                    
                    items.put(code, item);
                } else {
                    Item item = new Item();
                    
                    item.setCode(code);
                    
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
    
    public LinkedHashMap<String, Item> getItemsByDescriptionMask(String descriptionMask) {
        
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        String sql = "SELECT * FROM WH1 WHERE NAME LIKE '" + descriptionMask + "'";
        System.out.println(sql);
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        
        try {
            connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
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
    
    public LinkedHashMap<String, Item> deepSearchForAltercodeMask(String altercodeMask) {
        
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        
        try {
            connection = databaseConnectionFactory.getPet4UMicrosoftSQLConnection();
            
            statement = connection.createStatement();

//maybe thre is some join, or other clause for things like this, but i dnt know yet, no time for search
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
                    + " AND ALTERNATECODE LIKE '" + altercodeMask + "'"
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
