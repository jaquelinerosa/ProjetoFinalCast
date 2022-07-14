package br.com.cursos.cursos.repositories;


import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cursos.cursos.entity.Curso;



@Repository
public interface CursoRepository  extends JpaRepository<Curso, Integer> {
	/*SELECT * FROM cursos c WHERE c.data_abertura BETWEEN '2022-07-07' AND '2022-07-09';*/
	/*@Query(value = "from Curso c where c.dataAbertura BETWEEN :startDate AND :endDate")
	public List<Curso> getAllBetweenDates(@Param("startDate")LocalDate startDate,@Param("endDate")LocalDate endDate);*/

	@Query ("select c from Curso c where descricao like Concat(:desc)")
	public Curso getByDescricao(@Param("desc") String descricao);
	
	@Query("SELECT c from Curso c  where c.dataAbertura <= :data_abertura AND c.dataFechamento >= :data_fechamento") 
    Optional<Curso> exists(@Param("data_abertura") LocalDate data_inicio, @Param("data_fechamento") LocalDate data_fim);
}