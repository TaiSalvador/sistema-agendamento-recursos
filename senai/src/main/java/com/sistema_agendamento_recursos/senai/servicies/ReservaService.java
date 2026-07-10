package com.sistema_agendamento_recursos.senai.servicies;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.entities.DiaSemana;
import com.sistema_agendamento_recursos.senai.entities.RecursoEntity;
import com.sistema_agendamento_recursos.senai.entities.ReservaEntity;
import com.sistema_agendamento_recursos.senai.entities.UsuarioEntity;
import com.sistema_agendamento_recursos.senai.repository.RecursoRepository;
import com.sistema_agendamento_recursos.senai.repository.ReservaRepository;
import com.sistema_agendamento_recursos.senai.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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

            // Ignora reservas canceladas
            if (entity.getDataCancelamento() != null) {
                continue;
            }

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


        LocalDate hoje = LocalDate.now();


        // Não permite data passada
        if (dto.getData().isBefore(hoje)) {
            throw new RuntimeException("Não é possível realizar reserva em uma data que já passou.");
        }


        // Limite máximo de 5 dias para reserva
        LocalDate limite = hoje.plusDays(5);

        if (dto.getData().isAfter(limite)) {
            throw new RuntimeException("A reserva só pode ser feita com até 5 dias de antecedência.");
        }


        // Verifica se a data está dentro do período permitido pelo recurso
        if (dto.getData().isBefore(recurso.getDataInicialAgendamento())
                || dto.getData().isAfter(recurso.getDataFinalAgendamento())) {

            throw new RuntimeException("A data da reserva está fora do período permitido para este recurso.");
        }


        // Verifica se o horário está dentro do horário permitido pelo recurso
        if (dto.getHoraInicial().isBefore(recurso.getHoraInicialAgendamento())
                || dto.getHoraFinal().isAfter(recurso.getHoraFinalAgendamento())) {

            throw new RuntimeException("O horário da reserva está fora do horário permitido para este recurso.");
        }


        // Verifica o dia da semana
        DayOfWeek dia = dto.getData().getDayOfWeek();

        DiaSemana diaReserva;

        switch (dia) {

            case MONDAY:
                diaReserva = DiaSemana.SEGUNDA;
                break;

            case TUESDAY:
                diaReserva = DiaSemana.TERCA;
                break;

            case WEDNESDAY:
                diaReserva = DiaSemana.QUARTA;
                break;

            case THURSDAY:
                diaReserva = DiaSemana.QUINTA;
                break;

            case FRIDAY:
                diaReserva = DiaSemana.SEXTA;
                break;

            case SATURDAY:
                diaReserva = DiaSemana.SABADO;
                break;

            case SUNDAY:
                diaReserva = DiaSemana.DOMINGO;
                break;

            default:
                throw new RuntimeException("Dia inválido.");
        }


        if (!recurso.getDiasDisponiveis().contains(diaReserva)) {
            throw new RuntimeException("Este recurso não está disponível neste dia da semana.");
        }



        // Verifica conflito de horário
        for (ReservaEntity reserva : repository.findAll()) {


            if (reserva.getRecurso().getId().equals(recurso.getId())) {


                if (reserva.getData().equals(dto.getData())
                        && reserva.getDataCancelamento() == null) {


                    boolean horarioOcupado =
                            dto.getHoraInicial().isBefore(reserva.getHoraFinal())
                                    &&
                                    dto.getHoraFinal().isAfter(reserva.getHoraInicial());


                    if (horarioOcupado) {
                        throw new RuntimeException("Recurso indisponível por ocupação neste horário.");
                    }

                }

            }

        }


        // Salvar reserva
        ReservaEntity entity = new ReservaEntity();

        entity.setUsuario(usuario);
        entity.setRecurso(recurso);
        entity.setData(dto.getData());
        entity.setHoraInicial(dto.getHoraInicial());
        entity.setHoraFinal(dto.getHoraFinal());
        entity.setDataCancelamento(null);
        entity.setObservacao(dto.getObservacao());


        repository.save(entity);

    }



    public void cancelar(Long id, String observacao) {

        ReservaEntity reserva = repository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada."));


        if (reserva.getDataCancelamento() != null) {
            throw new RuntimeException("Esta reserva já foi cancelada.");
        }


        if (observacao == null || observacao.isBlank()) {
            throw new RuntimeException("Informe o motivo do cancelamento.");
        }


        if (LocalDate.now().isAfter(reserva.getData().minusDays(1))) {
            throw new RuntimeException("A reserva só pode ser cancelada até um dia antes da data agendada.");
        }


        reserva.setDataCancelamento(LocalDate.now());
        reserva.setObservacao(observacao);


        repository.save(reserva);
    }

    public List<ReservaDto> obterListaReservaCancelada() {

        List<ReservaDto> lista = new ArrayList<>();

        for (ReservaEntity entity : repository.findByDataCancelamentoIsNotNull()) {

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


}