package com.cloudrun.microservicetemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cloudrun.microservicetemplate.Milesplits.Data;


@RestController
@RequestMapping("/api")
public class FetchJason {

    
    @PostMapping(path = "/meetinfo", consumes="application/json") 
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {
        String schoolName = meet.getSchoolName(); 
        String url = meet.getUrl();

        char number1 = url.charAt(31);
        char number2 = url.charAt(32);
        char number3 = url.charAt(33);
        char number4 = url.charAt(34);
        char number5 = url.charAt(35);
        char number6 = url.charAt(36);

        String api = "https://www.milesplit.com/api/v1/meets/" + number1 + number2 + number3 + number4 + number5 + number6 + "/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";
                        
        DataSource dataSource = CloudSqlConnectionPoolFactory.createConnectionPool();

        try (Connection conn = dataSource.getConnection()) {
            ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
            String meetName = result.get(0).meetName().replaceAll(" ", "_");
            ArrayList<String> athleteArray = DatabaseOperations.athleteArray(result);
            DatabaseOperations.validateAthletes(conn, athleteArray);
            DatabaseOperations.insertMeetName(conn, result);
            DatabaseOperations.insertTrue(conn, meetName, athleteArray);
            DatabaseOperations.createMeetTable(conn, meetName);
            DatabaseOperations.loopThrough(conn, meetName, result);
            /* 
            DatabaseOperations.insertMeets(conn, url, schoolName);
            ArrayList<Milesplits.Data> apiResult = TestingMilesplit.listMilesplit(api, schoolName);
            ArrayList<String> argument = DatabaseOperations.athleteArray(apiResult);
            DatabaseOperations.validateAthletes(conn, argument);
            DatabaseOperations.insertMeetName(conn, apiResult);
            DatabaseOperations.insertAthletes(conn, apiResult, apiResult.get(0).meetName()); 
            ArrayList<sqlData> result = DatabaseOperations.sqlDataArray(conn, apiResult.get(0).meetName());
            */
            return ResponseEntity.status(HttpStatus.CREATED).body("Check the database");
        } catch (SQLException e) { 
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        } 
    }
    
    
    @GetMapping("/seasoninfo")
    public ResponseEntity<ArrayList<Athletes>> getSeasonData() {
        DataSource dataSource = CloudSqlConnectionPoolFactory.createConnectionPool();

        try (Connection conn = dataSource.getConnection()) {
            ArrayList<String> tableNames = DatabaseOperations.finalSqlDataArray(conn);
            ArrayList<String> athleteArrayTotal = DatabaseOperations.getDatabaseNames(conn);
            ArrayList<Athletes> array = DatabaseOperations.seasonInfo(conn, tableNames, athleteArrayTotal);
            return ResponseEntity.status(HttpStatus.CREATED).body(array);
        } catch (SQLException e) {
            ArrayList<Athletes> result = new ArrayList<Athletes>();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }  

    @GetMapping("/deleteinfo")
    public ResponseEntity<String> deleteSeasonInfo() {
        DataSource dataSource = CloudSqlConnectionPoolFactory.createConnectionPool();

        try (Connection conn = dataSource.getConnection()) {
            ArrayList<String> tableNames = DatabaseOperations.finalSqlDataArray(conn);
            DatabaseOperations.deleteSeason(conn, tableNames);
            return ResponseEntity.status(HttpStatus.CREATED).body("Check the database");
        } catch (SQLException e) {
            String result = e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }


    /* Current Working code as of the 11th
    
    public ResponseEntity<ArrayList<sqlData>> createMeet(@RequestBody Meet meet) {
        String schoolName = meet.getSchoolName(); 
        String url = meet.getUrl();

        char number1 = url.charAt(31);
        char number2 = url.charAt(32);
        char number3 = url.charAt(33);
        char number4 = url.charAt(34);
        char number5 = url.charAt(35);
        char number6 = url.charAt(36);

        String api = "https://www.milesplit.com/api/v1/meets/" + number1 + number2 + number3 + number4 + number5 + number6 + "/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";
                        

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            DatabaseOperations.insertMeets(conn, url, schoolName);
            ArrayList<Milesplits.Data> apiResult = TestingMilesplit.listMilesplit(api, schoolName);
            ArrayList<String> argument = DatabaseOperations.athleteArray(apiResult);
            DatabaseOperations.validateAthletes(conn, argument);
            DatabaseOperations.insertMeetName(conn, apiResult);
            DatabaseOperations.insertAthletes(conn, apiResult, apiResult.get(0).meetName().replaceAll("\\s", "")); 
            ArrayList<sqlData> result = DatabaseOperations.sqlDataArray(conn, apiResult.get(0).meetName().replaceAll("\\s", ""));
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) { 
            ArrayList<sqlData> result = new ArrayList<sqlData>();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 
    } */

    /* 
    !!!!!!!!!!!!!!!!! DO NOT TOUCH THIS SO HELP ME GOD BECAUSE THIS THING WORKS.
    ONLY COPY AND PASTE FROM HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public ResponseEntity<ArrayList<Data>> createMeet(@RequestBody Meet meet) {
        String schoolName = meet.getSchoolName(); 
        String url = meet.getUrl();

        char number1 = url.charAt(31);
        char number2 = url.charAt(32);
        char number3 = url.charAt(33);
        char number4 = url.charAt(34);
        char number5 = url.charAt(35);
        char number6 = url.charAt(36);

        String api = "https://www.milesplit.com/api/v1/meets/" + number1 + number2 + number3 + number4 + number5 + number6 + "/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";
                        

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            DatabaseOperations.insertMeets(conn, url, schoolName);
            ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) { 
            ArrayList<Milesplits.Data> result = new ArrayList<Milesplits.Data>();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 
    
    
    
    } */

    /* 
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {

        String schoolName = meet.getSchoolName(); 
        String url = meet.getUrl();
        
        

        try (Connection conn = DatabaseConnector.connectUsers()) {
            String result = DatabaseOperations.insertMeets(conn, url, schoolName);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            e.printStackTrace( printWriter );
            printWriter.flush();

            String stackTrace = writer.toString();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SQL Exception" + stackTrace +  e.getLocalizedMessage() + e.getMessage() + schoolName + url);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    //.body(new ArrayList<>()); // Explicitly returning a 500 error
        } catch (Exception e) {
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            e.printStackTrace( printWriter );
            printWriter.flush();

            String stackTrace = writer.toString();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception" + stackTrace + e.getLocalizedMessage() + e.getMessage() + schoolName + url);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    //.body(new ArrayList<>()); // Handle unexpected errors
        }
                    */
    /*
    public ResponseEntity<ArrayList<Data>> createMeet(@RequestBody Meet meet) {
        String schoolName = meet.getSchoolName(); 
        String url = meet.getUrl();

        char number1 = url.charAt(31);
        char number2 = url.charAt(32);
        char number3 = url.charAt(33);
        char number4 = url.charAt(34);
        char number5 = url.charAt(35);
        char number6 = url.charAt(36);

        String api = "https://www.milesplit.com/api/v1/meets/" + number1 + number2 + number3 + number4 + number5 + number6 + "/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";
                        

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            DatabaseOperations.insertMeets(conn, url, schoolName);
            ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) { 
            ArrayList<Milesplits.Data> result = new ArrayList<Milesplits.Data>();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 
    
    
    } */

    /*
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {

        String schoolName = meet.getSchoolName(); 
        String url = meet.getUrl();
        

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            String result = DatabaseOperations.insertMeets(conn, url, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SQL Exception" + e.getLocalizedMessage() + e.getMessage() + schoolName + url);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    //.body(new ArrayList<>()); // Explicitly returning a 500 error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception" + e.getLocalizedMessage() + e.getMessage() + schoolName + url);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    //.body(new ArrayList<>()); // Handle unexpected errors
        }
    } */
    /* 
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {

        String schoolName = meet.getMeetName(); 
        String url = meet.getUrl();

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            String result = DatabaseOperations.insertMeets(conn, url, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            String result = e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 
    */

    
    /* 
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {



        String schoolName = meet.getMeetName(); 
        String url = meet.getUrl();

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            String result = DatabaseOperations.insertMeets(conn, url, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            String result = e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 
    } */
    /* 
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {



        String meetName = meet.getMeetName(); 
        String url = meet.getUrl();

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            String result = DatabaseOperations.insertMeets(conn, url, meetName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            String result = e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    } *.
    /* 
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {

        String schoolName = meet.getMeetName(); 
        String url = meet.getUrl();

        char number1 = url.charAt(31);
        char number2 = url.charAt(32);
        char number3 = url.charAt(33);
        char number4 = url.charAt(34);
        char number5 = url.charAt(35);
        char number6 = url.charAt(36);
    
        String api = "https://www.milesplit.com/api/v1/meets/" + number1 + number2 + number3 + number4 + number5 + number6 + "/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";
    
        
        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            DatabaseOperations.insertMeets(conn, url, schoolName);
            ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            ArrayList<Milesplits.Data> result = new ArrayList<Milesplits.Data>();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            DatabaseOperations.insertMeets(conn, url, schoolName);
            ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(api);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("hello");
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    //.body(new ArrayList<>()); // Explicitly returning a 500 error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    //.body(new ArrayList<>()); // Handle unexpected errors
        }
    }  */
    
    /*
    public ResponseEntity<ArrayList<Data>> createMeet(@RequestBody Meet meet) {
            String schoolName = meet.getMeetName(); 
            String url = meet.getUrl();

            char number1 = url.charAt(31);
            char number2 = url.charAt(32);
            char number3 = url.charAt(33);
            char number4 = url.charAt(34);
            char number5 = url.charAt(35);
            char number6 = url.charAt(36);

            String api = "https://www.milesplit.com/api/v1/meets/" + number1 + number2 + number3 + number4 + number5 + number6 + "/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";
                            

            try (Connection conn = DatabaseConnector.connectTestMeet()) {
                DatabaseOperations.insertMeets(conn, url, schoolName);
                ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } catch (SQLException e) { 
                ArrayList<Milesplits.Data> result = new ArrayList<Milesplits.Data>();
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } 
        
        
        }  */
    /*
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {



        String schoolName = meet.getMeetName(); 
        String url = meet.getUrl();

        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            String result = DatabaseOperations.insertMeets(conn, url, schoolName);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            String result = e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } 
    } */

    
   
    
     
    
    


    /* 

    @PostMapping(path = "/createaccount", consumes="application/json") 
    public ResponseEntity<String> createUser(@RequestBody Users user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String schoolName = user.getSchoolName();
        String password = user.getPassword();
        
        // FIXME Figure out how to set the databseName randomly for users
        user.setDatabaseName("random");
        String databaseName = user.getDatabaseName(); 

        try (Connection conn = DatabaseConnector.connectUsers()) {
            String result = DatabaseOperations.insertUser(conn, firstName,
            lastName, email, schoolName, password, databaseName); 
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (SQLException e) {
            String result = e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }

    }
    */
    }

