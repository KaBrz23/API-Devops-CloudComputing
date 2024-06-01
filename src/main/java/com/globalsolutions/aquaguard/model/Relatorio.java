package com.globalsolutions.aquaguard.model;

import org.springframework.hateoas.EntityModel;

import com.globalsolutions.aquaguard.controller.RelatorioController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    public EntityModel<Relatorio> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(RelatorioController.class).show(id_relatorio)).withSelfRel(),
            linkTo(methodOn(RelatorioController.class).destroy(id_relatorio)).withRel("delete"),
            linkTo(methodOn(RelatorioController.class).index(null)).withRel("contents")
        );
    }
}
