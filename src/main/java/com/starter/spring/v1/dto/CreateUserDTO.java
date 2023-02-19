package com.starter.spring.v1.dto;

import com.starter.spring.v1.enums.GENDER;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {

    private String names;
    private String password;
    private String email;
    private GENDER gender;

}
