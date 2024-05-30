package com.globalsolutions.aquaguard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_relatorio;

    @NotNull(message = "{relatorio.descricao.notnull}")
    private String descricao;

    @ManyToOne
    private Usuario usuario;
}
