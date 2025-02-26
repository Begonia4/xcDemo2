package com.cloudrun.microservicetemplate;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DatabaseConnection {
    public static Connection getConnection() throws javax.naming.NamingException, java.sql.SQLException {
        Connection connection = null;
      
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:/jdbc/MySQLDB");
        connection = ds.getConnection();

        return connection;
    }
}