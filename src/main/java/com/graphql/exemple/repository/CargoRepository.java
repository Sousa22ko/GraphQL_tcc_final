package com.graphql.exemple.repository;

import com.graphql.exemple.core.GenericRepository;
import com.graphql.exemple.model.Cargo;

/**
 * 
 * @author Reinaldo Sousa
 *
 */
public interface CargoRepository extends GenericRepository<Cargo> {

//	@Query(value = "SELECT c FROM Cargo c where c.denominacao = ?1 AND c.ativo = true")
//	public Cargo findByDenominacao(String denominacao);

}
