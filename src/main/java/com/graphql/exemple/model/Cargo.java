package com.graphql.exemple.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import com.graphql.exemple.core.GenericEntity;

/**
 *
 * Entidade que representa os cargos dos colaboradores do nuplam
 * 
 * @author Reinaldo Sousa
 *
 */
@Entity
@Table(name = "cargo")
@AttributeOverride(name = "id", column = @Column(name = "id_cargo"))
@Where(clause = "ativo = true")
public class Cargo extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Denominação do cargo
	 */
	@NotNull(message = "Denominação do Cargo vazia.")
	@NotBlank(message = "Denominação do Cargo vazia.")
	@Column(unique = true)
	private String denominacao;

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}
}
