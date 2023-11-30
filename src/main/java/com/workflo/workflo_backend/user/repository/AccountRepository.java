package com.workflo.workflo_backend.user.repository;

import com.workflo.workflo_backend.user.models.Account;
import com.workflo.workflo_backend.user.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
