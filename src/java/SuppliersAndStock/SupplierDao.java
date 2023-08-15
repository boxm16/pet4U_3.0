package SuppliersAndStock;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierDao {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionFactory;

    String addSupplier(Supplier supplier) {
        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO suppliers (name, afm) VALUES (?,?)");

            insertStatement.setString(1, supplier.getName());
            insertStatement.setString(2, supplier.getAfm());

            insertStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "New Suuplier Added Successfully";
    }

}
