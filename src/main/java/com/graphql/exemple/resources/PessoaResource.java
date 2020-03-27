package com.graphql.exemple.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphql.exemple.core.GenericResource;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.service.PessoaGraphQLService;

@RequestMapping("/rh")
@RestController
public class PessoaResource extends GenericResource<Pessoa, PessoaGraphQLService> {

}
