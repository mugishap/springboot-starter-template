package com.starter.spring.v1.serviceImpls;

import com.starter.spring.v1.models.User;
import com.starter.spring.v1.services.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User createAccount(User user) {
        return null;
    }

    @Override
    public User updateUser(User user, UUID userId) {
        return null;
    }

    @Override
    public String deleteUser(UUID userId, String password) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUserById(UUID id) {
        return null;
    }

    @Override
    public List<User> searchUser(String query) {
        return null;
    }
}
