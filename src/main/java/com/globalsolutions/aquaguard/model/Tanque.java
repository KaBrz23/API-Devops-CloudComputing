package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globalsolutions.aquaguard.validation.Fissura;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tanque {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_tanque;

    @NotBlank(message = "{tanque.nometanque.notblank}")
    private String nomeTanque;

    @Fissura
    private String hasFissuras;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @ManyToOne
    private Usuario usuario;
}
