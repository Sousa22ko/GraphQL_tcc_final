package com.graphql.exemple.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.graphql.exemple.core.GenericGraphQLService;
import com.graphql.exemple.service.datafetcher.FindAllPessoaDataFetcher;
import com.graphql.exemple.service.datafetcher.FindByIdPessoaDataFetcher;

@Service
public class PessoaGraphQLService extends GenericGraphQLService<FindAllPessoaDataFetcher, FindByIdPessoaDataFetcher> {

	@Value("classpath:pessoa.graphql")
	Resource resource;

	@Override
	@PostConstruct
	protected void loadGraphQLSchema() throws IOException {
		File file = resource.getFile();
		register(file);
	}
}
