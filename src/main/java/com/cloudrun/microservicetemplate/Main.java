package com.cloudrun.microservicetemplate;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Main {
  public static void main(String[] args) {


     
    Connection conn = DatabaseConnector.connectTestMeet();

    ArrayList<String> tableNames = DatabaseOperations.finalSqlDataArray(conn);
        ArrayList<String> athleteArray = DatabaseOperations.getDatabaseNames(conn);

         // All of the athletes
         ArrayList<Athletes> athletes = new ArrayList<Athletes>();

         ArrayList<sqlData> sqlDataArray = new ArrayList<sqlData>();
 
             // For all the meets, put all the data availiable in the one big sqlDataArray
             for (int x = 0; x < tableNames.size(); x++) {
                 String meet = tableNames.get(x);
 
                     // Get the sqlData
                     String sql = "SELECT athletes.athleteName, " + meet + ".meetName, " + meet + ".place, " + meet + ".eventName, " + meet + ".round, " + meet + ".roundName, " + meet + ".heat, " + meet + ".mark, " + meet + ".divisionName, " + meet + ".gender, " + meet + ".genderName, " + meet + ".gradYear FROM athletes INNER JOIN " + meet + " ON athletes.athleteName=" + meet + ".athleteName";
                     try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                         ResultSet rs = pstmt.executeQuery();
                         
                         while (rs.next()) {
                             String athleteName = rs.getString(1);
                             String meetName = rs.getString(2);
                             String place = rs.getString(3);
                             String eventName = rs.getString(4);
                             String round = rs.getString(5);
                             String roundName = rs.getString(6);
                             String heat = rs.getString(7);
                             String mark = rs.getString(8);
                             String divisionName = rs.getString(9);
                             String gender = rs.getString(10);
                             String genderName = rs.getString(11);
                             String gradYear = rs.getString(12);
                             sqlData data = new sqlData(athleteName, meetName, place, eventName, round, roundName, heat, mark, divisionName, gender, genderName, gradYear);
 
                             // Add the sql data to the array
                             sqlDataArray.add(data);
                         }
                         
                     } catch (SQLException e) {
                         e.getMessage();
                     }
                 } 

                 System.out.println(sqlDataArray);
 
 
             
                 // for each athlete in the athlete array
                 for (int i = 0; i < athleteArray.size(); i++) {
                     String name = athleteArray.get(i);
                     ArrayList<sqlData> athleteSqlData = new ArrayList<sqlData>();

                     // For each event in the sqlData array
                     for (int x = 0; x < sqlDataArray.size(); x++) {
                         if (sqlDataArray.get(x).name() != null && sqlDataArray.get(x).name().equals(name)) {
                             athleteSqlData.add(sqlDataArray.get(x));
                         }
                     }
                     // After all the sql data is collected for all the meets, add the athlete to the athletes arraylist
                     Athletes athlete = new Athletes(name, athleteSqlData);
                     athletes.add(athlete);
                 } 
                 System.out.println(athletes);
         }

     
        

    /* 
        // All of the athletes
        ArrayList<Athletes> athletes = new ArrayList<Athletes>();

        ArrayList<sqlData> sqlDataArray = new ArrayList<sqlData>();

            // For all the meets, put all the data availiable in the one big sqlDataArray
            for (int x = 0; x < tableNames.size(); x++) {
                String meet = tableNames.get(x);

                    // Get the sqlData
                    String sql = "SELECT athletes.athleteName, " + meet + ".place, " + meet + ".eventName, " + meet + ".round, " + meet + ".roundName, " + meet + ".heat, " + meet + ".mark, " + meet + ".divisionName, " + meet + ".gender, " + meet + ".genderName, " + meet + ".gradYear FROM athletes INNER JOIN " + meet + " ON athletes.athleteName=" + meet + ".athleteName;";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        System.out.println(sql);
                        ResultSet rs = pstmt.executeQuery();
                        System.out.println(rs);
                        
                        while (rs.next()) {
                            System.out.println("hello I've made it here");
                            String athleteName = rs.getString(1);
                            String place = rs.getString(2);
                            String eventName = rs.getString(3);
                            String round = rs.getString(4);
                            String roundName = rs.getString(5);
                            String heat = rs.getString(6);
                            String mark = rs.getString(7);
                            String divisionName = rs.getString(8);
                            String gender = rs.getString(9);
                            String genderName = rs.getString(10);
                            String gradYear = rs.getString(11);
                            System.out.println("athleteName: " + athleteName);
                            sqlData data = new sqlData(athleteName, place, eventName, round, roundName, heat, mark, divisionName, gender, genderName, gradYear);

                            // Add the sql data to the array
                            sqlDataArray.add(data);
                        }
                        
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                } 
            
                for (int i = 0; i < athleteArrayTotal.size(); i++) {
                    String name = athleteArrayTotal.get(i);
                    ArrayList<sqlData> athleteSqlData = new ArrayList<sqlData>();
                    for (int x = 0; x < sqlDataArray.size(); x++) {
                        if (sqlDataArray.get(i).name() != null && sqlDataArray.get(i).name().equals(name)) {
                            athleteSqlData.add(sqlDataArray.get(i));
                        }
                    }
                    // After all the sql data is collected for all the meets, add the athlete to the athletes arraylist
                    Athletes athlete = new Athletes(name, sqlDataArray);
                    athletes.add(athlete);
                } 
                System.out.println(sqlDataArray); */
        
    /* 
    
    String schoolName = "San Antonio Reagan";
        String url = "https://tx.milesplit.com/meets/564228-texas-distance-festival-2024/results/1006149/formatted/";

        char number1 = url.charAt(31);
        char number2 = url.charAt(32);
        char number3 = url.charAt(33);
        char number4 = url.charAt(34);
        char number5 = url.charAt(35);
        char number6 = url.charAt(36);

        String api = "https://www.milesplit.com/api/v1/meets/" + number1 + number2 + number3 + number4 + number5 + number6 + "/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";
        
        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
            String meetName = result.get(0).meetName().replaceAll(" ", "_");
            ArrayList<String> athleteArray = DatabaseOperations.athleteArray(result);
            DatabaseOperations.validateAthletes(conn, athleteArray);
            DatabaseOperations.insertMeetName(conn, result);
            DatabaseOperations.insertTrue(conn, meetName, athleteArray);
            DatabaseOperations.createMeetTable(conn, meetName);
            DatabaseOperations.loopThrough(conn, meetName, result);
            ArrayList<String> tableNames = DatabaseOperations.finalSqlDataArray(conn);
            ArrayList<String> athleteArrayTotal = DatabaseOperations.getDatabaseNames(conn);
            ArrayList<Athletes> printthis = DatabaseOperations.seasonInfo(conn, tableNames, athleteArrayTotal);
            System.out.println(printthis);
            
        } catch (SQLException e) {
            ArrayList<Milesplits.Data> result = new ArrayList<Milesplits.Data>();
            System.out.println(e.getMessage());
        }  
        
        /* 
        try (Connection conn = DatabaseConnector.connectTestMeet()) {
            ArrayList<Milesplits.Data> result = TestingMilesplit.listMilesplit(api, schoolName);
            ArrayList<String> argument = DatabaseOperations.athleteArray(result);
            DatabaseOperations.validateAthletes(conn, argument);
            DatabaseOperations.insertMeetName(conn, result);
            // This operation insertAthletes is the one that is having all the problems
            // It is dupicating event meets 
            // And if there is already another column, then no events will be added, NULL is the output all the way through
            // fixed the null problem, and now it is not duplicating any more meets
            DatabaseOperations.insertAthletes(conn, result, result.get(0).meetName().replaceAll(" ", "_")); 
            ArrayList<sqlData> printthis = DatabaseOperations.sqlDataArray(conn, result.get(0).meetName().replaceAll("\\s", "_"));
            System.out.println(printthis);
        } catch (SQLException e) {
            ArrayList<Milesplits.Data> result = new ArrayList<Milesplits.Data>();
            System.out.println(e.getMessage());
        } 
            */
     
    }

