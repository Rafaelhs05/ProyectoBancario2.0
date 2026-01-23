package com.app.domain.repository;

import java.util.List;
import java.util.Optional;

import com.app.domain.model.StateAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.domain.model.Account;

public interface ReposistoryAccount extends JpaRepository<Account, Long> {

    Boolean existsByAccountNumber(String accountNumber);
    
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByStatus(StateAccount state);

}
