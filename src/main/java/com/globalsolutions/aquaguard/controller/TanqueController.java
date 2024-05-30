package com.globalsolutions.aquaguard.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

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

import com.globalsolutions.aquaguard.model.Tanque;
import com.globalsolutions.aquaguard.repository.TanqueRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tanque")
@CacheConfig(cacheNames = "tanques")
@Slf4j
@Tag(name = "tanque", description = "tanque que está sendo monitorado")
public class TanqueController {
    @Autowired
    TanqueRepository repositoryTanque;
    
    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Tanques"
    )
    public List<Tanque> index() {
        return repositoryTanque.findAll();
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Tanque por id"
    )
    public ResponseEntity<Tanque> listarTanque(@PathVariable Long id){

        return repositoryTanque
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public Tanque create(@RequestBody @Valid Tanque tanque) {
        log.info("Cadastrando tanque: {}", tanque);
        repositoryTanque.save(tanque);
        return tanque;
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
    public void destroy(@PathVariable Long id) {
        log.info("Apagando tanque");

        verificarSeExisteTanque(id);
        repositoryTanque.deleteById(id);
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
    public Tanque update(@PathVariable Long id, @RequestBody Tanque tanque){
        log.info("atualizando tanque com id {} para {}", id, tanque);

        verificarSeExisteTanque(id);
        tanque.setId_tanque(id);
        return repositoryTanque.save(tanque);
    }

    private void verificarSeExisteTanque(Long id) {
        repositoryTanque
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, 
                                "Não existe tanque com o id informado. Consulte lista em /tanque"
                            ));
    }
}
