package TechMan;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TechManDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    public String createOrdersDatabaseTable() {

        String query = "CREATE TABLE orders("
                + "item_id INT(6) NOT NULL ) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'orders' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'orders' could not be created:" + ex;
        }

    }

    public String deleteOrdersDatabaseTable() {
        String query = "DROP TABLE orders";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'orders' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'orders' could not be deleted:" + ex;
        }
    }

    public String createNotesDatabaseTable() {
        String query = "CREATE TABLE notes("
                + "item_id INT(6) NOT NULL, "
                + "note VARCHAR(160) NOT NULL ) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'notes' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'notes' could not be created:" + ex;
        }
    }

    public String deleteNotesDatabaseTable() {
        String query = "DROP TABLE notes";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'notes' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'notes' could not be deleted:" + ex;
        }
    }

    public String createSales_1_2022_DatabaseTable() {
        String query = "CREATE TABLE sales_1_2022("
                + "code VARCHAR (50) NOT NULL, "
                + "description VARCHAR (150) NOT NULL,"
                + "measure_unit VARCHAR (5) NOT NULL, "
                + "eshop_sales int (5) NULL, "
                + "shops_supply int (5) NULL, "
                + "total_sales int (5) NULL, "
                + "coeficient int (3) NULL, "
                + "total_sales_in_pieces int (5) NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'sales_1_2022' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'sales_1_2022' could not be created:" + ex;
        }
    }

    public String deleteSales_1_2022_DatabaseTable() {
        String query = "DROP TABLE sales_1_2022";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'sales_1_2022' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'sales_1_2022' could not be deleted:" + ex;
        }
    }

    public String createSales_2_2022_DatabaseTable() {
        String query = "CREATE TABLE sales_2_2022("
                + "code VARCHAR (50) NOT NULL, "
                + "description VARCHAR (150) NOT NULL,"
                + "measure_unit VARCHAR (5) NOT NULL, "
                + "eshop_sales int (5) NULL, "
                + "shops_supply int (5) NULL, "
                + "total_sales int (5) NULL, "
                + "coeficient int (3) NULL, "
                + "total_sales_in_pieces int (5) NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'sales_2_2022' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'sales_2_2022' could not be created:" + ex;
        }
    }

    public String deleteSales_2_2022_DatabaseTable() {
        String query = "DROP TABLE sales_2_2022";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'sales_2_2022' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'sales_2_2022' could not be deleted:" + ex;
        }
    }

    String createPet4u_DB() {
        String query = "CREATE DATABASE pet4u_db";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLInitialConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "DATABASE 'pet4u_db' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "DATABASE 'pet4u_db' could not be created:" + ex;
        }
    }

    String createCamelotItemsOfOurInterestTable() {
        String query = "CREATE TABLE camelot_interest("
                + "item_code VARCHAR (100) NOT NULL, "
                + "owner VARCHAR (15) NOT NULL, "
                + "minimal_stock INT (5) NOT NULL, "
                + "order_unit VARCHAR (10) NOT NULL, "
                + "order_quantity INT (5) NOT NULL, "
                + "weight_coefficient INT (3) NOT NULL, "
                + "camelot_minimal_stock INT (5) NOT NULL, "
                + "note VARCHAR (500)  NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_interest' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_interest' could not be created:" + ex;
        }
    }

    String deleteCamelotItemsOfOurInterestTable() {
        String query = "DROP TABLE camelot_interest";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_interest' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_interest' could not be deleted:" + ex;
        }
    }

    String createCamelotItemsOfOurInterestDayRestTable() {
        String query = "CREATE TABLE camelot_day_rest("
                + "item_code VARCHAR (100) NOT NULL, "
                + "date_stamp DATE NOT NULL, "
                + "item_rest VARCHAR (30) NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_day_rest' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_day_rest' could not be created:" + ex;
        }
    }

    String deleteCamelotItemsOfOurInterestDayRestTable() {
        System.out.println("DELTEING ");
        String query = "DROP TABLE camelot_day_rest";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_day_rest' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_day_rest' could not be deleted:" + ex;
        }
    }

    String createWeightCoefficinetDatabaseTable() {
        String query = "CREATE TABLE weight_coefficient("
                + "item_code VARCHAR (100) NOT NULL, "
                + "coefficient DOUBLE (5,5) NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'weight_coefficient' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'weight_coefficient' could not be created:" + ex;
        }
    }

    String deleteWeightCoefficinetDatabaseTable() {

        String query = "DROP TABLE weight_coefficient";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'weight_coefficient' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'weight_coefficient' could not be deleted:" + ex;
        }
    }

    String createSalesDatabaseTable() {
        String query = "CREATE TABLE sales("
                + "code VARCHAR (50) NOT NULL, "
                + "description VARCHAR (150) NOT NULL,"
                + "measure_unit VARCHAR (5) NOT NULL, "
                + "eshop_sales int (5) NULL, "
                + "shops_supply int (5) NULL, "
                + "total_sales int (5) NULL, "
                + "coeficient int (3) NULL, "
                + "total_sales_in_pieces int (5) NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'sales' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'sales' could not be created:" + ex;
        }
    }

    String createInventoryDatabaseTable() {
        String query = "CREATE TABLE inventory("
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "item_code VARCHAR (100) NOT NULL, "
                + "date_stamp DATE NOT NULL, "
                + "time_stamp VARCHAR (20) NOT NULL, "
                + "system_stock VARCHAR (30) NOT NULL, "
                + "real_stock VARCHAR (30) NOT NULL, "
                + "state VARCHAR (10)  NULL, "
                + "note VARCHAR (500) NOT NULL, "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'inventory' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'inventory' could not be created:" + ex;
        }
    }

    String deleteInventoryDatabaseTable() {
        String query = "DROP TABLE inventory";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'inventory' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'inventory' could not be deleted:" + ex;
        }
    }
    //---------------------------------
    //------------------------------------

    String createDeliveryTitleDatabaseTable() {
        String query = "CREATE TABLE delivery_title("
                + "id VARCHAR(30) NOT NULL, "
                + "number VARCHAR (30) NOT NULL, "
                + "supplier VARCHAR (80) NOT NULL, "
                + "note VARCHAR (500) NOT NULL, "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'delivery_title' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'delivery_title' could not be created:" + ex;
        }
    }

    String createDeliveryDataDatabaseTable() {
        String query = "CREATE TABLE delivery_data("
                + "delivery_id  VARCHAR(30) NOT NULL , "
                + "item_code  VARCHAR (50) , "
                + "sent VARCHAR (30) NOT NULL, "
                + "delivered VARCHAR (30) NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'delivery_data' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'delivery_data' could not be created:" + ex;
        }
    }

    String deleteDeliveryTitleDatabaseTable() {
        String query = "DROP TABLE delivery_title";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'delivery_title' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'delivery_title' could not be deleted:" + ex;
        }
    }

    String deleteDeliveryDataDatabaseTable() {
        String query = "DROP TABLE delivery_data";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'delivery_data' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'delivery_data' could not be deleted:" + ex;
        }
    }

    String createBestBeforeDatabaseTable() {

        String query = "CREATE TABLE best_before("
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "altercode VARCHAR (100) NOT NULL, "
                + "best_before_date_stamp DATE NOT NULL, "
                + "alert_date_stamp DATE NOT NULL, "
                + "note VARCHAR (500) NOT NULL, "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'best_before' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'best_before' could not be created:" + ex;
        }
    }

    String deleteBestBeforeDatabaseTable() {
        String query = "DROP TABLE best_before";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'best_before' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'best_before' could not be deleted:" + ex;
        }
    }

    String createSalesDatabaseTableX() {
        String query = "CREATE TABLE sales_X("
                + "code VARCHAR (50) NOT NULL, "
                + "description VARCHAR (150) NOT NULL,"
                + "eshop_sales DOUBLE (10,4) NULL, "
                + "shops_supply DOUBLE (10,4) NULL)"
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'sales_X' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'sales_X' could not be created:" + ex;
        }
    }

    String deleteInventoryDatabaseTableX() {
        String query = "DROP TABLE sales_X";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'sales_X' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'sales_X' could not be deleted:" + ex;
        }
    }

    String getMySqlConnectionStatus() {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();

            if (connection.isValid(5)) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM sales");
                while (resultSet.next()) {
                    System.out.println("-+-+-+"+resultSet.getString("code"));            }
                return "good: "+resultSet.toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        return "???????????";
    }

}
