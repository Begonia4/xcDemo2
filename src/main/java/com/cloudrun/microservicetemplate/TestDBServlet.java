package com.cloudrun.microservicetemplate;


import java.sql.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testdb")
public class TestDBServlet {

    @GetMapping
    public String testDatabaseConnection() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                return "Connected to the database!";
            } else {
                return "Failed to connect to the database";
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}