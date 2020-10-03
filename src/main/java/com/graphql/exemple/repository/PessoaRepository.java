package com.graphql.exemple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.graphql.exemple.core.GenericRepository;
import com.graphql.exemple.model.Pessoa;

/**
 * 
 * @author Reinaldo Sousa
 *
 */
public interface PessoaRepository extends GenericRepository<Pessoa> {

	@Query(value = "select * from pessoa p where p.nome like %?1% ", nativeQuery = true)
	public List<Pessoa> findByName(String nome);

	
	public default Pessoa save(Pessoa p) {
		Pessoa pr = new Pessoa("teste", "123.654.789-77", "ema@il.com");
		return pr;
	}
}
