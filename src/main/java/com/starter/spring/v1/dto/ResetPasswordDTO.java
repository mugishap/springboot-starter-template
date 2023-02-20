package com.starter.spring.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    private String resetPasswordToken;
    private String newPassword;

}
