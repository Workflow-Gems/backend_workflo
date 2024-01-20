package com.workflo.workflo_backend.user.repository;

import com.workflo.workflo_backend.user.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {


 //   @Query(value = "sELECT p.identifier, p.image, p.job_title , p.about, ps.skills FROM profile_skills ps join profile p on ps.profile_identifier =p.identifier where " +
//            "            (p.job_title IS NULL OR p.job_title = '' OR p.job_title = :jobTitle) OR " +
//            "    ps.skills in :skills", nativeQuery = true)
//
//    List<Profile> searchProfile(@Param("jobTitle") String jobTitle, @Param("skills") List<String> skills);


//            List<Profile> findProfilesByJobTitleOrSkillsIn(String jobTitleOrSkill);


}
