package com.prog.sistemaeventos.controller.request.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.model.Telefone;
import com.prog.sistemaeventos.model.Usuario;


public class UsuarioCadastroRS {
    private Long id;
    private String nomeCompleto;
    private String sexo;
    private String endereco;
    private LocalDate dataNascimento;
    private String login;
    private String senha;
    private String grupo;
    private String nivelAcesso;

    private List<Telefone> telefones = new ArrayList();

    private List<Usuario> parentes = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Usuario> getParentes() {
        return parentes;
    }

    public void setParentes(List<Usuario> parentes) {
        this.parentes = parentes;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    
}
