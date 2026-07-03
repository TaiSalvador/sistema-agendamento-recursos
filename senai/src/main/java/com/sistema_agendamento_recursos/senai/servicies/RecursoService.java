package com.sistema_agendamento_recursos.senai.servicies;

import com.sistema_agendamento_recursos.senai.dtos.RecursoDto;
import com.sistema_agendamento_recursos.senai.entities.RecursoEntity;
import com.sistema_agendamento_recursos.senai.repository.RecursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecursoService {

    private final RecursoRepository repository;

    public RecursoService(RecursoRepository repository) {
        this.repository = repository;
    }

    public List<RecursoDto> obterListaRecurso() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public RecursoDto obterRecursoPorId(Long id) {
        RecursoEntity r = repository.findById(id).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        return toDto(r);
    }

    public void inserir(RecursoDto dto) {
        repository.save(toEntity(dto));
    }

    public void atualizar(RecursoDto dto) {

        RecursoEntity r = repository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        RecursoEntity atualizado = toEntity(dto);
        atualizado.setId(dto.getId());

        repository.save(atualizado);
    }

    public void excluir(Long id) {
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
}
