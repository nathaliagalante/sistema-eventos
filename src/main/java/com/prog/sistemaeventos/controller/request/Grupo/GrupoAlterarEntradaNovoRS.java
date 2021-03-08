package com.prog.sistemaeventos.controller.request.Grupo;

import java.time.LocalDate;

public class GrupoAlterarEntradaNovoRS {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataRenovacao;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataRenovacao() {
        return dataRenovacao;
    }
    
    public void setDataRenovacao(LocalDate dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }
    
}
