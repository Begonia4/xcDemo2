package com.cloudrun.microservicetemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static String database = "testmeet"; // table is meets (url, name)
    private static final String BEGINNERURL = "jdbc:mysql://localhost:3306/";
    private static String url = BEGINNERURL + database;
    private static final String USER = "root";
    private static final String PASSWORD = "A8ue$)Jn0";
    public static Connection connectTestMeet() {
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database ", e);
        }
    }

    private static String database2 = "users"; /*  table is accounts CREATE TABLE accounts(
        -> accountID int NOT NULL AUTO_INCREMENT,
        -> firstName varchar(255),
        -> lastName varchar(255),
        -> email varchar(255),
        -> schoolName varchar(255),
        -> password varchar(255),
        -> databaseName varchar(255),
        -> PRIMARY KEY (accountID)
        -> );
         */
    private static String url2 = BEGINNERURL + database2;
    public static Connection connectUsers() {
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database ", e);
        }
    }
}