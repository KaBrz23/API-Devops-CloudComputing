package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globalsolutions.aquaguard.validation.StatusSaude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Tilapia {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_tilapia;

    @StatusSaude
    private String status_saude;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
}
