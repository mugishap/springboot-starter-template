package com.starter.spring.v1.models;

import com.starter.spring.v1.enums.ROLE;
import com.starter.spring.v1.enums.STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 40)
    private String names;

    @Column(length = 50)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private STATUS accountStatus = STATUS.UNVERIFIED;

}
