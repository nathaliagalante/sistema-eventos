package com.prog.sistemaeventos.controller.request.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.model.NivelAcesso;

public class UsuarioCadastroConsultarRS {
    private Long id;
    private String nomeCompleto;
    private String sexo;
    private String endereco;
    private LocalDate dataNascimento;
    private String login;
    private String senha;
    private String grupo;
    private NivelAcesso nivelAcesso;

    private List<String> telefones = new ArrayList<>();

    private List<String> parentes = new ArrayList<>();

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

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public List<String> getParentes() {
        return parentes;
    }

    public void setParentes(List<String> parentes) {
        this.parentes = parentes;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

}
