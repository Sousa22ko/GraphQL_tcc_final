package com.graphql.exemple.service.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;

public class PessoaDataFetcher {

	@Autowired
	public FindAllPessoaDataFetcher fapdf;

	@Autowired
	public FindByIdPessoaDataFetcher fbipdf;

}
