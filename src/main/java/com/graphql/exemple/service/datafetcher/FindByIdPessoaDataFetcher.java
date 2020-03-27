package com.graphql.exemple.service.datafetcher;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.datafecher.GenericListDataFetcher;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

@Component
public class FindByIdPessoaDataFetcher extends GenericListDataFetcher<Pessoa, PessoaRepository, Pessoa> {

	public FindByIdPessoaDataFetcher(Class<Pessoa> x) {
		super(x);
	}
}
