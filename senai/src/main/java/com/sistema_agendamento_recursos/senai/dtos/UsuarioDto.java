package com.sistema_agendamento_recursos.senai.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioDto {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    private String email;

    @NotBlank(message = "o campo senha é obrigatório")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$", message = "A senha deve conter pelo menos uma letra e um número")
    @Size(min = 5, message = "A senha deve possuir no mínimo 5 caracteres")
    private String senha;

    @NotBlank(message = "O campo matrículo é obrigatório")
    private String matricula;

    private LocalDate dataNascimento;

    public UsuarioDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
