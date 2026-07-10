package com.sistema_agendamento_recursos.senai.servicies;

import com.sistema_agendamento_recursos.senai.dtos.RecursoDto;
import com.sistema_agendamento_recursos.senai.entities.RecursoEntity;
import com.sistema_agendamento_recursos.senai.repository.RecursoRepository;
import com.sistema_agendamento_recursos.senai.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecursoService {

    private final RecursoRepository repository;

    private final ReservaRepository reservaRepository;

    public RecursoService(RecursoRepository repository, ReservaRepository reservaRepository) {
        this.repository = repository;
        this.reservaRepository = reservaRepository;
    }

    public List<RecursoDto> obterListaRecurso() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public RecursoDto obterRecursoPorId(Long id) {
        RecursoEntity r = repository.findById(id).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        return toDto(r);
    }

    public void inserir(RecursoDto dto){

        validar(dto);

        repository.save(toEntity(dto));
    }

    public void atualizar(RecursoDto dto){

        repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        validar(dto);

        repository.save(toEntity(dto));
    }
    public void excluir(Long id) {

        System.out.println("ID recebido: " + id);

        boolean existe = reservaRepository.existsByRecursoId(id);

        System.out.println("Existe reserva: " + existe);

        if (existe) {
            throw new RuntimeException(
                    "Não é possível excluir este recurso porque ele possui reservas cadastradas."
            );
        }

        repository.deleteById(id);
    }

    private RecursoEntity toEntity(RecursoDto dto) {
        RecursoEntity r = new RecursoEntity();

        r.setId(dto.getId());
        r.setDescricao(dto.getDescricao());
        r.setTipo(dto.getTipo());

        r.setDiasDisponiveis(dto.getDiasDisponiveis());

        r.setDataInicialAgendamento(dto.getDataInicialAgendamento());
        r.setDataFinalAgendamento(dto.getDataFinalAgendamento());
        r.setHoraInicialAgendamento(dto.getHoraInicialAgendamento());
        r.setHoraFinalAgendamento(dto.getHoraFinalAgendamento());

        return r;
    }

    private RecursoDto toDto(RecursoEntity r) {
        RecursoDto dto = new RecursoDto();

        dto.setId(r.getId());
        dto.setDescricao(r.getDescricao());
        dto.setTipo(r.getTipo());

        dto.setDiasDisponiveis(r.getDiasDisponiveis());

        dto.setDataInicialAgendamento(r.getDataInicialAgendamento());
        dto.setDataFinalAgendamento(r.getDataFinalAgendamento());
        dto.setHoraInicialAgendamento(r.getHoraInicialAgendamento());
        dto.setHoraFinalAgendamento(r.getHoraFinalAgendamento());

        return dto;
    }

    private void validar(RecursoDto dto){

        LocalDate hoje = LocalDate.now();

        if(dto.getDataInicialAgendamento().isBefore(hoje)){
            throw new IllegalArgumentException("A data inicial não pode ser anterior à data atual.");
        }

        if(dto.getDataFinalAgendamento().isBefore(dto.getDataInicialAgendamento())){
            throw new IllegalArgumentException("A data final deve ser maior ou igual à inicial.");
        }

        if(!dto.getHoraFinalAgendamento().isAfter(dto.getHoraInicialAgendamento())){
            throw new IllegalArgumentException("A hora final deve ser maior que a inicial.");
        }

    }
}
