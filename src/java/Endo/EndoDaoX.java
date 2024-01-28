/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import Service.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class EndoDaoX {

    private DatabaseConnectionFactory databaseConnectionFactory;

    public EndoDaoX() {
        this.databaseConnectionFactory = new DatabaseConnectionFactory();
    }

    LinkedHashMap<String, EndoBinder> getAllEndoBinders() {
        LinkedHashMap<String, EndoBinder> allEndoBinders = new LinkedHashMap<>();

        String query = "SELECT * FROM endo_binding;";

        try {
            Connection connection = this.databaseConnectionFactory.getMySQLConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                String bindedEndoId = resultSet.getString("endo_id");
                String bindingEndoId = resultSet.getString("binding_endo_id");

                if (allEndoBinders.containsKey(bindingEndoId)) {
                    EndoBinder endoBinder = allEndoBinders.get(bindingEndoId);

                    EndoApostolis endoApostolis = new EndoApostolis();
                    endoApostolis.setId(bindedEndoId);
                    endoBinder.addEndoApostolis(bindedEndoId, endoApostolis);

                    allEndoBinders.put(bindingEndoId, endoBinder);
                } else {
                    EndoBinder endoBinder = new EndoBinder();

                    EndoParalavis endoParalavis = new EndoParalavis();
                    endoParalavis.setId(bindingEndoId);
                    endoBinder.setEndoParalavis(endoParalavis);

                    EndoApostolis endoApostolis = new EndoApostolis();
                    endoApostolis.setId(bindedEndoId);
                    endoBinder.addEndoApostolis(bindedEndoId, endoApostolis);

                    allEndoBinders.put(bindingEndoId, endoBinder);
                }

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(EndoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allEndoBinders;
    }
}
