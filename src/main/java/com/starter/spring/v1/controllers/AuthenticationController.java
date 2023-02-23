package com.starter.spring.v1.controllers;

import com.starter.spring.v1.dto.*;
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
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid data passed", errors));
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "Login successful", this.authenticationService.login(dto.getEmail(), dto.getPassword())));
    }

    @PostMapping("/initiate-reset-password")
    public ResponseEntity<ApiResponse> initiateResetPassword(@RequestBody @Valid InitiateResetPasswordDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid data passed", errors));
        }
        String entity = this.authenticationService.initiateResetPassword(dto.getEmail());
        if (entity == null)
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Unable to send password reset email"));
        return ResponseEntity.ok().body(new ApiResponse(true, entity));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid data passed", errors));
        }
        return ResponseEntity.ok().body(new ApiResponse(true, this.authenticationService.resetPassword(dto.getResetPasswordToken(), dto.getNewPassword())));
    }

    @PostMapping("/initiate-verify-account")
    public ResponseEntity<ApiResponse> initiateVerifyAccount(@RequestBody @Valid InitiateVerifyAccountDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.internalServerError().body(new ApiResponse(false, "Invalid data passed", errors));
        }
        String verification = this.authenticationService.initiateVerifyAccount(dto.getEmail());
        if (verification == null) ResponseEntity.internalServerError().body(new ApiResponse(false, "Unable to send email"));
        return ResponseEntity.ok().body(new ApiResponse(true, verification));
    }

    @PostMapping("/verify-account")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestBody @Valid VerifyAccountDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid data passed", errors));
        }
        return ResponseEntity.ok().body(new ApiResponse(true, this.authenticationService.verifyAccount(dto.getToken())));
    }

    @GetMapping("/current-user")
    public ResponseEntity<ApiResponse> getLoggedInUser() {
        return ResponseEntity.ok().body(new ApiResponse(true, "Logged in user fetched successfully", this.authenticationService.getLoggedInUser()));
    }
}
