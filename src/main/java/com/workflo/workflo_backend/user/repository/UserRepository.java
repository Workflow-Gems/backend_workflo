package com.workflo.workflo_backend.user.repository;

import com.workflo.workflo_backend.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email =?1")
    Optional<User> findUserByEmail(String email);
}
