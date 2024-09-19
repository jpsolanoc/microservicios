package com.jpsolanoc.transactions.repository;

import com.jpsolanoc.transactions.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}