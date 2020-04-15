package com.graphql.exemple.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Entidade representa uma parcela das ferias de um ano em especifico
 * 
 * @author Reinaldo Sousa
 */
@Entity
@Table(name = "ferias_parcela")
@AttributeOverride(name = "id", column = @Column(name = "id_ferias_parcela"))
@Where(clause = "ativo = true")
public class FeriasParcela extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * data de inicio da parcela das ferias
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@NotNull(message = "Data de Início da Parcela das férias vazio.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = DateFormat.DATE, timezone = DateFormat.TIMEZONE)
	private Date inicio;

	/**
	 * data de fim da parcela das ferias
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@NotNull(message = "Data de Término da Parcela das férias vazio.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = DateFormat.DATE, timezone = DateFormat.TIMEZONE)
	private Date fim;

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}
}
