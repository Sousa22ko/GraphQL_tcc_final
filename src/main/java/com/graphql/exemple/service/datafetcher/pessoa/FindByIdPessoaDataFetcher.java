package com.graphql.exemple.service.datafetcher.pessoa;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.GenericDataFetcher;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

@Component
public class FindByIdPessoaDataFetcher extends GenericDataFetcher<Pessoa, PessoaRepository, Pessoa> {

}
