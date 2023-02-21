package com.starter.spring.v1.repositories;

import com.starter.spring.v1.models.PasswordReset;
import com.starter.spring.v1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

    @Query("SELECT p.user FROM PasswordReset p WHERE p.passwordResetToken=:passwordResetToken AND p.expiresAt > current date ")
    public Optional<User> getUserByPasswordResetCode(String passwordResetToken);

}
