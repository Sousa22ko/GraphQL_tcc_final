package com.graphql.exemple.core;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Integer> {

	@Override
	@Query(value = "select e from #{#entityName} e where e.ativo = true order by e.id asc")
	List<T> findAll();

	@Query(value = "select e from #{#entityName} e where e.id = ?1 and e.ativo = true")
	Optional<T> findById(Integer id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE #{#entityName} SET ativo=false where id = ?1")
	void deleteById(Integer id);

	default void softDelete(Integer id) {
		Optional<T> entity = findById(id);
		entity.get().setAtivo(false);
		save(entity.get());
	}

	@Override
	@Transactional
	@Modifying
	@Query(value = "UPDATE #{#entityName} e SET e.ativo=false where e = ?1")
	void delete(T entity);

	@Transactional
	@Modifying
	@Query(value = "delete from #{#entityName} where id = ?1")
	void hardDeleteById(Integer id);

	@Query(value = "select count(e) from #{#entityName} e where e.ativo = false")
	long countInactive();

	@Query(value = "select count(e) from #{#entityName} e where e.ativo = true")
	long countAtivo();
}
