package com.starter.spring.v1.controllers;

import com.starter.spring.v1.dto.LoginDTO;
import com.starter.spring.v1.responses.ApiResponse;
import com.starter.spring.v1.serviceImpls.AuthenticationServiceImpl;
import com.starter.spring.v1.serviceImpls.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.ok().body(new ApiResponse(false, "Invalid data passed", errors));
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "Login successful", this.authenticationService.login(dto.getEmail(), dto.getPassword())));
    }
}
