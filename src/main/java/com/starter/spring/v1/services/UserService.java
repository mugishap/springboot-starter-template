package com.starter.spring.v1.services;

import com.starter.spring.v1.dto.UpdateUserDTO;
import com.starter.spring.v1.models.User;
import java.util.List;


public interface UserService {

    public User createAccount(User user);

    public User updateUser(User user, UpdateUserDTO dto);

    public String deleteUser(Long userId, String password);

    public List<User> getUsers();

    public User getUserById(Long id);

    public User getUserByEmail(String email);

    public List<User> searchUser(String query);

}
