package com.starter.spring.v1.responses;

import com.starter.spring.v1.models.User;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private User user;

}
