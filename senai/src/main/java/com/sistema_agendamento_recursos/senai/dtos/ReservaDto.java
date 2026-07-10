package com.sistema_agendamento_recursos.senai.dtos;

import com.sistema_agendamento_recursos.senai.entities.RecursoEntity;
import com.sistema_agendamento_recursos.senai.entities.UsuarioEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaDto {

    private Long id;

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuario;

    @NotNull(message = "O recurso é obrigatório.")
    private Long recurso;

    private String nomeUsuario;

    private String descricaoRecurso;

    @NotNull(message = "A data é obrigatória.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @NotNull(message = "A hora inicial é obrigatória.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaInicial;

    @NotNull(message = "A hora final é obrigatória.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaFinal;

    private LocalDate dataCancelamento;

    private String observacao;

    public ReservaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getDescricaoRecurso() {
        return descricaoRecurso;
    }

    public void setDescricaoRecurso(String descricaoRecurso) {
        this.descricaoRecurso = descricaoRecurso;
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

    public LocalDate getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDate dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}