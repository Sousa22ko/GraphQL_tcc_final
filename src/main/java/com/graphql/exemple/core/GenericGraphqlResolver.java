package com.graphql.exemple.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@SuppressWarnings("unchecked")
public class GenericGraphqlResolver<T extends GenericEntity, R extends GenericRepository<T>, X>
		implements GraphQLQueryResolver {

	@Autowired
	protected R repository;

	protected X findAll() {
		System.err.println("To aqui");
		return (X) this.repository.findAll();
	}

}
