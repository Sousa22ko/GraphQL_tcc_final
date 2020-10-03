package com.graphql.exemple.core;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.graphql.exemple.model.scalar.DateScalar;
import com.graphql.exemple.util.TypeWiringHelper;

import graphql.GraphQL;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@SuppressWarnings("rawtypes")
public abstract class GenericResolver<DF extends GenericDataFetcher> {

	@Autowired
	protected DF dataFetcher;

	private GraphQL graphQL;

	protected URL resourcePath;

	protected GraphQLObjectType mutator;

	protected List<GraphQLObjectType> mutators = new ArrayList<GraphQLObjectType>();

	protected GraphQLSchema schema;
	protected GraphQLSchema schemaMutator;

	private GraphQLSchema.Builder builder;

	@PostConstruct
	private void init() throws IOException {
		TypeWiringHelper.datafetcher = dataFetcher;
		loadResource();
		preRegister();
//		setMutator();
		loadMutator();
		register();
	}

	protected abstract void loadResource() throws IOException;

	protected GraphQL getGraphQL() {
		return this.graphQL;
	}

	protected RuntimeWiring generateRuntimeWiring(RuntimeWiring.Builder builder) {
		try {
			return builder.type("Query", TypeWiringHelper.defaultTypeWiring()).build();
		} catch (Exception e) {
			System.err.println("erro ao criar classe service");
		}
		return null;
	}

	private void preRegister() throws IOException {

		TypeDefinitionRegistry typeRegistry = new SchemaParser()
				.parse(Resources.toString(resourcePath, Charsets.UTF_8));

		schema = new SchemaGenerator().makeExecutableSchema(typeRegistry,
				generateRuntimeWiring(RuntimeWiring.newRuntimeWiring().scalar(DateScalar.DATE)));
		builder = GraphQLSchema.newSchema(schema);
	}

	private void register() {
		schemaMutator = builder.build();
		graphQL = GraphQL.newGraphQL(schemaMutator).build();
	}

	/**
	 * sobrescreva para adicionar mutators
	 * 
	 * Crie um GraphQLObjectType e adicione a lista mutators para serem carregados
	 */
	protected void setMutator() {
	}

	private void loadMutator() {
		for (GraphQLObjectType aux : mutators) {
			builder = builder.mutation(aux);
		}
	}

	protected GraphQLSchema.Builder generateMutations() {
		return GraphQLSchema.newSchema(schema);
	}

	protected GraphQLObjectType.Builder newObject() {
		return GraphQLObjectType.newObject();
	}

	protected GraphQLFieldDefinition.Builder newField() {
		return GraphQLFieldDefinition.newFieldDefinition();
	}

	protected GraphQLInputObjectField.Builder newInputField() {
		return GraphQLInputObjectField.newInputObjectField();
	}

	protected GraphQLArgument.Builder newArgument() {
		return GraphQLArgument.newArgument();
	}
}
