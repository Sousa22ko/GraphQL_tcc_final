package com.graphql.exemple.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graphql.exemple.service.PessoaGraphqlService;

import graphql.ExecutionResult;

@RequestMapping("/rh")
@RestController
public class PessoaResource {

	@Autowired
	PessoaGraphqlService service;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<Object> findAll(@RequestBody String query) {
		ExecutionResult result = service.getGraphQL().execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);

	}
}
