package com.graphql.exemple.core;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Integer> {

	@Query(value = "select usuario from public.trilha_auditoria where id = ?1", nativeQuery = true)
	String findUsuarioByRev(Integer rev);

	@Override
	@Query(value = "select e from #{#entityName} e where e.ativo = true order by e.id asc")
	List<T> findAll();

	@Override
	@Query(value = "select e from #{#entityName} e where e.ativo = true")
	Page<T> findAll(Pageable pageable);

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

	default void deleteAll(Iterable<? extends T> arg0) {
		arg0.forEach(entity -> {
			deleteById(entity.getId());
		});
	}

	@Transactional
	@Modifying
	@Query(value = "delete from #{#entityName} where id = ?1")
	void hardDeleteById(Integer id);

	@Query(value = "select count(e) from #{#entityName} e where e.ativo = true")
	long countAtivo();

	/**
	 * Deleted entities
	 */

	@Query(value = "select e from #{#entityName} e where e.ativo = false order by e.id asc")
	List<T> findInactiveAll();

	@Query(value = "select e from #{#entityName} e where e.ativo = false")
	Page<T> findInactiveAll(Pageable pageable);

	@Query(value = "select e from #{#entityName} e where e.id = ?1 and e.ativo = false")
	Optional<T> findInactiveById(Integer id);

	@Query(value = "select count(e) from #{#entityName} e where e.ativo = false")
	long countInactive();

}
