package com.starter.spring.v1.services;

import com.starter.spring.v1.models.User;
import com.starter.spring.v1.responses.AuthResponse;

public interface AuthenticationService {

    public AuthResponse login(String email, String password);

    public String initiateResetPassword(String email);

    public String resetPassword(String token, String newPassword);

    public String initiateVerifyAccount(String email);

    public String verifyAccount(String token);

    public User getLoggedInUser();

}
