package com.workflo.workflo_backend.repositories;

import com.workflo.workflo_backend.user.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
