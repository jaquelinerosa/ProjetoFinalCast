package br.com.cursos.cursos.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cursos.cursos.entity.Curso;
import br.com.cursos.cursos.repositories.CursoRepository;

@Service
public class CursoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CursoService.class);

	@PersistenceContext
	EntityManager em;

	@Autowired
	CursoRepository cursoRepository;

	@Transactional
	public void cadastrar(Curso curso) {
		validaData(curso);
		validacao(curso);
		regraValidaCurso(curso);
		cursoRepository.save(curso);
	}

	@Transactional
	public void deletar(Integer IdCurso) {
		LOGGER.info("Deletado com Sucesso");
		validaDelete(IdCurso);
		cursoRepository.deleteById(IdCurso);
	}

	@Transactional
	public void editar(Curso IdCurso) {
		LOGGER.info("Editado com Sucesso");
		cursoRepository.save(IdCurso);
	}

	/*
	 * // metodo listagem com listagem por periodo e descricao
	 * 
	 * public List<Curso> listagem(String descricao, LocalDate dataAbertura,
	 * LocalDate dataFechamento) { LOGGER.info("Listado com Sucesso"); // método que
	 * constroi esse criteria builder CriteriaBuilder cb = em.getCriteriaBuilder();
	 * CriteriaQuery<Curso> cq = cb.createQuery(Curso.class); // criei um curso como
	 * se é a raiz como no sql select curso Root<Curso> curso =
	 * cq.from(Curso.class); // lista de predicate que pode ser vazia ou nao
	 * List<Predicate> predicates = new ArrayList(); // aqui estou testando se a
	 * descrição foi informada, se sim, criamos um // predicate descricao que tneha
	 * a descricao informada // se sim vou adicionar na lista que foi criada em if
	 * (descricao != null) { Predicate descricaoPredicate =
	 * cb.equal(curso.get("descricao"), descricao);
	 * 
	 * predicates.add(descricaoPredicate); }
	 * 
	 * Predicate[] predicateArray = new Predicate[predicates.size()];
	 * predicates.toArray(predicateArray); cq.where(predicateArray);
	 * 
	 * TypedQuery<Curso> query = em.createQuery(cq); return query.getResultList();
	 * 
	 * }
	 */
	
	
	  public List<Curso> filtroComposto(String descricao, LocalDate dataAbertura, LocalDate dataFechamento) {

		CriteriaBuilder criteria = em.getCriteriaBuilder();
		CriteriaQuery<Curso> criteriaQuery = criteria.createQuery(Curso.class);

		Root<Curso> curso = criteriaQuery.from(Curso.class);
		List<Predicate> predList = new ArrayList<>();

		if (descricao != null) {
			Predicate descricaoPredicate = criteria.equal(curso.get("descricao"), descricao);
			predList.add(descricaoPredicate);
		}

		if (dataAbertura != null) {
			Predicate dataIniPredicate = criteria.greaterThanOrEqualTo(curso.get("dataAbertura"), dataAbertura);
			predList.add(dataIniPredicate);
		}

		if (dataFechamento != null) {
			Predicate dataTerPredicate = criteria.lessThanOrEqualTo(curso.get("dataFechamento"), dataFechamento);
			predList.add(dataTerPredicate);
		}

		Predicate[] predicateArray = new Predicate[predList.size()];

		predList.toArray(predicateArray);

		criteriaQuery.where(predicateArray);

		TypedQuery<Curso> query = em.createQuery(criteriaQuery);

		return query.getResultList();
	} 


	
    
	/* nao permitido o mesmo periodo */
	private void validaData(Curso curso) {
		if (curso.getDataAbertura().isAfter(curso.getDataFechamento())) {
			throw new RuntimeException(" Data de Abertura não pode ser maior que a Data de Fechamento!");
		}

		if (curso.getDataAbertura().isBefore(LocalDate.now())) {
			LOGGER.info("Período por data requisitado com sucesso");
			throw new RuntimeException("Data de início menor que a data atual!");
		}

		//List<Curso> cursosBuscados = cursoRepository.getAllBetweenDates(curso.getDataAbertura(),
		//		curso.getDataFechamento());
		// if (cursosBuscados.size() > 0) {
		//	LOGGER.info("Período informado nao atende as requisicoes");
		//	throw new RuntimeException("Existe(m) curso(s) planejados(s) dentro do período informado, já cadastrado!");
		//}

	}

	// método pra buscar por nome
	public Curso findByDescricao(String descricao) {
		LOGGER.info("Busca por Nome chamada com sucesso!");
		return cursoRepository.getByDescricao(descricao);

	}

	// método exclusão de cursos ja realizado
	public void validaDelete(Integer idcurso) {
		Optional<Curso> curso = cursoRepository.findById(idcurso);
		Curso c = curso.get();

		if (c.getDataFechamento().isBefore(LocalDate.now())) {
			throw new RuntimeException("Não é possível excluir este curso, já realizado.");
		}
	}
	private ResponseEntity<String> validacao (Curso curso){
        Optional<Curso> cursos = cursoRepository.exists(curso.getDataAbertura(), curso.getDataFechamento());
        if (curso != null && !cursos.isEmpty()) {
            LOGGER.info("Mensagem de log: data não validada");
            throw new RuntimeException("Existe(m) Curso(s) Planejados(s) Dentro do Período Informado");
        }

        return null;
    }
	
	public void regraValidaCurso(Curso curso) {

        for (Curso aux : cursoRepository.findAll()) {
            if (aux.getDescricao().equals(curso.getDescricao())) {
                throw new RuntimeException("Este curso já existe.");
            }
        }

    }
	
}
