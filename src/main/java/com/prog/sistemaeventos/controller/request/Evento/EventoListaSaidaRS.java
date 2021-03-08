package com.prog.sistemaeventos.controller.request.Evento;

import java.time.LocalDate;

public class EventoListaSaidaRS {
    private Long id;
    private String nome;
    private LocalDate dataInicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    
}
