package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Tanque {
    private Long id_tanque;

    @NotBlank(message = "{tanque.nometanque.notblank}")
    private String nomeTanque;

    @NotBlank(message = "{tanque.hasfissuras.notblank}")
    private boolean hasFissuras;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
}