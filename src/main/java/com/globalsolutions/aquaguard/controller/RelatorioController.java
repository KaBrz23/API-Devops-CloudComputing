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

import com.globalsolutions.aquaguard.model.Relatorio;
import com.globalsolutions.aquaguard.model.Usuario;
import com.globalsolutions.aquaguard.repository.RelatorioRepository;
import com.globalsolutions.aquaguard.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/relatorio")
@CacheConfig(cacheNames = "relatorios")
@Slf4j
@Tag(name = "relatório", description = "Relatório dos logs das tilápias e dos tanques")
public class RelatorioController {
    @Autowired
    RelatorioRepository repositoryRelatorio;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Relatório"
    )
    public List<Relatorio> index() {
        return repositoryRelatorio.findAll();
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Relatório por id"
    )
    public ResponseEntity<Relatorio> listarRelatorio(@PathVariable Long id){

        return repositoryRelatorio
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public Relatorio create(@RequestBody @Valid Relatorio relatorio) {
        log.info("Criando relatório: {}", relatorio);
        repositoryRelatorio.save(relatorio);
        return relatorio;
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
    public void destroy(@PathVariable Long id) {
        log.info("Apagando relatório");

        verificarSeExisteRelatorio(id);
        repositoryRelatorio.deleteById(id);
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
    public Relatorio update(@PathVariable Long id, @RequestBody Relatorio relatorio){
        log.info("atualizando relatório com id {} para {}", id, relatorio);

        verificarSeExisteRelatorio(id);
        relatorio.setId_relatorio(id);
        return repositoryRelatorio.save(relatorio);
    }

    private void verificarSeExisteRelatorio(Long id) {
        repositoryRelatorio
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, 
                                "Não existe relatório com o id informado. Consulte lista em /relatorio"
                            ));
    }
}
