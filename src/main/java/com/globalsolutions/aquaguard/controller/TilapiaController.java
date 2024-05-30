package com.globalsolutions.aquaguard.controller;

import java.util.List;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.web.server.ResponseStatusException;

import com.globalsolutions.aquaguard.model.Tilapia;
import com.globalsolutions.aquaguard.repository.TilapiaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tilapia")
@CacheConfig(cacheNames = "tilapias")
@Slf4j
@Tag(name = "tilápia", description = "Tilápia que está sendo monitorada")
public class TilapiaController {
    @Autowired
    TilapiaRepository repositoryTilapia;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Tilápia"
    )
    public List<Tilapia> index() {
        return repositoryTilapia.findAll();
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Tilápia por id"
    )
    public ResponseEntity<Tilapia> listarTilapia(@PathVariable Long id){

        return repositoryTilapia
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public Tilapia create(@RequestBody @Valid Tilapia tilapia) {
        log.info("Cadastrando tilápia: {}", tilapia);
        repositoryTilapia.save(tilapia);
        return tilapia;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar Tilápia"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "401")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Apagando Tilápia");

        verificarSeExisteTilapia(id);
        repositoryTilapia.deleteById(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar Tilápia"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "401"),
        @ApiResponse(responseCode = "404")
    })
    public Tilapia update(@PathVariable Long id, @RequestBody Tilapia tilapia){
        log.info("atualizando tilápia com id {} para {}", id, tilapia);

        verificarSeExisteTilapia(id);
        tilapia.setId_tilapia(id);
        return repositoryTilapia.save(tilapia);
    }

    private void verificarSeExisteTilapia(Long id) {
        repositoryTilapia
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, 
                                "Não existe tilápia com o id informado. Consulte lista em /tilapia"
                            ));
    }

}
