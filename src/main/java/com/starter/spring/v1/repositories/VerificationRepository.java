package com.starter.spring.v1.repositories;

import com.starter.spring.v1.models.User;
import com.starter.spring.v1.models.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.UUID;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

    @Query("SELECT v.user FROM Verification v WHERE v.verificationToken=:verificationToken AND v.expiresAt > current date ")
    public Optional<User> getUserByVerificationCode(String verificationToken);
}
