package com.cloudrun.microservicetemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestingMilesplit {
    public static ArrayList<Milesplits.Data> listMilesplit(String api, String schoolName) {  
        RestTemplate restTemplate = new RestTemplate();  
        try {
            ResponseEntity<Milesplits> response = restTemplate.getForEntity(URI.create(api), Milesplits.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.err.println("API call failed: " + response.getStatusCode());
                return new ArrayList<>();
            }
            Milesplits users = Objects.requireNonNull(response.getBody());
            ArrayList<Milesplits.Data> fromSchool = new ArrayList<>();
            for (Milesplits.Data data : users.data()) {
                // FIXME Try to fix all my code so if it is null it does not get in the way
                if (data.teamName() != null) {
                    if (data.teamName().contains(schoolName)) {
                        fromSchool.add(data);
                    }
                }
                
            }
            return fromSchool;
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return new ArrayList<>();
        }
    }

    /* 
    public static ArrayList<Milesplits.Data> listMilesplit(String api, String schoolName) {  
      
        RestTemplate restTemplate = new RestTemplate();  
        ResponseEntity<Milesplits> response = restTemplate.getForEntity(URI.create(api), Milesplits.class);  

        Milesplits users = Objects.requireNonNull(response.getBody());  

        ArrayList<Milesplits.Data> fromSchool = new ArrayList<Milesplits.Data>();

        for (int i = 0; i < users.data().length; i++) {
            if (users.data()[i].teamName().contains(schoolName)) {
                fromSchool.add(users.data()[i]); 
            }
        }
        return fromSchool;
    } */

}
