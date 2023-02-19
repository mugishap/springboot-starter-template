package com.starter.spring.v1.services;

import com.starter.spring.v1.models.User;

public interface AuthenticationService {

    public User login(String email, String password);

    public String initiateResetPassword(String email);

    public User resetPassword(String token, String newPassword);

    public User initiateVerifyAccount(String email);

    public User verifyAccount(String token);

}
