package com.graphql.exemple.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.GenericGraphqlResolver;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

@Component
public class Query extends GenericGraphqlResolver<Pessoa, PessoaRepository, List<Pessoa>>{

}
