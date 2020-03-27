package com.graphql.exemple.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import graphql.ExecutionResult;

public class GenericResource<T extends GenericEntity, S extends GenericGraphQLService> {

	@Autowired
	protected S service;

	@GetMapping
	public ResponseEntity<Object> findAll(@RequestBody String query) {
		ExecutionResult result = service.getGraphQL().execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);

	}
}
