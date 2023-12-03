package com.workflo.workflo_backend.repositories;

import com.workflo.workflo_backend.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
