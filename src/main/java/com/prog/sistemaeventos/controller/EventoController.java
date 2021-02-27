package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.EventoRq;
import com.prog.sistemaeventos.model.Evento;
import com.prog.sistemaeventos.model.Usuario;
import com.prog.sistemaeventos.repository.EventoRepository;
import com.prog.sistemaeventos.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class EventoController {
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public EventoController(EventoRepository eventoRepository, UsuarioRepository usuarioRepository){
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/")
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

    @PostMapping("/")
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

        List<Usuario> usuarios = usuarioRepository.findAll();


        eventoRepository.save(evento);
    }
}
