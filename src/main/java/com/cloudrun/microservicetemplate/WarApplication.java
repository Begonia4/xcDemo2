package com.cloudrun.microservicetemplate;


import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class WarApplication {
	
	@GetMapping("/hello")
	public String display() {
		return "Hello Apache Tomcat using the Spring Boot Application";
	}
	/* 
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@PostMapping("/meetinfo")
    public ResponseEntity<String> createMeet(@RequestBody Meet meet) {
        String result =  "{\"meetSaved\":\"" + meet.getMeetName() + "\", \"meetURL\":\"" + meet.getUrl() + "\"}";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
	*/

	public static void main(String[] args) {
		SpringApplication.run(WarApplication.class, args);
	}

}
