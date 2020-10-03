package com.graphql.exemple.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.repository.PessoaRepository;

//@Component
public class Query {//implements GraphQLQueryResolver {

	@Autowired
	PessoaRepository pessoaRep;

	List<Pessoa> findAll(Integer size) {
		return pessoaRep.findAll();
	}

	Pessoa findById(Integer id) {
		return pessoaRep.findById(id).get();
	}

	List<Pessoa> findByName(String nome) {
		return pessoaRep.findByName(nome);
	}

//	List<Integer> count() {
//		List<Integer> resp = new ArrayList<Integer>();
//		Long l = pessoaRep.count();
//		resp.add(l.intValue());
//
//		return resp;
//	}

	List<Integer> count() {
		List<Integer> resp = new ArrayList<Integer>();
		resp.add(1);
		return resp;
	}
}
