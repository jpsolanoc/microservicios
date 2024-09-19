package com.jpsolanoc.clientcore.repository;

import com.jpsolanoc.clientcore.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}