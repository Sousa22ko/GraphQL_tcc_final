package com.graphql.exemple.datafetcher;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.graphql.exemple.core.GenericDataFetcher;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

@Service
@Transactional
public class PessoaDataFetcher extends GenericDataFetcher<Pessoa, PessoaRepository, List<Pessoa>> {

	@Override
	protected List<Pessoa> customOperation() {
		if (operationType.equals("findByName")) {
			return this.repository.findByName(environment.getArgument("nome"));
		} else
			return null;
	}
}
