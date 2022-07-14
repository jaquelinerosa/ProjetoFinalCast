package br.com.cursos.cursos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class CorsConfig extends WebMvcConfigurerAdapter {

	   @Override
	    public void addCorsMappings(CorsRegistry registry) {
	/*ALLOWED É QUE PERMITE*/
	        registry
	                .addMapping("/*")
	                .allowedOrigins("")
	                .allowedMethods("GET", "POST", "PUT", "DELETE");
	    }
	}

