package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.GrupoTrabalhoRq;
import com.prog.sistemaeventos.controller.request.UsuarioRq;
import com.prog.sistemaeventos.model.GrupoTrabalho;
import com.prog.sistemaeventos.model.Usuario;
import com.prog.sistemaeventos.repository.GrupoTrabalhoRepository;
import com.prog.sistemaeventos.repository.UsuarioRepository;

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
    
    public GrupoTrabalhoController(GrupoTrabalhoRepository grupoRepository){
        this.grupoRepository = grupoRepository;
    }

    @GetMapping("/consultar")
    public List<GrupoTrabalhoRq> getGrupos(){
        List<GrupoTrabalho> grupos = grupoRepository.findAll();

        List<GrupoTrabalhoRq> gprs = new ArrayList<GrupoTrabalhoRq>();
        for(GrupoTrabalho grupo: grupos){
            GrupoTrabalhoRq gp = new GrupoTrabalhoRq();
            gp.setId(grupo.getId());
            gp.setNome(grupo.getNome());
            gp.setDescricao(grupo.getDescricao());
            gp.setDataCriacao(grupo.getDataCriacao());
            gp.setDataRenovacao(grupo.getDataRenovacao());
            for (Usuario us : grupo.getMembros()) {
                gp.getMembros().add(us.getNomeCompleto());
            }       

            gprs.add(gp);
        }

        return gprs;
    }

    @PostMapping("/gravar")
    public void gravar(@RequestBody GrupoTrabalhoRq grupoRequest) throws Exception{
        GrupoTrabalho grupo = new GrupoTrabalho();
        grupo.setNome(grupoRequest.getNome());
        grupo.setDescricao(grupoRequest.getDescricao());
        grupo.setDataCriacao(grupoRequest.getDataCriacao());
        grupo.setDataRenovacao(grupoRequest.getDataRenovacao());
        
        grupoRepository.save(grupo);
    }

    @PostMapping("/alterar/{id}")
    public void alterar(@PathVariable("id") Long id, @RequestBody GrupoTrabalhoRq grupoRequest) throws Exception{
        
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
        
        
    }

    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable("id") Long id) throws Exception{

        var g = grupoRepository.findById(id);

        if(g.isPresent()){
            GrupoTrabalho grupo = g.get();
            grupoRepository.delete(grupo);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
    }
}


