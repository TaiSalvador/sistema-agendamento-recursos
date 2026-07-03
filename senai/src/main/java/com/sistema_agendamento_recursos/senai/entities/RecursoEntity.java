package com.sistema_agendamento_recursos.senai.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "recurso")
public class RecursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "descrição")
    private String descricao;

    @Column(name = "tipo")
    private String tipo;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DiaSemana> diasDisponiveis;

    @Column(name = "dataInicialAgendamento")
    private LocalDate dataInicialAgendamento;

    @Column(name = "dataFinalAgendamento")
    private LocalDate dataFinalAgendamento;

    @Column(name = "horaInicialAgendamento")
    private LocalTime horaInicialAgendamento;

    @Column(name = "horaFinalAgendamento")
    private LocalTime horaFinalAgendamento;

    public RecursoEntity() {
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
