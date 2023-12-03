package com.workflo.workflo_backend.repositories;

import com.workflo.workflo_backend.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
