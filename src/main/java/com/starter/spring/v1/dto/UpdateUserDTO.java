package com.starter.spring.v1.dto;

import com.starter.spring.v1.enums.GENDER;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {

    private String names;
    private String email;
    private String imageStr;
    private GENDER gender;
}
