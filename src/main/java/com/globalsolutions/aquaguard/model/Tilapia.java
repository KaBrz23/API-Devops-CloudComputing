package com.globalsolutions.aquaguard.model;

import java.time.LocalDate;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globalsolutions.aquaguard.controller.TilapiaController;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tilapia;

    @Doente
    private String isDoente;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @ManyToOne
    private Tanque tanque;

    public EntityModel<Tilapia> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(TilapiaController.class).show(id_tilapia)).withSelfRel(),
            linkTo(methodOn(TilapiaController.class).index(null)).withRel("contents")
        );
    }
}
