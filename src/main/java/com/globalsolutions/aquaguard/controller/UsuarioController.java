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

import com.globalsolutions.aquaguard.model.Usuario;
import com.globalsolutions.aquaguard.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/usuario")
@CacheConfig(cacheNames = "usuarios")
@Slf4j
@Tag(name = "usuário", description = "Usuário que irá se cadastrar no aplicativo de monitoramento")
public class UsuarioController {
    @Autowired
    UsuarioRepository repositoryUsuario;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Usuario"
    )
    public List<Usuario> index() {
        return repositoryUsuario.findAll();
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Usuario por id"
    )
    public ResponseEntity<Usuario> listarUsuario(@PathVariable Long id){

        return repositoryUsuario
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar Usuario"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400")
    })
    public Usuario create(@RequestBody @Valid Usuario usuario) {
        log.info("Cadastrando usuário: {}", usuario);
        repositoryUsuario.save(usuario);
        return usuario;
    }

    @DeleteMapping("{id_usuario}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar Usuario"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "401")
    })
    public void destroy(@PathVariable Long id_usuario) {
        log.info("Apagando usuário");

        verificarSeExisteUsuario(id_usuario);
        repositoryUsuario.deleteById(id_usuario);
    }

    @PutMapping("{id_usuario}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar Usuario"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "401"),
        @ApiResponse(responseCode = "404")
    })
    public Usuario update(@PathVariable Long id_usuario, @RequestBody Usuario usuario){
        log.info("atualizando usuário com id {} para {}", id_usuario, usuario);

        verificarSeExisteUsuario(id_usuario);
        usuario.setId_usuario(id_usuario);
        return repositoryUsuario.save(usuario);
    }

    private void verificarSeExisteUsuario(Long id_usuario) {
        repositoryUsuario
            .findById(id_usuario)
            .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, 
                                "Não existe usuário com o id informado. Consulte lista em /usuario"
                            ));
    }
}
