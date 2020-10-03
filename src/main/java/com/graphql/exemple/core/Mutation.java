package com.graphql.exemple.core;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.model.input.PessoaInput;

@Component
public class Mutation implements GraphQLMutationResolver {

	Pessoa save(PessoaInput obj) {
		return new Pessoa("teste", "123.654.789-88", "email@wqeqweq.teste");
	}
}
