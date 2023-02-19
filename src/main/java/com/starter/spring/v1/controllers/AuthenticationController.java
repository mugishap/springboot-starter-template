package com.starter.spring.v1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @GetMapping("/test")
    public ResponseEntity<String> tester() {
        System.out.println("Accessing the tester controller");
        return ResponseEntity.ok().body("Greetings");
    }
}
