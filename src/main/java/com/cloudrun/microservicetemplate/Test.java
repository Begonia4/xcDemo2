package com.cloudrun.microservicetemplate;


import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

@RestController
@RequestMapping("/api")
public class Test {
    /* 
    @GetMapping("/getusers")
    public ResponseEntity<String> getUsers() {
        try (Connection conn = DatabaseConnector.connectUsers()) {
            String names = DatabaseOperations.getNames(conn); 
            String ages = DatabaseOperations.getAges(conn);
            String result = "{\"names\":\"" + names + "\", \"ages\":\"" + ages +"\"}";
            return ResponseEntity.status(HttpStatus.CREATED).body(result); 

        } catch (SQLException e) {
            String result = e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 
        
    } */
}