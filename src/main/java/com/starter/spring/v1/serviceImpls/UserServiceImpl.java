package com.starter.spring.v1.serviceImpls;

import com.starter.spring.v1.dto.UpdateUserDTO;
import com.starter.spring.v1.enums.STATUS;
import com.starter.spring.v1.models.PasswordReset;
import com.starter.spring.v1.models.User;
import com.starter.spring.v1.models.Verification;
import com.starter.spring.v1.repositories.PasswordResetRepository;
import com.starter.spring.v1.repositories.UserRepository;
import com.starter.spring.v1.repositories.VerificationRepository;
import com.starter.spring.v1.services.CloudinaryService;
import com.starter.spring.v1.services.UserService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    @Override
    public User createAccount(User user) {
        try {
            PasswordReset passwordReset = new PasswordReset();
            Verification verification = new Verification();
            verification.setUser(user);
            passwordReset.setUser(user);
            user.setPasswordReset(passwordReset);
            user.setVerification(verification);

            this.passwordResetRepository.save(passwordReset);
            this.verificationRepository.save(verification);

            System.out.println(user.toString());
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    @Override
    public User updateUser(User user, UpdateUserDTO dto) {
        try {
            Map uploadedResponse = this.cloudinaryService.uploadImage(dto.getImageStr());
            String profileImage = uploadedResponse.get("secure_url").toString();
            user.setNames(dto.getNames());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setProfile(profileImage);
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteUser(UUID userId, String password) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new Error("User was not found"));
        user.setAccountStatus(STATUS.DELETED);
        this.userRepository.save(user);
        return "Account deleted successfully";
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new Error("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new Error("User not found"));
    }

    @Override
    public List<User> searchUser(String query) {
        return this.userRepository.searchUser(query);
    }


}
