package com.prog.sistemaeventos.controller.request.Grupo.Membro;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.model.Usuario;

public class MembroListarUsuariosSaidaRS {
    private Long id;
    private String nomeCompleto;

    private List<Usuario> usuarios = new ArrayList<>();

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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    

}
