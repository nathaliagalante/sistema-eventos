package com.prog.sistemaeventos.controller;

import java.util.ArrayList;
import java.util.List;

import com.prog.sistemaeventos.controller.request.Evento.EventoAlterarRS;
import com.prog.sistemaeventos.controller.request.Evento.EventoConsultarRS;
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

//import antlr.debug.Event;

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
    public List<EventoConsultarRS> getEventos(){
        List<Evento> eventos = eventoRepository.findAll();
        
        List<EventoConsultarRS> evrs = new ArrayList<EventoConsultarRS>();
        for (Evento evento: eventos){
            EventoConsultarRS ev = new EventoConsultarRS();
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
    public void gravar(@RequestBody EventoConsultarRS eventoRequest){
        Evento evento = new Evento();
        evento.setDataInicio(eventoRequest.getDataInicio());
        evento.setDataFim(eventoRequest.getDataFim());
        evento.setDescricao(eventoRequest.getDescricao());
        evento.setLocalEvento(eventoRequest.getLocalEvento());
        evento.setLocalInscricao(eventoRequest.getLocalInscricao());
        evento.setNome(eventoRequest.getNome());
        evento.setPublicoAlvo(eventoRequest.getPublicoAlvo());
        evento.setValorInvestimento(eventoRequest.getValorInvestimento());

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
    public void alterar(@PathVariable("id") Long id, @RequestBody EventoAlterarRS eventoRequest) throws Exception{

        var e = eventoRepository.findById(id);

        if(e.isPresent()){
            Evento evento = e.get();
            if(eventoRequest.getDataInicio()!=null){
                evento.setDataInicio(eventoRequest.getDataInicio());
            }
            if(eventoRequest.getDataFim()!=null){
                evento.setDataFim(eventoRequest.getDataFim());

            }
            if(eventoRequest.getDescricao()!=null){
                evento.setDescricao(eventoRequest.getDescricao());

            }
            if(eventoRequest.getLocalEvento()!=null){
                evento.setLocalEvento(eventoRequest.getLocalEvento());

            }
            if(eventoRequest.getLocalInscricao()!=null){
                evento.setLocalInscricao(eventoRequest.getLocalInscricao());

            }
            if(eventoRequest.getNome()!=null){
                evento.setNome(eventoRequest.getNome());

            }
            if(eventoRequest.getPublicoAlvo()!=null){
                evento.setPublicoAlvo(eventoRequest.getPublicoAlvo());

            }
            if(eventoRequest.getValorInvestimento()!=evento.getValorInvestimento()){
                evento.setValorInvestimento(eventoRequest.getValorInvestimento());

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
