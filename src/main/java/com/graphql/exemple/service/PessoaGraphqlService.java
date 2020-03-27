package com.graphql.exemple.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.graphql.exemple.core.datafecher.GenericGraphQLService;
import com.graphql.exemple.service.datafetcher.PessoaDataFetcher;

import graphql.schema.idl.RuntimeWiring;

@Service
public class PessoaGraphqlService extends GenericGraphQLService<PessoaDataFetcher> {

	@Value("classpath:pessoa.graphql")
	Resource resource;

	@PostConstruct
	public void loadGraphQLSchema() throws IOException {
		File file = resource.getFile();
		register(file);
	}

	@Override
	public RuntimeWiring partBuilder(RuntimeWiring.Builder rt) {
		return rt.type("Query", typewiring -> typewiring.dataFetcher("findAll", dataFetcher.fapdf).dataFetcher("findById", dataFetcher.fbipdf)).build();
	}
}
