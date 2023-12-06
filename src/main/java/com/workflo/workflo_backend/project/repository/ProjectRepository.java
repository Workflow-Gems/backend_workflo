package com.workflo.workflo_backend.project.repository;

import com.workflo.workflo_backend.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
