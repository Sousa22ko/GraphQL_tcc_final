package br.ufrn.imd.signuplam.rh.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.ufrn.imd.imdevn.model.Pessoa;
import br.ufrn.imd.imdevn.util.CustomDateDeserializer;
import br.ufrn.imd.imdevn.util.DateFormat;

/**
 * Entidade representa uma pessoa fisica no sistema
 * 
 * @author Reinaldo Sousa
 * 
 */
@Entity
@Audited
@Table(name = "pessoa_fisica", schema = "rh")
@PrimaryKeyJoinColumn(name = "id_pessoa", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
public class PessoaFisica extends Pessoa {

	private static final long serialVersionUID = 1L;

	/**
	 * Situação da pessoa em relação ao matrimonio
	 */
	private String estadoCivil;

	/**
	 * Data de nascimento da pessoa
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = DateFormat.DATE, timezone = DateFormat.TIMEZONE)
	private Date dataNascimento;

	/**
	 * CPF da pessoa
	 */
	@CPF
	@NotNull(message = "CPF da Pessoa vazio.")
	@Column(unique = true)
	private String cpf;

	/**
	 * Número do RG da pessoa física
	 */
	private String rg;

	/**
	 * Orgão que emitiu o documento de RG
	 */
	@Column(name = "orgao_emissor_rg")
	private String orgaoEmissorRG;

	/**
	 * Data de emissão do RG
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@Column(name = "data_emissao_rg")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = DateFormat.DATE, timezone = DateFormat.TIMEZONE)
	private Date dataEmissaoRG;

	/**
	 * Sexo da pessoa I = Indefinido - pessoa jurídica M = Masculino F = Feminino
	 * 
	 */
	private String sexo;

	/**
	 * Especifica a cidade de onde a pessoa nasceu
	 */
	private String naturalidade;

	/**
	 * Especifica O pais onde a pessoa nasceu
	 */
	private String nacionalidade;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = InformacoesAdicionais.class)
	@JoinColumn(name = "id_informacoes_adicionais", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private InformacoesAdicionais infoAdicional;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getOrgaoEmissorRG() {
		return orgaoEmissorRG;
	}

	public void setOrgaoEmissorRG(String orgaoEmissorRG) {
		this.orgaoEmissorRG = orgaoEmissorRG;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Date getDataEmissaoRG() {
		return dataEmissaoRG;
	}

	public void setDataEmissaoRG(Date dataEmissaoRG) {
		this.dataEmissaoRG = dataEmissaoRG;
	}

	public InformacoesAdicionais getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(InformacoesAdicionais infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataEmissaoRG == null) ? 0 : dataEmissaoRG.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((estadoCivil == null) ? 0 : estadoCivil.hashCode());
		result = prime * result + ((infoAdicional == null) ? 0 : infoAdicional.hashCode());
		result = prime * result + ((nacionalidade == null) ? 0 : nacionalidade.hashCode());
		result = prime * result + ((naturalidade == null) ? 0 : naturalidade.hashCode());
		result = prime * result + ((orgaoEmissorRG == null) ? 0 : orgaoEmissorRG.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
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
		PessoaFisica other = (PessoaFisica) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataEmissaoRG == null) {
			if (other.dataEmissaoRG != null)
				return false;
		} else if (!dataEmissaoRG.equals(other.dataEmissaoRG))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (estadoCivil == null) {
			if (other.estadoCivil != null)
				return false;
		} else if (!estadoCivil.equals(other.estadoCivil))
			return false;
		if (infoAdicional == null) {
			if (other.infoAdicional != null)
				return false;
		} else if (!infoAdicional.equals(other.infoAdicional))
			return false;
		if (nacionalidade == null) {
			if (other.nacionalidade != null)
				return false;
		} else if (!nacionalidade.equals(other.nacionalidade))
			return false;
		if (naturalidade == null) {
			if (other.naturalidade != null)
				return false;
		} else if (!naturalidade.equals(other.naturalidade))
			return false;
		if (orgaoEmissorRG == null) {
			if (other.orgaoEmissorRG != null)
				return false;
		} else if (!orgaoEmissorRG.equals(other.orgaoEmissorRG))
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		return true;
	}
	
}
