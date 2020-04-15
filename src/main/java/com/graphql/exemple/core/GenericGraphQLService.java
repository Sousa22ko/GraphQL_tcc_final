package com.graphql.exemple.core;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.graphql.exemple.util.TypeWiringHelper;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@SuppressWarnings("rawtypes")
public abstract class GenericGraphQLService<ALL extends GenericDataFetcher, ONE extends GenericDataFetcher> {

	@Autowired
	private ALL findList;

	@Autowired
	private ONE findOne;

	private GraphQL graphQL;

	protected URL resourcePath;

	@PostConstruct
	private void init() throws IOException {
		TypeWiringHelper.one = findOne;
		TypeWiringHelper.list = findList;
		loadResource();
		register();
	}
	
	protected abstract void loadResource() throws IOException;
	
	protected GraphQL getGraphQL() {
		return this.graphQL;
	}
	
	private RuntimeWiring generateRuntimeWiring(RuntimeWiring.Builder builder) {
		try {
			return builder.type("Query", TypeWiringHelper.defaultTypeWiring()).build();
		} catch (Exception e) {
			System.err.println("erro ao criar classe service");
		}
		return null;
	}
	
	private void register() throws IOException {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(Resources.toString(resourcePath, Charsets.UTF_8));

		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry,	generateRuntimeWiring(RuntimeWiring.newRuntimeWiring()));
		graphQL = GraphQL.newGraphQL(schema).build();
	}
}
