package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globalsolutions.aquaguard.validation.TipoSexo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_usuario;

    @NotBlank(message = "{usuario.nome.notblank}")
    private String nome;

    @Email
    private String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    @Size(min = 8, max = 255, message = "{usuario.senha.size}")
    private String senha;

    @NotBlank(message = "{usuario.telefone.notblank}")
    private String telefone;

    @CPF
    @NotBlank(message = "{usuario.cpf.notblank}")
    private String cpf;

    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotBlank(message = "{usuario.datanascimento.notblank}")
    private LocalDate data_nascimento;

    @TipoSexo
    private String sexo;
}
