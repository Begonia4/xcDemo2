package com.cloudrun.microservicetemplate;


import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class DatabaseAccess {

    public static Connection connectToDatabase() throws javax.naming.NamingException, java.sql.SQLException {
        Context initContext = new InitialContext();
        System.out.println(initContext);
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        System.out.println(envContext);
        DataSource ds = (DataSource) envContext.lookup("jdbc/MySQLDB");
        System.out.println(ds);

        return ds.getConnection();
    }
}

