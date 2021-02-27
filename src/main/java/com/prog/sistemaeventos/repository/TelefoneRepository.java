package com.prog.sistemaeventos.repository;

import com.prog.sistemaeventos.model.Telefone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
    
}
