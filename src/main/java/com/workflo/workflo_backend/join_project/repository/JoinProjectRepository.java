package com.workflo.workflo_backend.join_project.repository;

import com.workflo.workflo_backend.join_project.models.JoinProject;
import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JoinProjectRepository extends JpaRepository<JoinProject, Long> {

    List<JoinProject> findByVacancyIdAndStatus(Long vacancyId, JoinProjectStatus status);


    @Query("Select j from JoinProject j where j.user.id=:userId")
    List<JoinProject> findUserId(long userId);
}
