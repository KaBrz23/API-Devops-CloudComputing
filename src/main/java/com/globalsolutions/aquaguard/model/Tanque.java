package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;

import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globalsolutions.aquaguard.controller.UsuarioController;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tanque;

    @NotBlank(message = "{tanque.nometanque.notblank}")
    private String nomeTanque;

    @Fissura
    private String hasFissuras;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @ManyToOne
    private Usuario usuario;

    public EntityModel<Tanque> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(UsuarioController.class).show(id_tanque)).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).destroy(id_tanque)).withRel("delete"),
            linkTo(methodOn(UsuarioController.class).index(null)).withRel("contents")
        );
    }
}
