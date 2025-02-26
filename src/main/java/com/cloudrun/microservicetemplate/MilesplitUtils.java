package com.cloudrun.microservicetemplate;


import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class MilesplitUtils {  
  
    private MilesplitUtils() {}  

    // FIXME Create an API to get url 

    //FetchJason.createMeet
  
    public static final String USER_API = "https://www.milesplit.com/api/v1/meets/655694/performances?isMeetPro=0&fields=id%2CmeetName%2CteamName%2CfirstName%2ClastName%2Cgender%2CgenderName%2CdivisionName%2CgradYear%2CeventName%2CeventDistance%2Cround%2CroundName%2Cheat%2Cunits%2Cmark%2Cplace%2CstatusCode&m=GET";  
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();  
  
    public static List<Milesplits> toList(InputStream inputStream) {  
        try {  
            return OBJECT_MAPPER.readValue(inputStream, new TypeReference<>() {});  
        }  
        catch (IOException exc) {  
            throw new UncheckedIOException(exc);  
        }  
    }  
  
    public static Milesplits toObject(InputStream inputStream) {  
        try {  
            return OBJECT_MAPPER.readValue(inputStream, Milesplits.class);  
        }  
        catch (IOException exc) {  
            throw new UncheckedIOException(exc);  
        }  
    }  
  
    public static String toJson(Milesplits user) {  
        try {  
            return OBJECT_MAPPER.writeValueAsString(user);  
        }  
        catch (JsonProcessingException exc) {  
            throw new UncheckedIOException(exc);  
        }
    }
    
}