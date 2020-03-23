package com.graphql.exemple.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Entidade que define o setor
 * 
 * @author Reinaldo Sousa
 * @author Herick Melo
 * 
 */
@Entity
@Table(name = "Setor", schema = "rh")
@AttributeOverride(name = "id", column = @Column(name = "id_setor"))
public class Setor extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Numero do codigo do setor.
	 */
	private String codigo;

	/**
	 * Nome do setor
	 */
	@NotNull(message = "Denominação do Setor vazio.")
	@NotBlank(message = "Denominação do Setor vazio.")
	private String denominacao;

	/**
	 * Sigla do setor
	 */
	@NotNull(message = "Sigla do Setor vazia.")
	@NotBlank(message = "Sigla do Setor vazia.")
	@Column(unique = true)
	private String sigla;

	/**
	 * Setor responsável pelo setor atual.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Setor superSetor;

	public String getCodigo() {
		if (codigo != null && codigo.length() > 0)
			return codigo;
		else
			return superSetor.getCodigo();
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getSiglaDenominacao() {
		return sigla + " - " + denominacao;
	}

	public Setor getSuperSetor() {
		return superSetor;
	}

	public void setSuperSetor(Setor superSetor) {
		this.superSetor = superSetor;
	}
}
