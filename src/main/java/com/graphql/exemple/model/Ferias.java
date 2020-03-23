package com.graphql.exemple.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

/**
 * A entidade representa as ferias de um ano em especifico de um colaborador
 * 
 * @author Reinaldo Sousa
 */
@Entity
@Table(name = "ferias", schema = "rh")
@AttributeOverride(name = "id", column = @Column(name = "id_ferias"))
@Where(clause = "ativo = true")
public class Ferias extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Ano ao qual as ferias prtencem
	 */
	@NotNull(message = "Ano de Referência das Férias vazio.")
	private String anoReferencia;

	/**
	 * Status das ferias. Exs.: Marcada, Paga, Não Marcada
	 */
	@NotNull(message = "Status das Férias vazio.")
	private String status;

	/**
	 * Referencia para as parcelas das ferias
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ferias")
	private List<FeriasParcela> listParcelas;

	public Ferias() {
		listParcelas = new ArrayList<FeriasParcela>();
	}

	public String getAnoReferencia() {
		return anoReferencia;
	}

	public void setAnoReferencia(String anoReferencia) {
		this.anoReferencia = anoReferencia;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FeriasParcela> getListParcelas() {
		return listParcelas;
	}

	public void setListParcelas(List<FeriasParcela> listParcelas) {
		this.listParcelas = listParcelas;
	}
}
