package com.graphql.exemple.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.common.io.Resources;
import com.graphql.exemple.core.GenericGraphQLService;
import com.graphql.exemple.datafetcher.PessoaDataFetcher;
import com.graphql.exemple.util.TypeWiringHelper;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectType;
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

	@Override
	protected void setMutator() {

		GraphQLInputObjectType in = GraphQLInputObjectType.newInputObject().name("PessoaInput")
				.field(newInputField().name("nome").type(Scalars.GraphQLString).build())
				.field(newInputField().name("cpf").type(Scalars.GraphQLString).build())
				.field(newInputField().name("email").type(Scalars.GraphQLString).build()).build();

		this.mutator = newObject().name("SavePessoa")
				.field(newField().name("save").argument(newArgument().name("obj").type(in).build())
						.type(schema.getObjectType("Pessoa")).dataFetcher(dataFetcher).build())
				.build();
	}
}
