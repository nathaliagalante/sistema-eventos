package com.prog.sistemaeventos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nomeCompleto;
    private String sexo;
    private String endereco;
    private LocalDate dataNascimento;
    private String login;
    private String senha;

    @Column(columnDefinition = "text")
    private String foto;

    @JoinTable(name = "usuario_telefone")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Telefone> telefones = new ArrayList();

    @JoinTable(name = "usuario_usuario")
    @ManyToMany
    private List<Usuario> parentes = new ArrayList();

    @ManyToOne
    private GrupoTrabalho grupoTrabalho;

    @Enumerated(EnumType.STRING)
    private NivelAcesso nivelAcesso;

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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nomeCompleto=" + nomeCompleto + "]";
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    /*public void uploadFoto(FileUploadEvent event){
        byte[] content = event.getFile().getContent();
        String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content);
        this.setFoto(resp);
    }*/


    public List<Usuario> getParentes() {
        return parentes;
    }

    public void setParentes(List<Usuario> parentes) {
        this.parentes = parentes;
    }

    public void adicionarParente(Usuario usuario){
        if(usuario != null){
            this.parentes.add(usuario);
            usuario.getParentes().add(this);
        }
        
    }
    
    public void removerParente(Usuario usuario){
        this.parentes.remove(usuario);
        usuario.getParentes().remove(this);
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public void adicionarTelefones(Telefone telefone){
        if(telefone.getDdd()!=null && telefone.getNumero()!=null)
        {
            telefone.setUsuario(this);
            telefones.add(telefone);
        }   
    }
    
    public void removerTelefones(Telefone telefone){
        telefone.setUsuario(null);
        telefones.remove(telefone);
    }

    public GrupoTrabalho getGrupoTrabalho() {
        return grupoTrabalho;
    }

    public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) {
        this.grupoTrabalho = grupoTrabalho;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
    
}
