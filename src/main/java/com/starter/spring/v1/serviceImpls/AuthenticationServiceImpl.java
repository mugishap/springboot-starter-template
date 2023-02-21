package com.starter.spring.v1.serviceImpls;

import com.starter.spring.v1.config.JwtService;
import com.starter.spring.v1.enums.STATUS;
import com.starter.spring.v1.models.PasswordReset;
import com.starter.spring.v1.models.User;
import com.starter.spring.v1.models.Verification;
import com.starter.spring.v1.repositories.PasswordResetRepository;
import com.starter.spring.v1.repositories.UserRepository;
import com.starter.spring.v1.repositories.VerificationRepository;
import com.starter.spring.v1.responses.AuthResponse;
import com.starter.spring.v1.services.AuthenticationService;
import com.starter.spring.v1.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;
    private final VerificationRepository verificationRepository;
    private final MailService mailService;

    @Override
    public AuthResponse login(String email, String password) {
        System.out.println("Before authentication");
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email (" + email + ") does not exist"));
        if (user == null) return null;
        boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isMatch) return null;
        System.out.println("User:  " + user.getNames());
        String jwtToken = this.jwtService.generateToken(user);
        System.out.println("Token:  " + jwtToken);
        return new AuthResponse(jwtToken, user);
    }

    @Override
    public String initiateResetPassword(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email (" + email + ") does not exist"));
        PasswordReset passwordReset = user.getPasswordReset();
        String paswordResetToken = UUID.randomUUID().toString();
        passwordReset.setPasswordResetToken(paswordResetToken);
        Date today = new Date();
        long longTime = today.getTime() + 5 * 60 * 60 * 1000;
        Date expiresAt = new Date(longTime);
        passwordReset.setExpiresAt(expiresAt);
        this.passwordResetRepository.save(passwordReset);
        this.userRepository.save(user);
        this.mailService.sendResetPasswordMail(user.getEmail(), user.getNames(), paswordResetToken);
        return "Check you email for password reset link";
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        Optional<User> _user = this.passwordResetRepository.getUserByPasswordResetCode(token);
        if (_user.isEmpty()) return "Invalid or expired token";
        PasswordReset passwordReset = _user.get().getPasswordReset();
        passwordReset.setPasswordResetToken(null);
        passwordReset.setExpiresAt(null);
        passwordReset.setPasswordResetAt(new Date());
        User user = _user.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
        this.passwordResetRepository.save(passwordReset);
        return "Password updated successfully";
    }

    @Override
    public String initiateVerifyAccount(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email (" + email + ") does not exist"));
        Verification verification = user.getVerification();
        String verificationToken = UUID.randomUUID().toString();
        verification.setVerificationToken(verificationToken);
        long longTime = new Date().getTime() + 5 * 60 * 60 * 1000;
        Date expiresAt = new Date(longTime);
        verification.setExpiresAt(expiresAt);
        this.verificationRepository.save(verification);
        this.userRepository.save(user);
        this.mailService.sendVerificationMail(user.getEmail(), user.getNames(), verificationToken);
        return "Check you email for account verification link";
    }

    @Override
    public String verifyAccount(String token) {
        Optional<User> _user = this.verificationRepository.getUserByVerificationCode(token);
        if (_user.isEmpty()) return "Invalid or expired token";
        Verification verification = _user.get().verification;
        verification.setVerificationToken(null);
        verification.setExpiresAt(null);
        verification.setVerified(true);
        User user = _user.get();
        user.setAccountStatus(STATUS.ACTIVE);
        this.userRepository.save(user);
        this.verificationRepository.save(verification);
        return "Email verified successfully";
    }

    @Override
    public User getLoggedInUser() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Logged in user not found"));
    }
}
