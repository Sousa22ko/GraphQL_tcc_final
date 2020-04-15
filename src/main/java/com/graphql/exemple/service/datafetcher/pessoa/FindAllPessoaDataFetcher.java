package com.graphql.exemple.service.datafetcher.pessoa;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.GenericDataFetcher;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

@Component
public class FindAllPessoaDataFetcher extends GenericDataFetcher<Pessoa, PessoaRepository, ArrayList<Pessoa>> {

}
