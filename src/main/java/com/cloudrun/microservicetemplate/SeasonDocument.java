package com.cloudrun.microservicetemplate;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeasonDocument {
    
    @PostMapping("../html/meet-document.html")
    public String createDocs(@RequestBody String y) {
        return y;
    }
}
