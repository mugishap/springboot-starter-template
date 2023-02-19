package com.starter.spring.v1.services;

import com.starter.spring.v1.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public User createAccount(User user);

    public User updateUser(User user, UUID userId);

    public String deleteUser(UUID userId, String password);

    public List<User> getUsers();

    public User getUserById(UUID id);

    public List<User> searchUser(String query);

}
