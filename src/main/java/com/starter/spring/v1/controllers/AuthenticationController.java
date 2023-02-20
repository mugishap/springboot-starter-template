package com.starter.spring.v1.controllers;

import com.starter.spring.v1.dto.LoginDTO;
import com.starter.spring.v1.responses.ApiResponse;
import com.starter.spring.v1.serviceImpls.AuthenticationServiceImpl;
import com.starter.spring.v1.serviceImpls.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final UserServiceImpl userService;

    @GetMapping("/test")
    public ResponseEntity<String> tester() {
        System.out.println("Accessing the tester controller");
        return ResponseEntity.ok().body("Greetings");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Login successful", this.authenticationService.login(dto.getEmail(), dto.getPassword())));
    }
}
