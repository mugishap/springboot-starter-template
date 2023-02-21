package com.starter.spring.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starter.spring.v1.enums.GENDER;
import com.starter.spring.v1.enums.ROLE;
import com.starter.spring.v1.enums.STATUS;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 40)
    private String names;

    @Column(length = 50, unique = true)
    private String email;

    @Column(name = "profileImage")
    private String profile;

    @JsonIgnore()
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.USER;

    @Enumerated(EnumType.STRING)
    private GENDER gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private STATUS accountStatus = STATUS.UNVERIFIED;

    @OneToOne()
    public Verification verification;

    @OneToOne()
    public PasswordReset passwordReset;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public User(String names, String email, String password, GENDER gender) {
        this.names = names;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
}
