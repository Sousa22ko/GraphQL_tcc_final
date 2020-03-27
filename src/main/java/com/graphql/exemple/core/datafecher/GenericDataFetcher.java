package com.graphql.exemple.core.datafecher;

import org.springframework.beans.factory.annotation.Autowired;

import com.graphql.exemple.model.GenericEntity;
import com.graphql.exemple.repository.GenericRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class GenericDataFetcher<T extends GenericEntity, R extends GenericRepository<T>> implements DataFetcher<T> {

	@Autowired
	private R repository;

	@Override
	public T get(DataFetchingEnvironment environment) {
		return repository.findById(environment.getArgument("id")).get();
	}

}
