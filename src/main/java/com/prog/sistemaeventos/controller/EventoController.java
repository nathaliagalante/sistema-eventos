package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.EventoRq;
import com.prog.sistemaeventos.model.Evento;
import com.prog.sistemaeventos.model.Usuario;
import com.prog.sistemaeventos.repository.EventoRepository;
import com.prog.sistemaeventos.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evento")
public class EventoController {
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public EventoController(EventoRepository eventoRepository, UsuarioRepository usuarioRepository){
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/consultar")
    public List<EventoRq> getEventos(){
        List<Evento> eventos = eventoRepository.findAll();
        
        List<EventoRq> evrq = new ArrayList<EventoRq>();
        for (Evento evento: eventos){
            EventoRq ev = new EventoRq();
            ev.setNome(evento.getNome());
            ev.setDescricao(evento.getDescricao());
            ev.setId(evento.getId());
            ev.setLocalEvento(evento.getLocalEvento());
            ev.setDataInicio(evento.getDataInicio());
            ev.setDataFim(evento.getDataFim());
            ev.setLocalInscricao(evento.getLocalInscricao());;
            ev.setPublicoAlvo(evento.getPublicoAlvo());
            ev.setValorInvestimento(evento.getValorInvestimento());
            evrq.add(ev);
        }

        return evrq;
    }

    @PostMapping("/gravar")
    public void gravar(@RequestBody EventoRq eventoRq){
        Evento evento = new Evento();
        evento.setDataInicio(eventoRq.getDataInicio());
        evento.setDataFim(eventoRq.getDataFim());
        evento.setDescricao(eventoRq.getDescricao());
        evento.setLocalEvento(eventoRq.getLocalEvento());
        evento.setLocalInscricao(eventoRq.getLocalInscricao());
        evento.setNome(eventoRq.getNome());
        evento.setPublicoAlvo(eventoRq.getPublicoAlvo());
        evento.setValorInvestimento(eventoRq.getValorInvestimento());

        //List<Usuario> usuarios = usuarioRepository.findAll();


        eventoRepository.save(evento);
    }

    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable("id") Long id) throws Exception{

        var e = eventoRepository.findById(id);

        if(e.isPresent()){
            Evento evento = e.get();
            eventoRepository.delete(evento);
        }
        else{
            throw new Exception("ID não encontrado!");
        }
    }

    @PostMapping("/alterar/{id}")
    public void alterar(@PathVariable("id") Long id, @RequestBody EventoRq eventoRq) throws Exception{

        var e = eventoRepository.findById(id);

        if(e.isPresent()){
            Evento evento = e.get();
            if(eventoRq.getDataInicio()!=null){
                evento.setDataInicio(eventoRq.getDataInicio());
            }
            if(eventoRq.getDataFim()!=null){
                evento.setDataFim(eventoRq.getDataFim());

            }
            if(eventoRq.getDescricao()!=null){
                evento.setDescricao(eventoRq.getDescricao());

            }
            if(eventoRq.getLocalEvento()!=null){
                evento.setLocalEvento(eventoRq.getLocalEvento());

            }
            if(eventoRq.getLocalInscricao()!=null){
                evento.setLocalInscricao(eventoRq.getLocalInscricao());

            }
            if(eventoRq.getNome()!=null){
                evento.setNome(eventoRq.getNome());

            }
            if(eventoRq.getPublicoAlvo()!=null){
                evento.setPublicoAlvo(eventoRq.getPublicoAlvo());

            }
            if(eventoRq.getValorInvestimento()!=evento.getValorInvestimento()){
                evento.setValorInvestimento(eventoRq.getValorInvestimento());

            }
            

            eventoRepository.save(evento);
            
        }
        else{
            throw new Exception("ID não encontrado!");
        }
    }
}
