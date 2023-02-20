package com.starter.spring.v1.serviceImpls;

import com.starter.spring.v1.config.JwtService;
import com.starter.spring.v1.models.User;
import com.starter.spring.v1.repositories.UserRepository;
import com.starter.spring.v1.responses.AuthResponse;
import com.starter.spring.v1.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email, password
                )
        );
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email (" + email + ") does not exist"));
        String jwtToken = this.jwtService.generateToken(user);
        return new AuthResponse(jwtToken, user);
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
