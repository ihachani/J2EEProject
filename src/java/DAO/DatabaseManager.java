/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import exceptions.CreatingStatementException;
import exceptions.LoadingDriverException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author faiez
 */
public class DatabaseManager {
    private static Connection connection;
    private static Statement statement;
    
    public static Statement getStatement () throws CreatingStatementException {
        try {
            if (statement == null) {
                statement = getConnection().createStatement();
            }
            return statement;
        } catch (Exception e) {
            throw new CreatingStatementException();
        }
    }
    
    public static Connection getConnection () throws LoadingDriverException {
        try {
            if (connection == null) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection =  DriverManager.getConnection("jdbc:mysql://localhost/bibliotheque?user=root&password=");
            }
            return connection;
        } catch (Exception e) {
            throw new LoadingDriverException();
        }
    }
    
}
