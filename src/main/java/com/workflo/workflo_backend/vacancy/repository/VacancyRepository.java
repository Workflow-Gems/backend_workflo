package com.workflo.workflo_backend.vacancy.repository;

import com.workflo.workflo_backend.vacancy.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

}
