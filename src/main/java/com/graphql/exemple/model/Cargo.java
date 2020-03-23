package com.graphql.exemple.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import br.ufrn.imd.imdevn.core.model.GenericEntity;

/**
 *
 * Entidade que representa os cargos dos colaboradores do nuplam
 * 
 * @author Reinaldo Sousa
 *
 */
@Entity
@Audited
@Table(name = "cargo", schema = "rh")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((denominacao == null) ? 0 : denominacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cargo other = (Cargo) obj;
		if (denominacao == null) {
			if (other.denominacao != null)
				return false;
		} else if (!denominacao.equals(other.denominacao))
			return false;
		return true;
	}
}
