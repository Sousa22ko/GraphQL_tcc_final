package com.graphql.exemple.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

public class GenericMutation<T extends GenericEntity, R extends GenericRepository<T>> implements GraphQLMutationResolver{

	@Autowired
	protected R repository;
	
}
