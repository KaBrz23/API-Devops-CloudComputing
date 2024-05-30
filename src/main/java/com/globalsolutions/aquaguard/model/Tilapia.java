package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globalsolutions.aquaguard.validation.Doente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tilapia {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_tilapia;

    @Doente
    private String isDoente;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @ManyToOne
    private Tanque tanque;
}
