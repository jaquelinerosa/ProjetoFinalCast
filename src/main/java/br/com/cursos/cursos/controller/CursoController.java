package br.com.cursos.cursos.controller;


import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.cursos.cursos.CursosApplication;
import br.com.cursos.cursos.entity.Curso;

import br.com.cursos.cursos.services.CursoService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/cursos")
public class CursoController {
	/* tabela log */
	private static final Logger LOGGER = LoggerFactory.getLogger(CursosApplication.class);

	@Autowired
	private CursoService service;

	//método post
	@ApiOperation("Serviço para cadastrar novo curso")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cadastrar(@RequestBody Curso curso) {
		try {
			service.cadastrar(curso);
			LOGGER.info("Criado com Sucesso");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			LOGGER.error("Erro ao criar o cadastro");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}
	// x-x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x
	

	// x-x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x
	// método busca por periodo e descricao 
	
	 
	 @GetMapping
    public ResponseEntity<List<Curso>> filtroNomeData(
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAbertura,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFechamento) {
        LOGGER.info("Curso listado");
        return ResponseEntity.ok(service.filtroComposto(descricao, dataAbertura, dataFechamento));
    }


	

	// x-x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x
		//metodo editar
	@ApiOperation("Atualizar por Id")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Curso curso) {
		try {
			service.editar(curso);
			LOGGER.info("Criado com Sucesso");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			LOGGER.error("Erro ao criar o cadastro");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro:" + e.getMessage());
		}
	}

	// x-x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x-x--x-x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x--x-x-x

	@ApiOperation("Serviço para deletar curso por ID")
	@DeleteMapping(value = "{IdCurso}")
	public ResponseEntity<String> deleteById(@PathVariable("IdCurso") Integer IdCurso) {
		try {
			service.deletar(IdCurso);
			LOGGER.info("Criado com Sucesso");
			return ResponseEntity.ok().build();

		} catch (Exception e) {
			LOGGER.error("Erro ao deletar o cadastro");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
		
	
	}

	

	
	
}