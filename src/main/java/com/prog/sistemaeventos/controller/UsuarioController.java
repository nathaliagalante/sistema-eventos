package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.Usuario.TelefoneAdicionarRS;
import com.prog.sistemaeventos.controller.request.Usuario.UsuarioCadastroAlterarRS;
import com.prog.sistemaeventos.controller.request.Usuario.UsuarioCadastroConsultarRS;
import com.prog.sistemaeventos.controller.request.Usuario.UsuarioCadastroGravarRS;
import com.prog.sistemaeventos.controller.request.LoginEntradaRS;
import com.prog.sistemaeventos.model.Telefone;
import com.prog.sistemaeventos.model.Usuario;
import com.prog.sistemaeventos.repository.TelefoneRepository;
import com.prog.sistemaeventos.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
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
    private final TelefoneRepository telefoneRepository;
    private Boolean conectado = false;
    private Usuario usuarioConectado = null;
    
    public UsuarioController(UsuarioRepository usuarioRepository, TelefoneRepository telefoneRepository){
        this.usuarioRepository = usuarioRepository;
        this.telefoneRepository = telefoneRepository;
    }

    @CrossOrigin
    @GetMapping("/consultar")
    public List<UsuarioCadastroConsultarRS> getUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<UsuarioCadastroConsultarRS> usrs = new ArrayList<UsuarioCadastroConsultarRS>();
        for(Usuario usuario: usuarios){
            UsuarioCadastroConsultarRS user = new UsuarioCadastroConsultarRS();
            user.setDataNascimento(usuario.getDataNascimento());
            user.setEndereco(usuario.getEndereco());
            user.setId(usuario.getId());
            user.setLogin(usuario.getLogin());
            user.setNomeCompleto(usuario.getNomeCompleto());
            user.setSenha(usuario.getSenha());
            user.setSexo(usuario.getSexo()); 
            if(usuario.getGrupoTrabalho()!=null){
                user.setGrupo(usuario.getGrupoTrabalho().getNome());
            }
            if(usuario.getNivelAcesso()!=null){
                user.setNivelAcesso(usuario.getNivelAcesso());
            }

            List<String> parentesList = new ArrayList<>();
            for(Usuario par: usuario.getParentes()){
                parentesList.add(par.getNomeCompleto());
            }
            user.setParentes(parentesList);

            List<String> telefonesList = new ArrayList<>();
            for(Telefone tel: usuario.getTelefones()){
                telefonesList.add(tel.toString());
            }
            user.setTelefones(telefonesList);
                        
            
            usrs.add(user);
        }

        return usrs;
    }

    @CrossOrigin
    @PostMapping("/login")
    public void logar(@RequestBody LoginEntradaRS loginRequest){
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario u: usuarios){
            if(u.getLogin().equals(loginRequest.getLogin()) && u.getSenha().equals(loginRequest.getSenha())){
                usuarioConectado = u;
                conectado = true;
            } 
        }

    }

    @CrossOrigin
    @GetMapping("/login/visualizar")
    public LoginEntradaRS visualizarLogin(){
        LoginEntradaRS loginRS = new LoginEntradaRS();
        loginRS.setLogin(usuarioConectado.getLogin());
        loginRS.setSenha(usuarioConectado.getSenha());

        return loginRS;
    }

    @CrossOrigin
    @PostMapping("/gravar")
    public void gravar(@RequestBody UsuarioCadastroGravarRS usuarioRequest) throws Exception{
        Usuario usuario = new Usuario();

        usuario.setDataNascimento(usuarioRequest.getDataNascimento());
        usuario.setEndereco(usuarioRequest.getEndereco());
        usuario.setLogin(usuarioRequest.getLogin());
        usuario.setNomeCompleto(usuarioRequest.getNomeCompleto());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setSexo(usuarioRequest.getSexo());
        usuario.setNivelAcesso(usuarioRequest.getNivelAcesso());

        usuarioRepository.save(usuario);
    }

    @CrossOrigin
    @PostMapping("/alterar/{id}")
    public void alterar(@PathVariable("id") Long id, @RequestBody UsuarioCadastroAlterarRS usuarioRequest) throws Exception{
        
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
            if(usuarioRequest.getNivelAcesso()!=null){
                usuario.setNivelAcesso(usuarioRequest.getNivelAcesso());
            }
            usuarioRepository.save(usuario);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
        
        
    }

    @CrossOrigin
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

    @CrossOrigin
    @PostMapping("/gerenciar/{id}/parente/adicionar/{idparente}")
    public void adicionarParente(@PathVariable("id") Long id, @PathVariable("idparente") Long idparente) throws Exception{
        
        var u = usuarioRepository.findById(id);
        var p = usuarioRepository.findById(idparente);

        if(u.isPresent() && p.isPresent()){
                Usuario usuario = u.get();
                Usuario parente = p.get();
                
                usuario.adicionarParente(parente);

                usuarioRepository.save(usuario);
                usuarioRepository.save(parente);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
    }

    @CrossOrigin
    @PostMapping("/gerenciar/{id}/parente/remover/{idparente}")
    public void removerParente(@PathVariable("id") Long id, @PathVariable("idparente") Long idparente) throws Exception{
        
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
        else{
            throw new Exception("ID não encontrado!");
        }
    }

    /******************* TELEFONE *********************/
    @CrossOrigin
    @PostMapping("/gerenciar/{id}/telefone/adicionar")
    public void adicionarTelefone(@PathVariable("id") Long id, @RequestBody TelefoneAdicionarRS telefoneRequest) throws Exception{
        var u = usuarioRepository.findById(id);

        if(u.isPresent()){
            Usuario usuario = u.get();
            Telefone tel = new Telefone();
            tel.setDdd(telefoneRequest.getDdd());
            tel.setNumero(telefoneRequest.getNumero());

            usuario.adicionarTelefones(tel);

            usuarioRepository.save(usuario);
        }else{
            throw new Exception("ID não encontrado!");
        }
    }

    @CrossOrigin
    @PostMapping("/gerenciar/{id}/telefone/remover/{idtelefone}")
    public void removerTelefone(@PathVariable("id") Long id, @PathVariable("idtelefone") Long idtelefone) throws Exception{
        var u = usuarioRepository.findById(id);

        var tel = telefoneRepository.findById(idtelefone);

        if(u.isPresent() && tel.isPresent()){
            Telefone telefone = tel.get();
            Usuario usuario = u.get();

            usuario.removerTelefones(telefone);

            usuarioRepository.save(usuario);
        }else{
            throw new Exception("ID não encontrado!");
        }
    }

    
}
