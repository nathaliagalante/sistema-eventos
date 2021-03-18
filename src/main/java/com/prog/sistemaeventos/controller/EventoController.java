package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.EventoRS;
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

import antlr.debug.Event;

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
    public List<EventoRS> getEventos(){
        List<Evento> eventos = eventoRepository.findAll();
        
        List<EventoRS> evrs = new ArrayList<EventoRS>();
        for (Evento evento: eventos){
            EventoRS ev = new EventoRS();
            ev.setNome(evento.getNome());
            ev.setDescricao(evento.getDescricao());
            ev.setId(evento.getId());
            ev.setLocalEvento(evento.getLocalEvento());
            ev.setDataInicio(evento.getDataInicio());
            ev.setDataFim(evento.getDataFim());
            ev.setLocalInscricao(evento.getLocalInscricao());;
            ev.setPublicoAlvo(evento.getPublicoAlvo());
            ev.setValorInvestimento(evento.getValorInvestimento());
            evrs.add(ev);
        }

        return evrs;
    }

    @PostMapping("/gravar")
    public void gravar(@RequestBody EventoRS eventoRs){
        Evento evento = new Evento();
        evento.setDataInicio(eventoRs.getDataInicio());
        evento.setDataFim(eventoRs.getDataFim());
        evento.setDescricao(eventoRs.getDescricao());
        evento.setLocalEvento(eventoRs.getLocalEvento());
        evento.setLocalInscricao(eventoRs.getLocalInscricao());
        evento.setNome(eventoRs.getNome());
        evento.setPublicoAlvo(eventoRs.getPublicoAlvo());
        evento.setValorInvestimento(eventoRs.getValorInvestimento());

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
    public void alterar(@PathVariable("id") Long id, @RequestBody EventoRS eventoRq) throws Exception{

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

    @PostMapping("/aniversarios/{ano}")
    public void autocadastrarAniversarios(@PathVariable("ano") Integer ano){
        for(Usuario user: usuarioRepository.findAll()){
           for(Evento event: eventoRepository.findAll()){
               if(event.getNome().equals("Aniversário de " + user.getNomeCompleto()) && event.getDataInicio().getYear()==ano){
                   eventoRepository.delete(event);
               }
           }
           Evento ev = new Evento();
           ev.setNome("Aniversário de " + user.getNomeCompleto());
           ev.setDescricao("Aniversário de " + user.getNomeCompleto()); 
           ev.setDataInicio(user.getDataNascimento().withYear(ano));
           ev.setDataFim(user.getDataNascimento().withYear(ano));

           eventoRepository.save(ev);
        }
    }
}
