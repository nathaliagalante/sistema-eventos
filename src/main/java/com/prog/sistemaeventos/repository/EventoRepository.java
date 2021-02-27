package com.prog.sistemaeventos.repository;

import com.prog.sistemaeventos.model.Evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
}
