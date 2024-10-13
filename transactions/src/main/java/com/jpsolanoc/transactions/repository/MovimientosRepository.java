package com.jpsolanoc.transactions.repository;

import com.jpsolanoc.transactions.entity.Movements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientosRepository extends JpaRepository<Movements, Long> {

    Page<Movements> findAll(Specification<Movements> specification, Pageable pageable);

    long count(Specification<Movements> specification);
}