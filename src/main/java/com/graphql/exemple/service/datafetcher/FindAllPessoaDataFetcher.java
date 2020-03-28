package com.graphql.exemple.service.datafetcher;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.datafecher.GenericListDataFetcher;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

@Component
public class FindAllPessoaDataFetcher extends GenericListDataFetcher<Pessoa, PessoaRepository, ArrayList<Pessoa>> {

}
