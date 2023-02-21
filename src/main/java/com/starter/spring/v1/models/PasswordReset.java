package com.starter.spring.v1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "password_resets")
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "token")
    private String passwordResetToken;

    @Column(name = "expires")
    private Date expiresAt;

    @Column(name = "last_reset")
    private Date passwordResetAt;

    @OneToOne()
    private User user;

}
