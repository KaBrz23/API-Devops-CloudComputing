package com.globalsolutions.aquaguard.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.globalsolutions.aquaguard.model.Relatorio;
import com.globalsolutions.aquaguard.model.Tanque;
import com.globalsolutions.aquaguard.model.Tilapia;
import com.globalsolutions.aquaguard.model.Usuario;
import com.globalsolutions.aquaguard.repository.RelatorioRepository;
import com.globalsolutions.aquaguard.repository.TanqueRepository;
import com.globalsolutions.aquaguard.repository.TilapiaRepository;
import com.globalsolutions.aquaguard.repository.UsuarioRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner{
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TilapiaRepository tilapiaRepository;

    @Autowired
    TanqueRepository tanqueRepository;

    @Autowired
    RelatorioRepository relatorioRepository;

    @Override
    public void run(String... args) throws Exception {
        usuarioRepository.saveAll(List.of(
            Usuario.builder()
                .id_usuario(1L)
                .nome("Kauê Fernandes Braz")
                .email("kaue@fiap.com.br")
                .senha("12345678")
                .telefone("123456789")
                .cpf("54610446898")
                .data_nascimento(LocalDate.now().minusWeeks(998))
                .sexo("Masculino")
                .build()
        ));

        tanqueRepository.saveAll(List.of(
            Tanque.builder()
                .id_tanque(1L)
                .nomeTanque("Tanque Principal")
                .hasFissuras("Detectado")
                .data(LocalDate.now())
                .usuario(usuarioRepository.findById(1L).get())
                .build()
        ));


        tilapiaRepository.saveAll(List.of(
            Tilapia.builder()
                .id_tilapia(1L)
                .isDoente("Doente")
                .data(LocalDate.now())
                .tanque(tanqueRepository.findById(1L).get())
                .build()
        ));
        
        relatorioRepository.saveAll(List.of(
            Relatorio.builder()
                .id_relatorio(1L)
                .descricao("Foi detectado 1 Tilápia Doente e há uma fissura no Tanque Principal")
                .usuario(usuarioRepository.findById(1L).get())
                .build()
        ));
    }
}
