package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.Grupo.GrupoTrabalhoCadastroAlterarRS;
import com.prog.sistemaeventos.controller.request.Grupo.GrupoTrabalhoCadastroConsultarRS;
import com.prog.sistemaeventos.controller.request.Grupo.GrupoTrabalhoCadastroGravarRS;
import com.prog.sistemaeventos.controller.request.Grupo.Membro.MembroComboboxSaidaRS;
import com.prog.sistemaeventos.controller.request.Grupo.Membro.MembroListarUsuariosSaidaRS;
import com.prog.sistemaeventos.controller.request.Grupo.Membro.MembroSelecionarLiderEntradaRS;
import com.prog.sistemaeventos.model.GrupoTrabalho;
import com.prog.sistemaeventos.model.Usuario;
import com.prog.sistemaeventos.repository.GrupoTrabalhoRepository;
import com.prog.sistemaeventos.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grupo")
public class GrupoTrabalhoController {
    private final GrupoTrabalhoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;
    
    public GrupoTrabalhoController(GrupoTrabalhoRepository grupoRepository, UsuarioRepository usuarioRepository){
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @CrossOrigin
    @GetMapping("/consultar")
    public List<GrupoTrabalhoCadastroConsultarRS> getGrupos(){
        List<GrupoTrabalho> grupos = grupoRepository.findAll();

        List<GrupoTrabalhoCadastroConsultarRS> gprs = new ArrayList<GrupoTrabalhoCadastroConsultarRS>();
        for(GrupoTrabalho grupo: grupos){
            GrupoTrabalhoCadastroConsultarRS gp = new GrupoTrabalhoCadastroConsultarRS();
            gp.setId(grupo.getId());
            gp.setNome(grupo.getNome());
            gp.setDescricao(grupo.getDescricao());
            gp.setDataCriacao(grupo.getDataCriacao());
            gp.setDataRenovacao(grupo.getDataRenovacao());
            if(grupo.getLider()!=null){
                gp.setLider(grupo.getLider().getNomeCompleto());
            }      
            gprs.add(gp);
        }

        return gprs;
    }

    @CrossOrigin
    @PostMapping("/gravar")
    public void gravar(@RequestBody GrupoTrabalhoCadastroGravarRS grupoRequest) throws Exception{
        GrupoTrabalho grupo = new GrupoTrabalho();
        grupo.setNome(grupoRequest.getNome());
        grupo.setDescricao(grupoRequest.getDescricao());
        grupo.setDataCriacao(grupoRequest.getDataCriacao());
        grupo.setDataRenovacao(grupoRequest.getDataRenovacao());
        
        grupoRepository.save(grupo);
    }

    /*@PostMapping("/alterar/{id}")
    public void alterar(@PathVariable("id") Long id, @RequestBody GrupoTrabalhoCadastroAlterarRS grupoRequest) throws Exception{
        
        var g = grupoRepository.findById(id);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();

            if(grupoRequest.getNome()!=null){
                grupo.setNome(grupoRequest.getNome());
            }
            if(grupoRequest.getDescricao()!=null){
                grupo.setDescricao(grupoRequest.getDescricao());
            }
            if(grupoRequest.getDataCriacao()!=null){
                grupo.setDataCriacao(grupoRequest.getDataCriacao());
            }
            if(grupoRequest.getDataRenovacao()!=null){
                grupo.setDataRenovacao(grupoRequest.getDataRenovacao());
            }
            
            grupoRepository.save(grupo);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
        
        
    }*/

    @CrossOrigin
    @PostMapping("/alterar")
    public void alterar(@RequestBody GrupoTrabalhoCadastroAlterarRS grupoRequest) throws Exception{
        
        var objeto = grupoRepository.findById(grupoRequest.getId());

        if(objeto.isPresent()){
            GrupoTrabalho grupo = objeto.get();
            grupo.setDescricao(grupoRequest.getDescricao());
            grupo.setDataRenovacao(grupoRequest.getDataRenovacao());
            grupo.setNome(grupoRequest.getNome());
            grupoRepository.save(grupo);
        } else {
            throw new Exception("Não foi possível alterar a categoria");
        }
        
        
    }

    @CrossOrigin
    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable("id") Long id) throws Exception{

        var g = grupoRepository.findById(id);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            for(Usuario u : grupo.getMembros()){
                u.setGrupoTrabalho(null);
            }
            grupoRepository.delete(grupo);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
    }

