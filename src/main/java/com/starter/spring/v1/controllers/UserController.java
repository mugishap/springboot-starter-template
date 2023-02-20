package com.starter.spring.v1.controllers;

import com.starter.spring.v1.dto.CreateUserDTO;
import com.starter.spring.v1.dto.UpdateUserDTO;
import com.starter.spring.v1.models.User;
import com.starter.spring.v1.responses.ApiResponse;
import com.starter.spring.v1.serviceImpls.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok().body(new ApiResponse(true, "Users fetched successfully", this.userService.getUsers()));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserDTO dto) {
        User user = new User(dto.getNames(), dto.getEmail(), dto.getPassword(), dto.getGender());
        User entity = this.userService.createAccount(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/create").toString());
        return ResponseEntity.created(uri).body(new ApiResponse(true, "Account created successfully", entity));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUSer(@RequestBody UpdateUserDTO dto) {
        User user = this.userService.getUserByEmail(dto.getEmail());
        return ResponseEntity.ok().body(new ApiResponse(true, "User updated successfully", this.userService.updateUser(user)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestBody String password) {
        User user = this.userService.getUserByEmail("precieuxmugisha@gmail.com");
        return ResponseEntity.ok().body(new ApiResponse(true, this.userService.deleteUser(user.getId(), password)));
    }

}
