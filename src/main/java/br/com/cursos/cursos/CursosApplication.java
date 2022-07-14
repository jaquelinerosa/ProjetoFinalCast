package br.com.cursos.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.cursos.cursos.services.AuditingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditingService")
public class CursosApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CursosApplication.class);

	public static void main(String[] args) {
		LOGGER.info("iniciando Api de Cadastro");

		SpringApplication.run(CursosApplication.class, args);
		LOGGER.info("Api de cadastro iniciada com sucesso");
	}

	@Bean
	AuditorAware<String> auditorProvider() {
		return new AuditingService();
	}
}
