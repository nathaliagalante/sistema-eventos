package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.UsuarioRq;
import com.prog.sistemaeventos.model.Usuario;
import com.prog.sistemaeventos.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/")
    public List<UsuarioRq> getUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<UsuarioRq> usrs = new ArrayList<UsuarioRq>();
        for(Usuario usuario: usuarios){
            UsuarioRq user = new UsuarioRq();
            user.setDataNascimento(usuario.getDataNascimento());
            user.setEndereco(usuario.getEndereco());
            user.setId(usuario.getId());
            user.setLogin(usuario.getLogin());
            user.setNomeCompleto(usuario.getNomeCompleto());
            user.setSenha(usuario.getSenha());
            user.setSexo(usuario.getSexo());
            usrs.add(user);
        }

        return usrs;
    }

    @PostMapping("/")
    public void gravar(@RequestBody UsuarioRq usuarioRequest) throws Exception{
        Usuario usuario = new Usuario();
        usuario.setDataNascimento(usuarioRequest.getDataNascimento());
        usuario.setEndereco(usuarioRequest.getEndereco());
        usuario.setLogin(usuarioRequest.getLogin());
        usuario.setNomeCompleto(usuarioRequest.getNomeCompleto());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setSexo(usuarioRequest.getSexo());

        usuarioRepository.save(usuario);
    }
}
