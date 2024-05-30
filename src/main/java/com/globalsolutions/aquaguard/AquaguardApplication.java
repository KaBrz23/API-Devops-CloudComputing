package com.globalsolutions.aquaguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@Controller
@EnableCaching
@OpenAPIDefinition(
	info = @Info(
		title = "API do AquaGuard",
		description = "APP de monitoramento de Tilápias",
		contact = @Contact(name = "Kauê Braz", email = "rm97768@fiap.com.br"),
		version = "1.0.0"
	)
)
public class AquaguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AquaguardApplication.class, args);
	}

	@RequestMapping
	@ResponseBody
	public String home(){
		return "AquaGuard: Monitoramento Inteligente de Tilápias";
	}

}
