package com.prog.sistemaeventos.controller.request.Grupo;

import java.time.LocalDate;

public class GrupoCadastroEntradaRS {
    private String nome;
    private String descricao;
    private LocalDate datarenovacao;

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

    public LocalDate getDatarenovacao() {
        return datarenovacao;
    }
    
    public void setDatarenovacao(LocalDate datarenovacao) {
        this.datarenovacao = datarenovacao;
    }

    
}
