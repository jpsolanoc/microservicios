package com.jpsolanoc.transactions.repository;

import com.jpsolanoc.transactions.entity.Movimientos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    Page<Movimientos> findAll(Specification<Movimientos> specification, Pageable pageable);

    long count(Specification<Movimientos> specification);
}