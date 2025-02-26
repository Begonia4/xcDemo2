package com.cloudrun.microservicetemplate;


import java.sql.Connection;

public class MySQLConnectionTest {
    public static void main(String[] args) {

        try {
            Connection connection = DatabaseAccess.connectToDatabase();
            System.out.println("Connection successful!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            System.out.println("Error occurred: " + message);
        }
    }
}