    @CrossOrigin
    @PostMapping("/gerenciar/{id}/membros/adicionar/{idmembro}")
    public void adicionarMembro(@PathVariable("id") Long id, @PathVariable("idmembro") Long idmembro) throws Exception{

        var g = grupoRepository.findById(id);
        var u = usuarioRepository.findById(idmembro);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            if(u.isPresent()){
                Usuario usuario = u.get();
                if(usuario.getGrupoTrabalho()==null){
                    usuario.setGrupoTrabalho(grupo);
                    grupo.adicionarMembros(usuario);
                    grupoRepository.save(grupo);
                    usuarioRepository.save(usuario);
                }else{
                    throw new Exception("Usuário já está em um grupo");
                }
            }else{
                throw new Exception("ID do usuário não encontrado!");
            }
        }else{
            throw new Exception("ID do grupo não encontrado!");
        }

    }

    @CrossOrigin
    @PostMapping("/gerenciar/{id}/membros/remover/{idmembro}")
    public void removerMembro(@PathVariable("id") Long id, @PathVariable("idmembro") Long idmembro) throws Exception{
        var g = grupoRepository.findById(id);
        var u = usuarioRepository.findById(idmembro);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            if(u.isPresent()){
                Usuario usuario = u.get();
                grupo.removerMembros(usuario);
                usuario.setGrupoTrabalho(null);
                if(grupo.getLider()!=null){
                    if(usuario.getId().equals(grupo.getLider().getId())){
                        grupo.setLider(null);
                    }
                }
                grupoRepository.save(grupo);
                usuarioRepository.save(usuario);
            }else{
                throw new Exception("ID do usuário não encontrado!");
            }
        }
        else{
            throw new Exception("ID do grupo não encontrado!");
        }

    }

    @CrossOrigin
    @PostMapping("/gerenciar/{id}/membros/esvaziar")
    public void esvaziarGrupo(@PathVariable("id") Long id) throws Exception{
        var g = grupoRepository.findById(id);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            for(Usuario membro : grupo.getMembros()){
                membro.setGrupoTrabalho(null);
            }
            grupo.setLider(null);
            grupo.getMembros().clear();
            grupoRepository.save(grupo);
        }

    }

    @CrossOrigin
    @PostMapping("/gerenciar/{id}/membros/lider/{idmembro}")
    public void escolherLider(@PathVariable("id") Long id, @PathVariable("idmembro") Long idmembro) throws Exception{
        var g = grupoRepository.findById(id);
        var u = usuarioRepository.findById(idmembro);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            Usuario usuario = u.get();
            for(Usuario membro : grupo.getMembros()){
                if(usuario.getId().equals(membro.getId())){
                    grupo.setLider(membro);
                }
            }
            grupoRepository.save(grupo);

        }else{
            throw new Exception("ID do grupo não encontrado!");
        }
    }

    
    @CrossOrigin
    @GetMapping("/gerenciar/{id}/listar")
    public List<MembroListarUsuariosSaidaRS> getMembros(@PathVariable("id") Long id){
        var g = grupoRepository.findById(id);

        List<MembroListarUsuariosSaidaRS> membros = new ArrayList<MembroListarUsuariosSaidaRS>();

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            for(Usuario u : grupo.getMembros()){
                MembroListarUsuariosSaidaRS m = new MembroListarUsuariosSaidaRS();
                m.setId(u.getId());
                m.setNomeCompleto(u.getNomeCompleto());
                membros.add(m);
            }
        }
        return membros;
    }

    @CrossOrigin
    @GetMapping("/gerenciar/listargroupless")
    public List<MembroComboboxSaidaRS> listarSemGrupo(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<MembroComboboxSaidaRS> membros = new ArrayList<MembroComboboxSaidaRS>();
        for(Usuario u : usuarios){
            if(u.getGrupoTrabalho()==null){
                MembroComboboxSaidaRS m = new MembroComboboxSaidaRS();
                m.setId(u.getId());
                m.setNomeCompleto(u.getNomeCompleto());
                membros.add(m);
            }
        }
        return membros;

    }

    /*@CrossOrigin
    @GetMapping("/gerenciar/listarlideres")
    public List<MembroSelecionarLiderEntradaRS> listarLider(@PathVariable("id") Long id){
        var g = grupoRepository.findById(id);

        List<MembroSelecionarLiderEntradaRS> membros = new ArrayList<MembroSelecionarLiderEntradaRS>();

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            for(Usuario u : grupo.getMembros()){
                MembroSelecionarLiderEntradaRS m = new MembroSelecionarLiderEntradaRS();
                m.setId(u.getId());
                m.setNomeCompleto(u.getNomeCompleto());
                membros.add(m);
            }
        }
        return membros;
    }*/

   /* @PostMapping("/gerenciar/{id}/membros/listar")
    public List<MembroListarUsuariosSaidaRS> listarMembrosGrupo(@PathVariable("id") Long id){
        var g = grupoRepository.findById(id);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            List<MembroListarUsuariosSaidaRS> membros = new ArrayList<MembroListarUsuariosSaidaRS>();
            for(Usuario u : grupo.getMembros()){
                MembroListarUsuariosSaidaRS m = new MembroListarUsuariosSaidaRS();
                m.setId(u.getId());
                m.setNomeCompleto(u.getNomeCompleto());
                membros.add(m);
            }
            return membros;
        }
    }*/

}


