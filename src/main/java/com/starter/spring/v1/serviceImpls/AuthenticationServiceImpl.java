package com.starter.spring.v1.serviceImpls;

import com.starter.spring.v1.models.User;
import com.starter.spring.v1.services.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public String initiateResetPassword(String email) {
        return null;
    }

    @Override
    public User resetPassword(String token, String newPassword) {
        return null;
    }

    @Override
    public User initiateVerifyAccount(String email) {
        return null;
    }

    @Override
    public User verifyAccount(String token) {
        return null;
    }
}
