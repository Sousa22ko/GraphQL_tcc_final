package com.graphql.exemple.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.common.io.Resources;
import com.graphql.exemple.core.GenericGraphQLService;
import com.graphql.exemple.service.datafetcher.pessoa.FindAllPessoaDataFetcher;
import com.graphql.exemple.service.datafetcher.pessoa.FindByIdPessoaDataFetcher;

@Service
public class PessoaGraphQLService extends GenericGraphQLService<FindAllPessoaDataFetcher, FindByIdPessoaDataFetcher> {

	@Override
	protected void loadResource() throws IOException {
		resourcePath = Resources.getResource("pessoa.graphql");
	}
}
