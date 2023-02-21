package com.starter.spring.v1.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class InitiateResetPasswordDTO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Should be a valid email")
    private String email;

}
