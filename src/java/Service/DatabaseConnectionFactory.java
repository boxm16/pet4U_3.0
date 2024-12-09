package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectionFactory {

    public Connection getPet4UMicrosoftSQLConnection() {
        Basement basement = new Basement();
        Connection connection = null;
        String dbName;
        String serverip;
        //  String serverport = "1433"; 
        String url;
        String driver;
        String databaseUserName;
        String databasePassword;

        if (basement.getApplicationHostName().equals("LAPTOP")) {
            dbName = "pet";
            serverip = "localhost";
            String serverport = "1433";
            url = "jdbc:sqlserver://" + serverip + "\\PET4U_SQL:" + serverport + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            databaseUserName = "sa";
            databasePassword = "1234";
        } else {
            dbName = "petworld";
            serverip = "192.168.0.252";
            //  String serverport = "1433"; 
            url = "jdbc:sqlserver://" + serverip + "\\SQLSTD1" + ";databaseName=" + dbName + ";encrypt=false;trustServerCertificate=true;";
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            databaseUserName = "wh";
            databasePassword = "2023wh";
        }

        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, databaseUserName, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getCamelotMicrosoftSQLConnection() {
        Basement basement = new Basement();
        Connection connection = null;
        String dbName;
        String serverip;
        //  String serverport = "1433"; 
        String url;
        String driver;
        String databaseUserName;
        String databasePassword;

        if (basement.getApplicationHostName().equals("LAPTOP")) {
            dbName = "camelot";
            serverip = "localhost";
            String serverport = "1433";
            url = "jdbc:sqlserver://" + serverip + "\\PET4U_SQL:" + serverport + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            databaseUserName = "sa";
            databasePassword = "1234";
        } else {
            dbName = "fotiou";
            serverip = "192.168.0.252";
            //  String serverport = "1433"; 
            url = "jdbc:sqlserver://" + serverip + "\\EPSILON" + ";databaseName=" + dbName + ";encrypt=false;trustServerCertificate=true;";
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            databaseUserName = "wh";
            databasePassword = "2023wh";
        }

        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, databaseUserName, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    //-------------------------MySQL---------------------------

    public Connection getMySQLInitialConnection() {
        //this connection is only for creating schema
        Basement basement = new Basement();
        Connection connection = null;
        try {

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306?useSSL=false";
            String username = "root";
            String password;
            if (basement.getApplicationHostName().equals("LAPTOP")) {
                password = "athina2004";
            } else {
                password = "per4ito";
            }

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Initial connection created at: " + LocalDateTime.now());
        return connection;
    }

    public Connection getMySQLConnection() {
        //this is regular connection 
        Basement basement = new Basement();
        Connection connection = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/pet4u_db?useSSL=false";
            String username = "root";
            String password;
            if (basement.getApplicationHostName().equals("LAPTOP")) {
                password = "athina2004";
            } else {
                password = "per4ito";
            }

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public Connection getMySQLApiBridgeConnection() {
        //this is regular connection 
        Basement basement = new Basement();
        Connection connection = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/api_bridge?useSSL=false";
            String username = "api_bridge_user_1";
            String password;
            if (basement.getApplicationHostName().equals("LAPTOP")) {
                password = "athina2004";
            } else {
                password = "api_bridge_user_1";
            }

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

}
