package com.jpsolanoc.transactions.repository;

import com.jpsolanoc.transactions.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Account, Long> {
}