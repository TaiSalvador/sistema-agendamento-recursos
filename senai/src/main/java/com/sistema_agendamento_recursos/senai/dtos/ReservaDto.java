package com.sistema_agendamento_recursos.senai.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDto {

    private long id;

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuario;

    @NotNull(message = "O recurso é obrigatório.")
    private Long recurso;

    @NotNull(message = "A data é obrigatória.")
    private LocalDate data;

    @NotNull(message = "A hora inicial é obrigatória.")
    private LocalTime horaInicial;

    @NotNull(message = "A hora final é obrigatória.")
    private LocalTime horaFinal;


    public ReservaDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getRecurso() {
        return recurso;
    }

    public void setRecurso(Long recurso) {
        this.recurso = recurso;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(LocalTime horaInicial) {
        this.horaInicial = horaInicial;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }
}
