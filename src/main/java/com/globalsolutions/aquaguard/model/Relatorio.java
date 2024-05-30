package com.globalsolutions.aquaguard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Relatorio {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_relatorio;

    @NotNull(message = "{relatorio.descricao.notnull}")
    private String descricao;
}
