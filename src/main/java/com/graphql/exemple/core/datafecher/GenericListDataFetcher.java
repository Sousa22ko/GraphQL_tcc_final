package com.graphql.exemple.core.datafecher;

import org.springframework.beans.factory.annotation.Autowired;

import com.graphql.exemple.core.GenericEntity;
import com.graphql.exemple.core.GenericRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class GenericListDataFetcher<T extends GenericEntity, R extends GenericRepository<T>, X>
		implements DataFetcher<X> {

	@Autowired
	private R repository;

	@SuppressWarnings("unchecked")
	@Override
	public X get(DataFetchingEnvironment environment) {
		
		String field = environment.getFields().get(0).getName();

		if (field.equals("findById")) {
			return (X) repository.findById(environment.getArgument("id")).get();
		} else if(field.equals("findAll")){
			return (X) repository.findAll();
		}
		else {
			System.err.println("tipo de query não suportada. revise o seu .graphql ou sua query");
			return null;
		}
	}
}
