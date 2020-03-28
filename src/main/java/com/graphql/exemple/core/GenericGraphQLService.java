package com.graphql.exemple.core;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.graphql.exemple.core.datafecher.GenericListDataFetcher;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@SuppressWarnings("rawtypes")
public abstract class GenericGraphQLService<A extends GenericListDataFetcher, O extends GenericListDataFetcher> {
	
	@Autowired
	protected A findList;
	
	@Autowired
	protected O findOne;

	protected GraphQL graphQL;

	private RuntimeWiring generateRuntimeWiring(RuntimeWiring.Builder builder) {
		return builder.type("Query", typewiring -> typewiring.dataFetcher("findAll", findList).dataFetcher("findById", findOne)).build();
	}

	@PostConstruct
	protected abstract void loadGraphQLSchema() throws IOException;

	protected GraphQL getGraphQL() {
		return this.graphQL;
	}

	protected void register(File file) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(file);

		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, generateRuntimeWiring(RuntimeWiring.newRuntimeWiring()));
		graphQL = GraphQL.newGraphQL(schema).build();
	}
}
