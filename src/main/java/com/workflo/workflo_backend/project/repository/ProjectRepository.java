package com.workflo.workflo_backend.project.repository;

import com.workflo.workflo_backend.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("Select count(p) from Project p where p.creatorId.id=:id")
    Long countProjects(Long id);
    @Query("Select p from Project p where p.creatorId.id=:userId")
    List<Project> findByUserId(Long userId);
    List<Project> findProjectsByCategoryOrProjectStatus(String categoryOrStatus);

}
