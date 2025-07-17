package com.example.springdata.repository;

import com.example.springdata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    // custom query to count users
    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();
}
