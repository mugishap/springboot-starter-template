package com.starter.spring.v1.serviceImpls;

import com.starter.spring.v1.enums.STATUS;
import com.starter.spring.v1.models.User;
import com.starter.spring.v1.repositories.UserRepository;
import com.starter.spring.v1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createAccount(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
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
