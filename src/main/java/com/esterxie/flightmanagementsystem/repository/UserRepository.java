package com.esterxie.flightmanagementsystem.repository;

import com.esterxie.flightmanagementsystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> getAllUsersFromDatabase();

    @Query(value = "SELECT * FROM users where email=?1 LIMIT 1", nativeQuery = true)
    Optional<User> findUserByEmail(String email);
}
