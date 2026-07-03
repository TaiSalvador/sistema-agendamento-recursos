package com.sistema_agendamento_recursos.senai.dtos;

import com.sistema_agendamento_recursos.senai.entities.DiaSemana;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RecursoDto {

    private long id;
    private String descricao;
    private String tipo;
    private List<DiaSemana> diasDisponiveis;
    private LocalDate dataInicialAgendamento;
    private LocalDate dataFinalAgendamento;
    private LocalTime horaInicialAgendamento;
    private LocalTime horaFinalAgendamento;

    public RecursoDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<DiaSemana> getDiasDisponiveis() {
        return diasDisponiveis;
    }

    public void setDiasDisponiveis(List<DiaSemana> diasDisponiveis) {
        this.diasDisponiveis = diasDisponiveis;
    }

    public LocalDate getDataInicialAgendamento() {
        return dataInicialAgendamento;
    }

    public void setDataInicialAgendamento(LocalDate dataInicialAgendamento) {
        this.dataInicialAgendamento = dataInicialAgendamento;
    }

    public LocalDate getDataFinalAgendamento() {
        return dataFinalAgendamento;
    }

    public void setDataFinalAgendamento(LocalDate dataFinalAgendamento) {
        this.dataFinalAgendamento = dataFinalAgendamento;
    }

    public LocalTime getHoraInicialAgendamento() {
        return horaInicialAgendamento;
    }

    public void setHoraInicialAgendamento(LocalTime horaInicialAgendamento) {
        this.horaInicialAgendamento = horaInicialAgendamento;
    }

    public LocalTime getHoraFinalAgendamento() {
        return horaFinalAgendamento;
    }

    public void setHoraFinalAgendamento(LocalTime horaFinalAgendamento) {
        this.horaFinalAgendamento = horaFinalAgendamento;
    }
}