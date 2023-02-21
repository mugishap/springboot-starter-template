package com.starter.spring.v1.dto;

import com.starter.spring.v1.enums.GENDER;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserDTO {

    @NotBlank(message = "Names cannot be null")
    private String names;

    @NotBlank(message = "Password is required")
    @Size(message = "Password should be between 4 and 16 characters", max = 16, min = 4)
    private String password;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    private GENDER gender;

}
