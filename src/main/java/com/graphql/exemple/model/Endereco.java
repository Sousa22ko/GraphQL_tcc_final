package com.graphql.exemple.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entidade referencia o endereço de uma pessoa
 * 
 * @author Reinaldo Sousa
 *
 */
@Entity
@Table(name = "endereco", schema = "rh")
@AttributeOverride(name = "id", column = @Column(name = "id_endereco"))
public class Endereco extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Bairro
	 */
	@NotNull(message = "Bairro vazio.")
	private String bairro;

	/**
	 * Numero do endereço
	 */
	private String numero;

	/**
	 * Rua, avenida, travessa, ...
	 */
	@NotNull(message = "Logradouro vazio.")
	private String logradouro;

	/**
	 * Numero do cep
	 */
	@NotNull(message = "CEP vazio.")
	private String cep;

	/**
	 * Cidade
	 */
	@NotNull(message = "Cidade vazio.")
	private String cidade;

	/**
	 * Estado
	 */
	@NotNull(message = "Estado vazio.")
	private String estado;

	/**
	 * Pais
	 */
	@NotNull(message = "País vazio.")
	private String pais;

	/**
	 * Ponto de referencia
	 */
	private String pontoReferencia;

	/**
	 * Complemento do endereço
	 */
	private String complemento;

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
