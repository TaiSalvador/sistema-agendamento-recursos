package com.sistema_agendamento_recursos.senai.servicies;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.entities.RecursoEntity;
import com.sistema_agendamento_recursos.senai.entities.ReservaEntity;
import com.sistema_agendamento_recursos.senai.entities.UsuarioEntity;
import com.sistema_agendamento_recursos.senai.repository.RecursoRepository;
import com.sistema_agendamento_recursos.senai.repository.ReservaRepository;
import com.sistema_agendamento_recursos.senai.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final RecursoRepository recursoRepository;

    public ReservaService(ReservaRepository repository, UsuarioRepository usuarioRepository, RecursoRepository recursoRepository) {

        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.recursoRepository = recursoRepository;
    }

    public List<ReservaDto> obterListaReserva() {

        List<ReservaDto> lista = new ArrayList<>();

        for (ReservaEntity entity : repository.findAll()) {

            ReservaDto dto = new ReservaDto();

            dto.setId(entity.getId());
            dto.setUsuario(entity.getUsuario().getId());
            dto.setRecurso(entity.getRecurso().getId());
            dto.setData(entity.getData());
            dto.setHoraInicial(entity.getHoraInicial());
            dto.setHoraFinal(entity.getHoraFinal());
            //dto.setDataCancelamento(entity.getDataCancelamento());
            //dto.setObservacao(entity.getObservacao());

            lista.add(dto);
        }

        return lista;
    }

    public void inserir(ReservaDto dto) {

        UsuarioEntity usuario = usuarioRepository.findById(dto.getUsuario()).orElseThrow();

        RecursoEntity recurso = recursoRepository.findById(dto.getRecurso()).orElseThrow();

        ReservaEntity entity = new ReservaEntity();

        entity.setUsuario(usuario);
        entity.setRecurso(recurso);
        entity.setData(dto.getData());
        entity.setHoraInicial(dto.getHoraInicial());
        entity.setHoraFinal(dto.getHoraFinal());
        entity.setDataCancelamento(null);
       // entity.setObservacao(dto.getObservacao());

        repository.save(entity);
    }

    public void excluir(Long id) {
        ReservaEntity reserva = repository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        if (LocalDate.now().isAfter(reserva.getData())) {
            throw new RuntimeException("Não é possível excluir uma reserva que já aconteceu.");
        }

        repository.deleteById(id);
    }

}

