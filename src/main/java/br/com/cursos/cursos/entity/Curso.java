package br.com.cursos.cursos.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cursos")
public class Curso extends CursoLog implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcurso")
    private Integer IdCurso;
    
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @Column(name = "qtdAluno", length = 100, nullable = false)
    private String qtdAluno;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura;

    /*@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)*/
	 @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;
    
    
    @JoinColumn
    @ManyToOne
    private Categoria categoria;

	public Curso() {
		// TODO Auto-generated constructor stub
	}


	public Integer getIdCurso() {
		return IdCurso;
	}

	public void setIdCurso(Integer idCurso) {
		IdCurso = idCurso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
		
	public String getQtdAluno() {
		return qtdAluno;
	}


	public void setQtdAluno(String qtdAluno) {
		this.qtdAluno = qtdAluno;
	}


	public LocalDate getDataAbertura() {
		return dataAbertura;
	}


	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}


	public LocalDate getDataFechamento() {
		return dataFechamento;
	}


	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Curso [IdCurso=" + IdCurso + ", descricao=" + descricao + ", qtd_alunos=" + qtdAluno + "]";
	}

}