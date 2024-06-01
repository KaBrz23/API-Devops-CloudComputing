package com.globalsolutions.aquaguard.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.globalsolutions.aquaguard.model.Tanque;
import com.globalsolutions.aquaguard.repository.TanqueRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tanque")
@CacheConfig(cacheNames = "tanques")
@Tag(name = "tanque", description = "tanque que está sendo monitorado")
public class TanqueController {
    @Autowired
    TanqueRepository repositoryTanque;

    @Autowired
    PagedResourcesAssembler<Tanque> pageAssembler;
    
    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Tanques"
    )
    public PagedModel<EntityModel<Tanque>> index(
        @PageableDefault(size = 3) Pageable pageable
    ) {
        Page<Tanque> page = null;

        if (page == null){
            page = repositoryTanque.findAll(pageable);
        }

        return pageAssembler.toModel(page);
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Tanque por id"
    )
    public EntityModel<Tanque> show(@PathVariable Long id){
        var tanque =  repositoryTanque.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Tanque não encontrado")
        );
        
        return tanque.toEntityModel();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar Tanque"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Tanque> create(@RequestBody @Valid Tanque tanque) {
        repositoryTanque.save(tanque);

        return ResponseEntity
                    .created(tanque.toEntityModel().getRequiredLink("self").toUri())
                    .body(tanque);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar Tanque"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "401")
    })
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        repositoryTanque.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Tanque não encontrado")
        );

        repositoryTanque.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar tanque"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "401"),
        @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Tanque> update(@PathVariable Long id, @RequestBody Tanque tanqueAtualizado){
        Tanque tanque = repositoryTanque.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Tanque não encontrado")
        );
        
        tanque.setNomeTanque(tanqueAtualizado.getNomeTanque());
        tanque.setHasFissuras(tanqueAtualizado.getHasFissuras());
        tanque.setData(tanqueAtualizado.getData());
        tanque.setUsuario(tanqueAtualizado.getUsuario());
    
        repositoryTanque.save(tanque);
    
        return ResponseEntity.ok(tanque);
    }
}
