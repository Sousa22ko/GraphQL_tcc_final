package com.graphql.exemple.core;

import java.io.File;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public abstract class GenericGraphQLService {// <T> {

//	@Autowired
//	protected T dataFetcher;

	protected GraphQL graphQL;

	public RuntimeWiring buildRunTimeWiring() {
		return partBuilder(RuntimeWiring.newRuntimeWiring());
	}

	public abstract RuntimeWiring partBuilder(RuntimeWiring.Builder rt);

	public GraphQL getGraphQL() {
		return this.graphQL;
	}

	protected void register(File file) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(file);

		RuntimeWiring wiring = buildRunTimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}
}
