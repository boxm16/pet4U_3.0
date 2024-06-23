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

//-------------------------------
    public String createNotesDatabaseTable() {
        String query = "CREATE TABLE notes("
                + "id INT(6) NOT NULL AUTO_INCREMENT, "
                + "item_code VARCHAR(15) NOT NULL , "
                + "note VARCHAR(360) NOT NULL, "
                + "PRIMARY KEY (id)) "
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

    public String createCamelotNotesDatabaseTable() {
        String query = "CREATE TABLE camelot_notes("
                + "id INT(6) NOT NULL AUTO_INCREMENT, "
                + "item_code VARCHAR(100) NOT NULL , "
                + "note VARCHAR(360) NOT NULL, "
                + "insertion_time DATETIME NOT NULL, "
                + "deletion_time DATETIME  NULL,  "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_notes' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_notes' could not be created:" + ex;
        }
    }

    public String deleteCamelotNotesDatabaseTable() {
        String query = "DROP TABLE camelot_notes";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_notes' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_notes' could not be deleted:" + ex;
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
                + "invoice_id VARCHAR(30) NOT NULL, "
                + "id VARCHAR(30) NOT NULL, "
                + "number VARCHAR (30) NOT NULL, "
                + "supplier VARCHAR (80) NOT NULL, "
                + "note VARCHAR (500) NOT NULL, "
                + "PRIMARY KEY (invoice_id)) "
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
                    System.out.println("-+-+-+" + resultSet.getString("code"));
                }
                return "good: " + resultSet.toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        return "???????????";
    }

    //--------------------------------------
    public String createSuppliersDatabaseTable() {
        String query = "CREATE TABLE suppliers("
                + "id INT(6) NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(100) NOT NULL, "
                + "afm VARCHAR(20)  NULL, "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'suppliers' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'suppliers' could not be created:" + ex;
        }
    }

    String deleteSuppliersDatabaseTable() {
        String query = "DROP TABLE suppliers";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'suppliers' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'suppliers' could not be deleted:" + ex;
        }
    }

    String createStockManagementDatabaseTable() {
        String query = "CREATE TABLE stock_management("
                + "supplier_id INT (6) NOT NULL, "
                + "item_code VARCHAR (100) NOT NULL, "
                + "objective_sales DOUBLE (10,4) NULL, "
                + "objective_sales_expiration_date  DATE NULL, "
                + "order_horizon INT(6) NULL, "
                + "minimal_stock INT (5) NOT NULL, "
                + "order_unit VARCHAR (10) NOT NULL, "
                + "order_unit_capacity INT (5) NOT NULL, "
                + "note VARCHAR (500)  NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'stock_management' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'stock_management' could not be created:" + ex;
        }
    }

    String deleteStockManagementDatabaseTable() {
        String query = "DROP TABLE stock_management";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'stock_management' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'stock_management' could not be deleted:" + ex;
        }
    }

    public String createPet4uItemStateDatabaseTables() {
        String query = "CREATE TABLE item_state("
                + "item_code VARCHAR (100) NOT NULL, "
                + "date_stamp DATE NOT NULL, "
                + "state VARCHAR (30)  NULL, "
                + "item_stock VARCHAR (30) NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'item_state' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'item_state' could not be created:" + ex;
        }
    }

    public String deletePet4uItemStateDatabaseTables() {
        String query = "DROP TABLE item_state";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'item_state' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'item_state' could not be deleted:" + ex;
        }
    }

    String createMonthSalesDatabaseTables() {
        String query = "CREATE TABLE month_sales("
                + "code VARCHAR (100) NOT NULL, "
                + "date DATE NOT NULL, "
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
            return "Table 'month_sales' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'month_sales' could not be created:" + ex;
        }
    }

    String deleteMonthSalesDatabaseTables() {
        String query = "DROP TABLE month_sales";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'month_sales' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'month_sales' could not be deleted:" + ex;
        }
    }

    String createOffersDatabaseTables() {
        String query = "CREATE TABLE offers("
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "item_code VARCHAR (100) NOT NULL, "
                + "title VARCHAR (150) NOT NULL, "
                + "start_date DATE NOT NULL, "
                + "end_date DATE  NULL, "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'offers' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'offers' could not be created:" + ex;
        }
    }

    String deleteOffersDatabaseTables() {
        String query = "DROP TABLE offers";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'offers' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'offers' could not be deleted:" + ex;
        }
    }

    String createRoyalDatabaseTables() {
        String query = "CREATE TABLE royal_stock_management("
                + "item_code VARCHAR (100) NOT NULL, "
                + "off_line_stock INT (5) NOT NULL, "
                + "on_line_stock INT (5) NOT NULL, "
                + "maximal_stock INT (5) NOT NULL, "
                + "note VARCHAR (500)  NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'royal_stock_management' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'royal_stock_management' could not be created:" + ex;
        }
    }

    String deleteRoyalDatabaseTables() {
        String query = "DROP TABLE royal_stock_management";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'royal' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'royal_stock_management' could not be deleted:" + ex;
        }
    }

    String createPet4uStockSnapshotDatabaseTables() {
        String query = "CREATE TABLE pet4u_stock_snapshot("
                + "item_code VARCHAR (100) NOT NULL, "
                + "date_stamp DATE NOT NULL, "
                + "xalkidona VARCHAR (30)  NULL, "
                + "menidi VARCHAR (30)  NULL, "
                + "kallithea VARCHAR (30)  NULL, "
                + "alimos VARCHAR (30)  NULL, "
                + "aghia_paraskevi VARCHAR (30)  NULL, "
                + "dafni VARCHAR (30)  NULL, "
                + "koukaki VARCHAR (30)  NULL, "
                + "mixalakopoulou VARCHAR (30)  NULL, "
                + "varibobi VARCHAR (30)  NULL, "
                + "xalandri VARCHAR (30)  NULL, "
                + "nea_ionia VARCHAR (30)  NULL, "
                + "arghiroupoli VARCHAR (30)  NULL, "
                + "peristeri VARCHAR (30)  NULL, "
                + "petroupoli VARCHAR (30)  NULL, "
                + "paleo_faliro VARCHAR (30)  NULL, "
                + "katastrofi VARCHAR (30)  NULL, "
                + "endo VARCHAR (30)  NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'pet4u_stock_snapshot' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'pet4u_stock_snapshot' could not be created:" + ex;
        }
    }

    String deletePet4uStockSnapshotDatabaseTables() {
        String query = "DROP TABLE pet4u_stock_snapshot";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'pet4u_stock_snapshot' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'pet4u_stock_snapshot' could not be deleted:" + ex;
        }
    }

    String createEndoDatabaseTables() {
        String query = "CREATE TABLE endo("
                + "id INT NOT NULL, "
                + "date DATE NOT NULL, "
                + "type VARCHAR (10) NOT NULL, "
                + "sender VARCHAR (15) NOT NULL, "
                + "receiver VARCHAR (15) NOT NULL, "
                + "item_code VARCHAR (100) NOT NULL, "
                + "quantity VARCHAR (30) NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'endo' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo' could not be created:" + ex;
        }
    }

    String deleteEndoDatabaseTables() {
        String query = "DROP TABLE endo";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'endo' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo' could not be deleted:" + ex;
        }
    }

    String createEndoOrderTitleDatabaseTables() {

        String query = "CREATE TABLE endo_order_title("
                + "id VARCHAR (50) NOT NULL , "
                + "date DATE NOT NULL, "
                + "destination VARCHAR (15) NOT NULL, "
                + "note VARCHAR(360) NULL, "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);

            statement.close();
            connection.close();
            return "Tables 'endo_order_title' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_order_title' could not be created:" + ex;
        }
    }

    String createEndoOrderDataDatabaseTables() {

        String query = "CREATE TABLE endo_order_data("
                + "order_id VARCHAR (50) NOT NULL, "
                + "item_code VARCHAR (100) NOT NULL, "
                + "item_barcode VARCHAR (100) NULL, "
                + "item_description VARCHAR (100) NOT NULL, "
                + "ordered_quantity VARCHAR (30) NOT NULL, "
                + "sent_quantity VARCHAR (30) NOT NULL, "
                + "price VARCHAR (30) NOT NULL, "
                + "amount VARCHAR (30) NOT NULL, "
                + "comment VARCHAR (130) NOT NULL )"
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Tables 'endo_order_data' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_order_data' could not be created:" + ex;
        }
    }

    String deleteEndoOrderTitleDatabaseTables() {
        String query1 = "DROP TABLE endo_order_title";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);

            statement.close();
            connection.close();
            return "Table 'endo_order_title' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_order_title' could not be deleted:" + ex;
        }
    }

    String deleteEndoOrderDataDatabaseTables() {
        String query1 = "DROP TABLE endo_order_data";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);

            statement.close();
            connection.close();
            return "Table 'endo_order_data' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_order_data' could not be deleted:" + ex;
        }
    }

    String createEndoLockerTitleDatabaseTables() {

        String query = "CREATE TABLE endo_locker_title("
                + "id INT NOT NULL, "
                + "date DATE NOT NULL, "
                + "number VARCHAR(15) NOT NULL, "
                + "locked_time_stamp VARCHAR(30) NOT NULL, "
                + "destination VARCHAR (50) NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);

            statement.close();
            connection.close();
            return "Tables 'endo_locker_title' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_locker_title' could not be created:" + ex;
        }
    }

    String createEndoLockerDataDatabaseTables() {
        String query = "CREATE TABLE endo_locker_data("
                + "id INT NOT NULL, "
                + "item_code VARCHAR (100) NOT NULL, "
                + "quantity VARCHAR (30) NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Tables 'endo_locker_data' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_locker_data' could not be created:" + ex;
        }
    }

    String deleteEndoLockerTitleDatabaseTables() {
        String query1 = "DROP TABLE endo_locker_title";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);

            statement.close();
            connection.close();
            return "Table 'endo_locker_title' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_locker_title' could not be deleted:" + ex;
        }
    }

    String deleteEndoLockerDataDatabaseTables() {
        String query1 = "DROP TABLE endo_locker_data";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);

            statement.close();
            connection.close();
            return "Table 'endo_locker_data' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'endo_locker_data' could not be deleted:" + ex;
        }
    }

    String createCamelotStockPositionsDatabaseTable() {
        String query = "CREATE TABLE camelot_stock_positions("
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "item_code VARCHAR (100) NOT NULL, "
                + "position VARCHAR (30) NOT NULL,"
                + "date_stamp DATE NOT NULL, "
                + "status VARCHAR (30) ,"
                + "user VARCHAR (30) ,"
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Tables 'camelot_stock_positions' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_stock_positions' could not be created:" + ex;
        }
    }

    String deleteCamelotStockPositionsDatabaseTable() {
        String query1 = "DROP TABLE camelot_stock_positions";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);

            statement.close();
            connection.close();
            return "Table 'camelot_stock_positions' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_stock_positions' could not be deleted:" + ex;
        }
    }

    String createCamelotSalesDatabaseTable() {
        String query = "CREATE TABLE camelot_month_sales("
                + "code VARCHAR (100) NOT NULL, "
                + "date DATE NOT NULL, "
                + "sales DOUBLE (10,4) NULL)"
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_month_sales' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_month_sales' could not be created:" + ex;
        }
    }

    String deleteCamelotSalesDatabaseTable() {
        String query = "DROP TABLE camelot_month_sales";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'camelot_month_sales' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'camelot_month_sales' could not be deleted:" + ex;
        }
    }

    String createNotForEndoDatabaseTable() {
        String query = "CREATE TABLE not_for_endo("
                + "id INT(6) NOT NULL AUTO_INCREMENT, "
                + "item_code VARCHAR(15) NOT NULL , "
                + "note VARCHAR(360) NOT NULL, "
                + "PRIMARY KEY (id)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'notForEndo' created succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'notForEndo' could not be created:" + ex;
        }
    }

    String deleteNotForEndoDatabaseTable() {

        String query = "DROP TABLE not_for_endo";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return "Table 'notForEndo' deleted succesfully";
        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return "Table 'notForEndo' could not be deleted:" + ex;
        }
    }

}
