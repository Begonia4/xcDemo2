package com.cloudrun.microservicetemplate;


import java.sql.*;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

public class DatabaseOperations {

    // *** meets table operations
    /* Eventually the meets will be the athletes table will not store the 
     * name and url of the meet. It will look like below, with the meets
     * organized as follows (place, event, mark, division, gender, grade)
     * +---------------+---------------------------------+--------------------------------+
     * | athletes      | meet 1                          | meet 2                         | 
     * +---------------+---------------------------------+--------------------------------+
     * | Amy Jones     | {1, 1600m, 7, V, female, 12}    | {1, 1600m, 7, V, female, 12}   | 
     * +---------------+---------------------------------+--------------------------------+
     * | Arthur Jones  | {1, 1600m, 5, V, male, 10}      | {1, 1600m, 5, V, male, 10}     |
     * +---------------+---------------------------------+--------------------------------+
     */

    // Delete info from a season
    public static void deleteSeason(Connection conn, ArrayList<String> tableNames) {
        for (int i = 0; i < tableNames.size(); i++) {
            String sql = "DROP TABLE " + tableNames.get(i);
            String sql2 = "ALTER TABLE athletes DROP COLUMN " + tableNames.get(i);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.getMessage();
            }   
            try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.getMessage();
            } 
        }
        String sql3 = "DELETE FROM athletes";
        try (PreparedStatement pstmt = conn.prepareStatement(sql3)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    // Check what is the value of a table
    public static String checkValue(Connection conn, String name, String meetName) {
        String sql = "SELECT " + meetName + " FROM athletes WHERE athleteName = \"" + name + "\"";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            return rs.getString(meetName);  
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    // Getting info from the tablee
    public static ArrayList<Athletes> seasonInfo(Connection conn, ArrayList<String> tableNames, ArrayList<String> athleteArray) {
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
        
                         //System.out.println(sqlDataArray);
         
         
                     
                         // for each athlete in the athlete array
                         for (int i = 0; i < athleteArray.size(); i++) {
                             String name = athleteArray.get(i);
                             ArrayList<sqlData> athleteSqlData = new ArrayList<sqlData>();
                             for (int x = 0; x < sqlDataArray.size(); x++) {
                                 if (sqlDataArray.get(x).name() != null && sqlDataArray.get(x).name().equals(name)) {
                                     athleteSqlData.add(sqlDataArray.get(x));
                                 }
                             }
                             // After all the sql data is collected for all the meets, add the athlete to the athletes arraylist
                             Athletes athlete = new Athletes(name, athleteSqlData);
                             athletes.add(athlete);
                         } 
                         return athletes;
                         //System.out.println(athletes);
                 }
        
        /* 

        // All of the athletes
        ArrayList<Athletes> athletes = new ArrayList<Athletes>();

        ArrayList<sqlData> sqlDataArray = new ArrayList<sqlData>();

            // For all the meets, put all the data availiable in the one big sqlDataArray
            for (int x = 0; x < tableNames.size(); x++) {
                String meet = tableNames.get(x);

                    // Get the sqlData
                    String sql = "SELECT athletes.athleteName, " + meet + ".place, " + meet + ".eventName, " + meet + ".round, " + meet + ".roundName, " + meet + ".heat, " + meet + ".mark, " + meet + ".divisionName, " + meet + ".gender, " + meet + ".genderName, " + meet + ".gradYear FROM athletes INNER JOIN " + meet + " ON athletes.athleteName=" + meet + ".athleteName";
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


            
                // for each athlete in the athlete array
                for (int i = 0; i < athleteArray.size(); i++) {
                    String name = athleteArray.get(i);
                    ArrayList<sqlData> athleteSqlData = new ArrayList<sqlData>();
                    for (int x = 0; x < sqlDataArray.size(); x++) {
                        if (sqlDataArray.get(i).name() != null && sqlDataArray.get(i).name().equals(name)) {
                            athleteSqlData.add(sqlDataArray.get(i));
                        }
                    }
                    // After all the sql data is collected for all the meets, add the athlete to the athletes arraylist
                    Athletes athlete = new Athletes(name, athleteSqlData);
                    athletes.add(athlete);
                } 
                return athletes;
        }   */
        


    // Creating a meet table
    public static String createMeetTable(Connection conn, String meetName) {
        String sql = "CREATE TABLE " + meetName + " (athleteName varchar(255), meetName varchar(255), place varchar(255), eventName varchar(255), round varchar(255), roundName varchar(255), heat varchar(255), mark varchar(255), divisionName varchar(255), gender varchar(255), genderName varchar(255), gradYear varchar(255))";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            return "success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    
    //Looping through arraylist
    public static void loopThrough(Connection conn, String meetName, ArrayList<Milesplits.Data> athleteArray) {
        for (int i = 0; i < athleteArray.size(); i++) {
            String name = athleteArray.get(i).firstName() + " " + athleteArray.get(i).lastName();
            String meetName2 = athleteArray.get(i).meetName();
            String place = athleteArray.get(i).place();
            String eventName = athleteArray.get(i).eventName();
            String round = athleteArray.get(i).round();
            String roundName = athleteArray.get(i).roundName();
            String heat  = athleteArray.get(i).heat();
            String mark = athleteArray.get(i).mark();
            String divisionName = athleteArray.get(i).divisionName();
            String gender = athleteArray.get(i).gender();
            String genderName = athleteArray.get(i).genderName();
            String gradYear = athleteArray.get(i).gradYear();
            insertMeetTable(conn, meetName, name, meetName2, place, eventName, round, 
            roundName, heat, mark, divisionName, gender, genderName, gradYear);
        }
    }

    // Adding athlete info 
    public static String insertMeetTable(Connection conn, String meetName, String athleteName, String meetName2, String place, String eventName, String round, 
    String roundName, String heat, String mark, String divisionName, String gender, String genderName, String gradYear) {
        String sql = "INSERT INTO " + meetName + " (athleteName, meetName, place, eventName, round, roundName, heat, mark, divisionName, gender, genderName, gradYear) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, athleteName);
            pstmt.setString(2, meetName2);
            pstmt.setString(3, place);
            pstmt.setString(4, eventName);
            pstmt.setString(5, round);
            pstmt.setString(6, roundName);
            pstmt.setString(7, heat);
            pstmt.setString(8, mark);
            pstmt.setString(9, divisionName);
            pstmt.setString(10, gender);
            pstmt.setString(11, genderName);
            pstmt.setString(12, gradYear);
            pstmt.executeUpdate();
            return "sucess";
        } catch (SQLException e) {
            return e.getMessage();
        }
        
    }
    
    // Adding the meet column
    public static String insertMeetName(Connection conn, ArrayList<Milesplits.Data> athleteArray) {
        String sql = "ALTER TABLE athletes ADD " + athleteArray.get(0).meetName().replaceAll(" ", "_") +  " varchar(255)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            return "Success";
        } catch (SQLException e) {
            return sql;
        }
    }

    // Inserting new athletes do not use directly
    public static String insertNewAthletes(Connection conn, String name) {
        String sql = "INSERT INTO athletes (athleteName) VALUES (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return e.getMessage(); 
        }
        return "hello";
    }

    // Checking for athletes do not use directly 
    public static String check(ArrayList<String> names, String athleteName) {
        for (int x = 0; x < names.size(); x++) {
            if (athleteName.equals(names.get(x))) {
                return "gotcha";
            }
        } 
        return "uh-oh";
    }

    //Turning it into an array of strings
    public static ArrayList<String> athleteArray(ArrayList<Milesplits.Data> array) {
        ArrayList<String> apiNames = new ArrayList<String>();    
        for (int i = 0; i < array.size(); i++) {
            String athleteName = array.get(i).firstName() + " " +  array.get(i).lastName();
            apiNames.add(athleteName);
        }
        System.out.println("size: " + apiNames.size());
        return apiNames;
    }

    //Getting athlete names
    public static ArrayList<String> getDatabaseNames(Connection conn) {
        String sql = "SELECT athleteName FROM athletes"; 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> databaseNames = new ArrayList<String>();

            while (rs.next()) {
                String name = rs.getString("athleteName"); 
                databaseNames.add(name);
            }
            return databaseNames;
        }  catch (SQLException e) {
            ArrayList<String> error = new ArrayList<String>();
            error.add(e.getMessage());
            return error;
        }
    }

    // Replace Null with true
    public static void insertTrue(Connection conn, String meetName, ArrayList<String> apiNames) {
        for (int i = 0; i < apiNames.size(); i++) {
            String name = apiNames.get(i);
            String sql = "UPDATE athletes SET " + meetName + " = \"true\" WHERE athleteName = \"" + name +"\"";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.getMessage();
            }
        }
    }

    // Validating athletes 
    public static String validateAthletes(Connection conn, ArrayList<String> apiNames) {
        
            // for each athlete in athleteArray, we check if they match any of the names from our sql query
            // if they're not there, we insert their name 
            for (int i = 0; i < apiNames.size(); i++) {
                // Getting an array of the current athlete names for every turn in the athleteArray
                ArrayList<String> databaseNames = getDatabaseNames(conn); 
                String athleteName = apiNames.get(i); 
                String value = check(databaseNames, athleteName);
                if (value.equals("uh-oh")) {
                    insertNewAthletes(conn, athleteName);
                }
            } 
    
        return "success";
    }
    
    // SQL Inserting athlete data
    public static String insertSQLAthletes(Connection conn, String sql) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
                return "Success!!!";
            } catch (SQLException e) {
                return e.getMessage();
            } 
    }

    // Check if there is already data in the meet for that athlete and update it with that information included. 
    public static ArrayList<String> checkMeetData(Connection conn, String sql, String meet) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery(); 
            ArrayList<String> events = new ArrayList<String>();
            while (rs.next()) {
                String result = rs.getString(meet);
                events.add(result);
            }
            System.out.println(events);
            return events;
        } catch (SQLException e) {
            ArrayList<String> message = new ArrayList<String>();
            message.add(e.getMessage());
            return message;
        }
    }

    // Inserting athlete data
    public static String insertAthletes(Connection conn, ArrayList<Milesplits.Data> athleteArray, String meet) {
        for (int i = 0; i < athleteArray.size(); i++) {
            String name = athleteArray.get(i).firstName() + " " + athleteArray.get(i).lastName();
            String sql1 = "SELECT " + meet + " FROM athletes WHERE athleteName = \"" + name +"\"";
            ArrayList<String> events = checkMeetData(conn, sql1, meet);
            String array = name + "," + athleteArray.get(i).place() + "," + athleteArray.get(i).eventName() + "," + athleteArray.get(i).round() + "," + athleteArray.get(i).roundName() + "," + athleteArray.get(i).heat() + "," + athleteArray.get(i).mark() + "," + athleteArray.get(i).divisionName() + "," + athleteArray.get(i).gender() + "," + athleteArray.get(i).genderName() + "," + athleteArray.get(i).gradYear(); 
            if (events.get(0) == null) {
                String sql2 = "UPDATE athletes SET " + meet + " = \"" + array + "\" WHERE athleteName = \"" + name + "\""; 
                insertSQLAthletes(conn, sql2);
            } else {
                String currentEntries = "";
                for (String event : events) {
                    currentEntries = currentEntries + event;
                }
                String sql3 = "UPDATE athletes SET " + meet + " = \"" + currentEntries + "," + array + "\" WHERE athleteName = \"" + name + "\"";
                insertSQLAthletes(conn, sql3);
            } 
            
        }
        return "success"; 
    }

    /* 

    // Putting athlete data in an array of sqlData for one meet
    public static ArrayList<sqlData> sqlDataArray(Connection conn, String meet) {
        // Array to hold all the values of sqlData from the database
        ArrayList<sqlData> dataFromDatabase = new ArrayList<sqlData>();
        // Get all the athletes
        ArrayList<String> athleteNames = getDatabaseNames(conn);
        // Loop this for each athlete in the database
        for (int i = 0; i < athleteNames.size(); i++) {
            String name = athleteNames.get(i);
            ArrayList<String> meetInfo = getAthleteData(conn, meet, name);
            for (int y = 0; y < meetInfo.size(); y++) {
                if (meetInfo.get(y) != null) {
                    String[] words = meetInfo.get(y).split(",");
                    for (int z = 0; z < (words.length / 11); z++) {
                        // event0 (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10) z=0 yz
                        // event1 (11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21) z = 1 yz
                        // event2 (22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32) z = 2 yz ()  -10
                        // event3 (33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43)
                        // event4 (44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54)
                        // event5 (55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65) 
                        int nextvalue = z+1; 
                        String Y = String.valueOf(nextvalue);
                        String Z = String.valueOf(z);
                        String W = Y + Z; 
                        int w = Integer.valueOf(W); 
                        System.out.println("w: " + w);
                        sqlData currentEvent = new sqlData(words[w-10], words[w-9], words[w-8], words[w-7], words[w-6], words[w-5], words[w-4], words[w-3], words[w-2], words[w-1], words[w]);
                        dataFromDatabase.add(currentEvent);
                    }
                }
                
            }
        }
        return dataFromDatabase; 
    } */

    // Getting data from table for multiple meets
    public static ArrayList<String> finalSqlDataArray(Connection conn) {
        String sql = "DESCRIBE athletes";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ArrayList<String> tableList = new ArrayList<String>();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String fields = rs.getString("field");
                tableList.add(fields);
            }
            tableList.remove(0);
            return tableList;
        } catch (SQLException e) {
            ArrayList<String> result = new ArrayList<String>();
            return result;
        }

    }

    /* 

    // Getting athlete data SQL
    public static ArrayList<String> getAthleteData(Connection conn, String meetName, String athleteName) {
        String sql = "SELECT " + meetName + " FROM athletes WHERE athleteName = \"" + athleteName + "\""; 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ArrayList<String> sqlList = new ArrayList<String>();
            ResultSet rs =  pstmt.executeQuery();

            while (rs.next()) {
                String result = rs.getString(meetName); 
                sqlList.add(result);
            }

            sqlList.remove(0);

            ArrayList<sqlData> finalArray = new ArrayList<sqlData>();

            for (int i = 0; i < sqlList.size(); i++) {
                ArrayList<sqlData> returnArray = sqlDataArray(conn, sqlList.get(i));
                finalArray.addAll(returnArray);
            }

            return sqlList;
        } catch (SQLException e) {
            ArrayList<String> message = new ArrayList<String>();
            message.add(e.getMessage());
            return message;
        }

    } */

    // Inserting meet info data
    public static String insertMeets(Connection conn, String url, String schoolName) {
        String sql = "INSERT INTO meets (url, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, url);
            pstmt.setString(2, schoolName);
            pstmt.executeUpdate();
            return "Success!!!";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    // *** accounts table operations

    // Creating a user
    public static String insertUser(Connection conn, String firstName, 
        String lastName, String email, String schoolName, String password,
        String databaseName) {
            String sql = "INSERT INTO accounts (firstName, lastName, email, schoolName, password, databaseName) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, schoolName);
                pstmt.setString(5, password);
                pstmt.setString(6, databaseName);
                pstmt.executeUpdate();
                return "Success!!!";
            } catch (SQLException e) {
                return e.getMessage();
            }
        }

    // Logging in a user - I'll need to let the meets operations
    // know the database name they will have to used while logged in
    // Ask dad about this? ~^ Do more research to understand user dynamics

    // *** users table operations

    public static void insertRecord(Connection conn, String name, int age) {
        String sql = "INSERT INTO users (name, age) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static String getNames(Connection conn) {
        String sql = "SELECT name FROM users";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery(); 

            ArrayList<String> names = new ArrayList<String>();

            while (rs.next()) {
                String name = rs.getString("name");
                names.add(name);
            }

            StringBuilder bld = new StringBuilder();
            for (int i = 0; i < names.size(); ++i) {
                bld.append(names.get(i));
                bld.append(" ");
            }
            return bld.toString();

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static String getAges(Connection conn) {
        String sql = "SELECT age FROM users";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Integer> ages = new ArrayList<Integer>();

            while (rs.next()) {
                Integer age = rs.getInt("age");
                ages.add(age);
            }
            StringBuilder bld = new StringBuilder();
            for (int i = 0; i <ages.size(); ++i) {
                bld.append(ages.get(i));
                bld.append(" ");

            }
            return bld.toString();

        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    }
