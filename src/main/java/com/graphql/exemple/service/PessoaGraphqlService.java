package com.graphql.exemple.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.common.io.Resources;
import com.graphql.exemple.core.GenericGraphQLService;
import com.graphql.exemple.service.datafetcher.pessoa.PessoaDataFetcher;
import com.graphql.exemple.util.TypeWiringHelper;

import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.RuntimeWiring.Builder;

@Service
public class PessoaGraphQLService extends GenericGraphQLService<PessoaDataFetcher> {

	@Override
	protected void loadResource() throws IOException {
		resourcePath = Resources.getResource("pessoa.graphql");
	}

	@Override
	protected RuntimeWiring generateRuntimeWiring(Builder builder) {
		return builder.type("Query", TypeWiringHelper.customTipewiring("findByName", this.dataFetcher)).build();
	}

}
