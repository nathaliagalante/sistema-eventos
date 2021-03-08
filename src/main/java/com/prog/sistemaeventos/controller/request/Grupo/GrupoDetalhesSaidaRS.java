package com.prog.sistemaeventos.controller.request.Grupo;

import java.time.LocalDate;
import java.util.List;

import com.prog.sistemaeventos.model.Usuario;

public class GrupoDetalhesSaidaRS {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataCriacao = LocalDate.now();
    private LocalDate dataRenovacao;

    private Usuario lider;

    private List<Usuario> membros;

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

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(LocalDate dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public Usuario getLider() {
        return lider;
    }

    public void setLider(Usuario lider) {
        this.lider = lider;
    }

    public List<Usuario> getMembros() {
        return membros;
    }

    public void setMembros(List<Usuario> membros) {
        this.membros = membros;
    }

    


    


}
