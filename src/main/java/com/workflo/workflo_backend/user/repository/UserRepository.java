package com.workflo.workflo_backend.user.repository;

import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import com.workflo.workflo_backend.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email =?1")
    Optional<User> findUserByEmail(String email);

//    @Query("select u from User u where u.id=?1")
    Optional<FoundUserResponse> findProjectedById(Long id);


    @Query("select a from User a where a.account.profile.jobTitle=:jobTitle")
    List<User> findByJobTitle(String jobTitle);
}
