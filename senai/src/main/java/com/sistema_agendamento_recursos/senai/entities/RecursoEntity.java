package com.sistema_agendamento_recursos.senai.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import javax.xml.crypto.Data;

@Entity
@Table(name = "recurso")
public class RecursoEntity {

    private long id;
    private String descricao;
    private String tipo;
    private Data diasSemanaDisponivel;
    private Data dataInicialAgendamento;
    private Data dataFinalAgendamento;
    private Data horaInicialAgendamento;
    private Data horaFinalAgendamento;

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

    public Data getDiasSemanaDisponivel() {
        return diasSemanaDisponivel;
    }

    public void setDiasSemanaDisponivel(Data diasSemanaDisponivel) {
        this.diasSemanaDisponivel = diasSemanaDisponivel;
    }

    public Data getDataInicialAgendamento() {
        return dataInicialAgendamento;
    }

    public void setDataInicialAgendamento(Data dataInicialAgendamento) {
        this.dataInicialAgendamento = dataInicialAgendamento;
    }

    public Data getDataFinalAgendamento() {
        return dataFinalAgendamento;
    }

    public void setDataFinalAgendamento(Data dataFinalAgendamento) {
        this.dataFinalAgendamento = dataFinalAgendamento;
    }

    public Data getHoraInicialAgendamento() {
        return horaInicialAgendamento;
    }

    public void setHoraInicialAgendamento(Data horaInicialAgendamento) {
        this.horaInicialAgendamento = horaInicialAgendamento;
    }

    public Data getHoraFinalAgendamento() {
        return horaFinalAgendamento;
    }

    public void setHoraFinalAgendamento(Data horaFinalAgendamento) {
        this.horaFinalAgendamento = horaFinalAgendamento;
    }

}
