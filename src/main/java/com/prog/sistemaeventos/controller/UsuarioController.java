package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.Usuario.UsuarioCadastroRS;
import com.prog.sistemaeventos.model.Usuario;
import com.prog.sistemaeventos.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/consultar")
    public List<UsuarioCadastroRS> getUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<UsuarioCadastroRS> usrs = new ArrayList<UsuarioCadastroRS>();
        for(Usuario usuario: usuarios){
            UsuarioCadastroRS user = new UsuarioCadastroRS();
            user.setDataNascimento(usuario.getDataNascimento());
            user.setEndereco(usuario.getEndereco());
            user.setId(usuario.getId());
            user.setLogin(usuario.getLogin());
            user.setNomeCompleto(usuario.getNomeCompleto());
            user.setSenha(usuario.getSenha());
            user.setSexo(usuario.getSexo()); 
            user.setTelefones(usuario.getTelefones());
            if(usuario.getGrupoTrabalho()!=null){
                user.setGrupo(usuario.getGrupoTrabalho().getNome());
            }
            if(usuario.getNivelAcesso()!=null){
                user.setNivelAcesso(usuario.getNivelAcesso().name());
            }

            List<String> parentesList = new ArrayList<>();
            for(Usuario par: usuario.getParentes()){
                parentesList.add(par.getNomeCompleto());
            }
            user.setParentes(parentesList);
                        
            
            usrs.add(user);
        }

        return usrs;
    }

    @PostMapping("/gravar")
    public void gravar(@RequestBody UsuarioCadastroRS usuarioRequest) throws Exception{
        Usuario usuario = new Usuario();
        usuario.setDataNascimento(usuarioRequest.getDataNascimento());
        usuario.setEndereco(usuarioRequest.getEndereco());
        usuario.setLogin(usuarioRequest.getLogin());
        usuario.setNomeCompleto(usuarioRequest.getNomeCompleto());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setSexo(usuarioRequest.getSexo());

        usuarioRepository.save(usuario);
    }

    @PostMapping("/alterar/{id}")
    public void alterar(@PathVariable("id") Long id, @RequestBody UsuarioCadastroRS usuarioRequest) throws Exception{
        
        var u = usuarioRepository.findById(id);

        if(u.isPresent()){
            Usuario usuario = u.get();
            if(usuarioRequest.getDataNascimento()!=null){
                usuario.setDataNascimento(usuarioRequest.getDataNascimento());
            }
            if(usuarioRequest.getEndereco()!=null){
                usuario.setEndereco(usuarioRequest.getEndereco());
            }
            if(usuarioRequest.getLogin()!=null){
                usuario.setLogin(usuarioRequest.getLogin());
            }
            if(usuarioRequest.getNomeCompleto()!=null){
                usuario.setNomeCompleto(usuarioRequest.getNomeCompleto());
            }
            if(usuarioRequest.getSenha()!=null){
                usuario.setSenha(usuarioRequest.getSenha());
            }
            if(usuarioRequest.getSexo()!=null){
                usuario.setSexo(usuarioRequest.getSexo());
            }
            usuarioRepository.save(usuario);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
        
        
    }

    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable("id") Long id) throws Exception{

        var u = usuarioRepository.findById(id);

        if(u.isPresent()){
            Usuario usuario = u.get();
            usuarioRepository.delete(usuario);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
    }

    @PostMapping("/gerenciar/{id}/parente/adicionar/{idparente}")
    public void adicionarParente(@PathVariable("id") Long id, @PathVariable("idparente") Long idparente){
        
        var u = usuarioRepository.findById(id);
        var p = usuarioRepository.findById(idparente);

        if(u.isPresent() && p.isPresent()){
                Usuario usuario = u.get();
                Usuario parente = p.get();
                
                usuario.adicionarParente(parente);

                usuarioRepository.save(usuario);
                usuarioRepository.save(parente);
        }
    }

    @PostMapping("/gerenciar/{id}/parente/remover/{idparente}")
    public void removerParente(@PathVariable("id") Long id, @PathVariable("idparente") Long idparente){
        
        var u = usuarioRepository.findById(id);
        var p = usuarioRepository.findById(idparente);

        if(u.isPresent() && p.isPresent()){
            Usuario usuario = u.get();
            Usuario parente = p.get();
            
            if(usuario.getParentes().contains(parente)){
                usuario.removerParente(parente);

                usuarioRepository.save(usuario);
                usuarioRepository.save(parente);
            }
            
        }
    }
}
