package com.graphql.exemple.model;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.graphql.exemple.core.GenericEntity;
import com.graphql.exemple.util.CustomDateDeserializer;
import com.graphql.exemple.util.DateFormat;

/**
 * Entidade que define o vinculo ao qual o colaborador exerce
 * 
 * @author Reinaldo Sousa
 * 
 */
@Entity
@Table(name = "vinculo")
@AttributeOverride(name = "id", column = @Column(name = "id_vinculo"))
@Where(clause = "ativo = true")
public class Vinculo extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Status do Vínculo: Ativo ou Inativo
	 */
	@NotNull(message = "Status do Vínculo vazio.")
	private Boolean statusVinculo;

	/**
	 * Define o tipo de vinculo que a pessoa tem com a instituição, podendo ser
	 * estatutario, clt, bolsista, resindente, estagiario
	 */
	private String tipoVinculo;

	/**
	 * Numero da matricula da pessoa
	 */
	private String matricula;

	/**
	 * Data que a pessoa começou as atividades na instituição
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = DateFormat.DATE, timezone = DateFormat.TIMEZONE)
	private Date dataAdmissao;

	/**
	 * Referencia do cargo do colaborador
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cargo")
	private Cargo cargo;

	/**
	 * Referencia ao setor onde o colaborador trabalha
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_setor")
	private Setor setor;

	private Integer jornadaSemanal;

	/**
	 * Referencia a lista de ferias do funcionario
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vinculo")
	private List<Ferias> ferias;

	public String getTipoVinculo() {
		return tipoVinculo;
	}

	public void setTipoVinculo(String tipoVinculo) {
		this.tipoVinculo = tipoVinculo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Integer getJornadaSemanal() {
		return jornadaSemanal;
	}

	public void setJornadaSemanal(Integer jornadaSemanal) {
		this.jornadaSemanal = jornadaSemanal;
	}

	public Boolean getStatusVinculo() {
		return statusVinculo;
	}

	public void setStatusVinculo(Boolean statusVinculo) {
		this.statusVinculo = statusVinculo;
	}

	public List<Ferias> getFerias() {
		return ferias;
	}

	public void setFerias(List<Ferias> listFerias) {
		this.ferias = listFerias;
	}
}
