package com.graphql.exemple.service.datafetcher.pessoa;

import java.util.List;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.GenericDataFetcher;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

@Component
public class PessoaDataFetcher extends GenericDataFetcher<Pessoa, PessoaRepository, List<Pessoa>> {

	@Override
	protected List<Pessoa> customOperation() {
		if (field.equals("findByName")) {
			return this.repository.findByName(environment.getArgument("nome"));
		} else
			return null;
	}
}
