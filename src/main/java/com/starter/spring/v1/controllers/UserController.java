package com.starter.spring.v1.controllers;

import com.starter.spring.v1.dto.CreateUserDTO;
import com.starter.spring.v1.dto.UpdateUserDTO;
import com.starter.spring.v1.models.User;
import com.starter.spring.v1.responses.ApiResponse;
import com.starter.spring.v1.serviceImpls.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/unsecure/all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok().body(new ApiResponse(true, "Users fetched successfully", this.userService.getUsers()));
    }

    @PostMapping("/unsecure/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid CreateUserDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Validation failed", errors));
        }
        User user = new User(dto.getNames(), dto.getEmail(), dto.getPassword(), dto.getGender());
        User entity = this.userService.createAccount(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/create").toString());
        if (entity == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, "User with that email already exists"));
        return ResponseEntity.created(uri).body(new ApiResponse(true, "Account created successfully", entity));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserDTO dto) {
        User user = this.userService.getUserByEmail(dto.getEmail()); //TODO -> implement to get logged in user
        if(user == null) return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, "Email already exists"));
        return ResponseEntity.ok().body(new ApiResponse(true, "User updated successfully", this.userService.updateUser(user,dto)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestBody String password) {
        User user = this.userService.getUserByEmail("precieuxmugisha@gmail.com"); //TODO -> implement to get logged in user
        return ResponseEntity.ok().body(new ApiResponse(true, this.userService.deleteUser(user.getId(), password)));
    }

}
