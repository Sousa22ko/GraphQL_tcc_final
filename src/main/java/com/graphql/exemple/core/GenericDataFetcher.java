package com.graphql.exemple.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graphql.exemple.util.Constant;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public abstract class GenericDataFetcher<T extends GenericEntity, R extends GenericRepository<T>, X> implements DataFetcher<X> {

	@Autowired
	private R repository;

	@Override
	@SuppressWarnings("unchecked")
	public X get(DataFetchingEnvironment environment) {

		String field = environment.getFields().get(0).getName();

		if (field.equals(Constant.findById)) {
			return (X) repository.findById(environment.getArgument("id")).get();
		} else if (field.equals(Constant.findAll)) {
			List<T> result = repository.findAll();
			Integer limit = environment.getArgument("limit");
			if (limit == null || limit > result.size())
				limit = result.size();
			if (limit < 0)
				limit = 0;

			return (X) result.subList(0, limit);
		} else if (customOperation() != null) {
			return customOperation();
		} else {
			System.err.println("tipo de query não suportada. revise o seu .graphql ou sua query");
			return null;
		}
	}

	protected X customOperation() {
		return null;
	};
}
