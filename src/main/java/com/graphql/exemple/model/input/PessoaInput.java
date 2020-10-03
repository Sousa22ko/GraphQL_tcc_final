package com.graphql.exemple.model.input;

import com.graphql.exemple.model.Pessoa;

public class PessoaInput {
	
	private String nome;
	
	private String cpf;
	
	private String email;
	
	private Boolean statusCadastro = true;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getStatusCadastro() {
		return statusCadastro;
	}

	public void setStatusCadastro(Boolean statusCadastro) {
		this.statusCadastro = statusCadastro;
	}
	
	public Pessoa toPessoa() {
		Pessoa p = new Pessoa();
		p.setNome(this.getNome());
		p.setCpf(this.getCpf());
		p.setEmail(this.getEmail());
		p.setStatusCadastro(true);
		return p;
	}

}
