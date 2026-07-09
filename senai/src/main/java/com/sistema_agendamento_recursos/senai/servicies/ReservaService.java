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

    public ReservaService(ReservaRepository repository,
                          UsuarioRepository usuarioRepository,
                          RecursoRepository recursoRepository) {

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
            dto.setDataCancelamento(entity.getDataCancelamento());
            dto.setObservacao(entity.getObservacao());

            lista.add(dto);
        }

        return lista;
    }

    public ReservaDto obterReservaPorId(Long id) {

        ReservaEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        ReservaDto dto = new ReservaDto();

        dto.setId(entity.getId());
        dto.setUsuario(entity.getUsuario().getId());
        dto.setRecurso(entity.getRecurso().getId());
        dto.setData(entity.getData());
        dto.setHoraInicial(entity.getHoraInicial());
        dto.setHoraFinal(entity.getHoraFinal());
        dto.setDataCancelamento(entity.getDataCancelamento());
        dto.setObservacao(entity.getObservacao());

        return dto;
    }

    public void inserir(ReservaDto dto) {

        if (dto == null) {
            throw new RuntimeException("Dados da reserva não informados.");
        }

        UsuarioEntity usuario = usuarioRepository.findById(dto.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        RecursoEntity recurso = recursoRepository.findById(dto.getRecurso())
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado."));

        // Validação de conflito de horário
        for (ReservaEntity reserva : repository.findAll()) {

            if (reserva.getRecurso().getId() == dto.getRecurso()
                    && reserva.getData().equals(dto.getData())
                    && reserva.getDataCancelamento() == null) {

                boolean conflito =
                        dto.getHoraInicial().isBefore(reserva.getHoraFinal())
                                && dto.getHoraFinal().isAfter(reserva.getHoraInicial());

                if (conflito) {
                    throw new RuntimeException("Já existe uma reserva para este recurso nesse horário.");
                }
            }
        }

        ReservaEntity entity = new ReservaEntity();

        entity.setUsuario(usuario);
        entity.setRecurso(recurso);
        entity.setData(dto.getData());
        entity.setHoraInicial(dto.getHoraInicial());
        entity.setHoraFinal(dto.getHoraFinal());
        entity.setDataCancelamento(null);
        entity.setObservacao(null);

        repository.save(entity);
    }

    public void cancelar(Long id, String observacao) {

        ReservaEntity reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        if (reserva.getDataCancelamento() != null) {
            throw new RuntimeException("Esta reserva já foi cancelada.");
        }

        if (observacao == null || observacao.isBlank()) {
            throw new RuntimeException("Informe o motivo do cancelamento.");
        }

        // Só pode cancelar até 1 dia antes da reserva
        if (LocalDate.now().isAfter(reserva.getData().minusDays(1))) {
            throw new RuntimeException("A reserva só pode ser cancelada até um dia antes da data agendada.");
        }

        reserva.setDataCancelamento(LocalDate.now());
        reserva.setObservacao(observacao);

        repository.save(reserva);
    }

}