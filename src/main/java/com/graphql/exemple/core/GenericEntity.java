package com.graphql.exemple.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graphql.exemple.util.DateHelper;

@MappedSuperclass
public abstract class GenericEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	@Column(nullable = false, updatable = false, name = "DATA_CRIACAO")
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCriacao = new Date();

	@LastModifiedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_MODIFICACAO")
	private Date dataModificacao;

	/**
	 * Atributo de controle para exclusão lógica TRUE = ativo FALSE = inativo
	 */
	@Column(nullable = false)
	private Boolean ativo = true;

	public GenericEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public Date getDataCriacao() {
		return dataCriacao;
	}

	@JsonIgnore
	public String getDataCriacaoFormatada() {
		return DateHelper.getDateAsDateString(dataCriacao);
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@JsonIgnore
	public Date getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GenericEntity other = (GenericEntity) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@JsonIgnore
	public Boolean getAtivo() {
		return ativo;
	}

	@JsonIgnore
	public Boolean isAtivo() {
		return ativo;
	}

	@JsonIgnore
	public String getEntityName() {
		return this.getClass().getSimpleName();
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
