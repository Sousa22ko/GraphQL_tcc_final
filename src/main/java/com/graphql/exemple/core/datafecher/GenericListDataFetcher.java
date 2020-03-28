package com.graphql.exemple.core.datafecher;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.graphql.exemple.core.GenericEntity;
import com.graphql.exemple.core.GenericRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@SuppressWarnings("rawtypes")
public class GenericListDataFetcher<T extends GenericEntity, R extends GenericRepository<T>, X>	implements DataFetcher<X> {

	protected Class typeParameter;

	@Autowired
	private R repository;

	public GenericListDataFetcher(Class x) {
		typeParameter = x;
	}

	@SuppressWarnings("unchecked")
	@Override
	public X get(DataFetchingEnvironment environment) {
		Object test = null;

		if (typeParameter.cast(test) instanceof ArrayList) {
			return (X) repository.findAll();
		} else {
			return (X) repository.findById(environment.getArgument("id"));
		}
	}
}
