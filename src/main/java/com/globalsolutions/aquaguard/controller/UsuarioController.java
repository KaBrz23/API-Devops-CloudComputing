package com.globalsolutions.aquaguard.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

    @Autowired
    PagedResourcesAssembler<Usuario> pageAssembler;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Usuario"
    )
    public PagedModel<EntityModel<Usuario>> index(
        @PageableDefault(size = 3) Pageable pageable
    ) {
        Page<Usuario> page = null;

        if (page == null){
            page = repositoryUsuario.findAll(pageable);
        }

        return pageAssembler.toModel(page);
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Usuario por id"
    )
    public EntityModel<Usuario> show(@PathVariable Long id){
        var usuario =  repositoryUsuario.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Usuário não encontrado")
        );
        
        return usuario.toEntityModel();
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
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario usuario) {
        repositoryUsuario.save(usuario);

        return ResponseEntity
                    .created(usuario.toEntityModel().getRequiredLink("self").toUri())
                    .body(usuario);
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
    public ResponseEntity<Object> destroy(@PathVariable Long id_usuario) {
        repositoryUsuario.findById(id_usuario).orElseThrow(
            () -> new IllegalArgumentException("Usuário não encontrado")
        );

        repositoryUsuario.deleteById(id_usuario);

        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Usuario> update(@PathVariable Long id_usuario, @RequestBody @Valid Usuario usuarioAtualizado) {
        Usuario usuario = repositoryUsuario.findById(id_usuario).orElseThrow(
            () -> new IllegalArgumentException("Usuário não encontrado")
        );
    
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setTelefone(usuarioAtualizado.getTelefone());
        usuario.setCpf(usuarioAtualizado.getCpf());
        usuario.setData_nascimento(usuarioAtualizado.getData_nascimento());
        usuario.setSexo(usuarioAtualizado.getSexo());
    
        repositoryUsuario.save(usuario);
    
        return ResponseEntity.ok(usuario);
    }


}
