package com.starter.spring.v1.repositories;

import com.starter.spring.v1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u" +
            " WHERE (lower(u.names)  LIKE ('%' || lower(:searchQuery) || '%')) " +
            " OR (lower(u.email) LIKE ('%' || lower(:searchQuery) || '%'))")
    List<User> searchUser(String searchQuery);

    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findByEmail(String email);

}
