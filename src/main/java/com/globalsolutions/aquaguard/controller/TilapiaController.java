package com.globalsolutions.aquaguard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.globalsolutions.aquaguard.model.Tilapia;
import com.globalsolutions.aquaguard.repository.TilapiaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tilapia")
@CacheConfig(cacheNames = "tilapias")
@Tag(name = "tilápia", description = "Tilápia que está sendo monitorada")
public class TilapiaController {
    @Autowired
    TilapiaRepository repositoryTilapia;

    @Autowired
    PagedResourcesAssembler<Tilapia> pageAssembler;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Tilápia"
    )
    public PagedModel<EntityModel<Tilapia>> index(
        @PageableDefault(size = 3) Pageable pageable
    ) {
        Page<Tilapia> page = null;

        if (page == null){
            page = repositoryTilapia.findAll(pageable);
        }

        return pageAssembler.toModel(page);
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Tilápia por id"
    )
    public EntityModel<Tilapia> show(@PathVariable Long id){
        var tilapia =  repositoryTilapia.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Tilápia não encontrada")
        );
        
        return tilapia.toEntityModel();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar Tilapia"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Tilapia> create(@RequestBody @Valid Tilapia tilapia) {
        repositoryTilapia.save(tilapia);

        return ResponseEntity
                    .created(tilapia.toEntityModel().getRequiredLink("self").toUri())
                    .body(tilapia);
    }


}
