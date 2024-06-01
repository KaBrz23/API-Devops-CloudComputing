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
import com.globalsolutions.aquaguard.model.Relatorio;
import com.globalsolutions.aquaguard.repository.RelatorioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/relatorio")
@CacheConfig(cacheNames = "relatorios")
@Tag(name = "relatório", description = "Relatório dos logs das tilápias e dos tanques")
public class RelatorioController {
    @Autowired
    RelatorioRepository repositoryRelatorio;

    @Autowired
    PagedResourcesAssembler<Relatorio> pageAssembler;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Relatório"
    )
    public PagedModel<EntityModel<Relatorio>> index(
        @PageableDefault(size = 3) Pageable pageable
    ) {
        Page<Relatorio> page = null;

        if (page == null){
            page = repositoryRelatorio.findAll(pageable);
        }

        return pageAssembler.toModel(page);
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Relatório por id"
    )
    public EntityModel<Relatorio> show(@PathVariable Long id){
        var relatorio =  repositoryRelatorio.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório não encontrado")
        );
        
        return relatorio.toEntityModel();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Criar Relatório"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Relatorio> create(@RequestBody @Valid Relatorio relatorio) {
        repositoryRelatorio.save(relatorio);

        return ResponseEntity
                    .created(relatorio.toEntityModel().getRequiredLink("self").toUri())
                    .body(relatorio);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar relatório"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "401")
    })
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        repositoryRelatorio.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório não encontrado")
        );

        repositoryRelatorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar relatório"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "401"),
        @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Relatorio> update(@PathVariable Long id, @RequestBody Relatorio relatorioAtualizado){
        Relatorio relatorio = repositoryRelatorio.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório não encontrado")
        );
        
        relatorio.setDescricao(relatorioAtualizado.getDescricao());
        relatorio.setUsuario(relatorioAtualizado.getUsuario());
    
        repositoryRelatorio.save(relatorio);
    
        return ResponseEntity.ok(relatorio);
    }
}
