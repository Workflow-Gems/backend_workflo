package com.workflo.workflo_backend.user.repository;


import com.workflo.workflo_backend.user.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

//    @Query("select t from Token t where t.token=?1")
//    boolean existsByToken(String generatedToken);
    @Query("select t from Token t where t.token=?1")
    Optional<Token> findTokenByToken(String token);
}
