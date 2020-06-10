package com.graphql.exemple.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphql.exemple.core.GenericController;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.resolver.PessoaResolver;

@RequestMapping
@RestController
public class MainController extends GenericController<Pessoa, PessoaResolver> {

}
