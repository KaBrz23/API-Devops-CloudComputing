package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Tilapia {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id_tilapia;

    @StatusSaude
    private String status_saude;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
}
