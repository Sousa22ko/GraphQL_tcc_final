package com.graphql.exemple.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.graphql.exemple.APIQL.APIQueryParams;
import com.graphql.exemple.APIQL.ObjectToJPQL;
import com.graphql.exemple.util.LoggerHelper;

/**
 * Abstract service para persistências padrão.
 * 
 * @author itamir, Mateus Antonio, Talison F.
 *
 * @param <T> Modelo da entidade.
 */
public class GenericService<T extends GenericEntity, R extends GenericRepository<T>> {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	protected R repository;

	@Autowired
	EntityManager em;

	@Autowired
	protected LoggerHelper loggerHelper;

	Boolean ativo = true;

	/**
	 * Metodo para implementar regras de negocio relacionado a validacao
	 */
	public boolean validate(T entity) {
		return true;
	}

	/**
	 * Método para implementar regras de negócio relacionadas à validacao da remoção
	 */
	public boolean validateDelete(T entity) {
		return true;
	}

	/**
	 * Realiza um pré-processamento antes de salvar ou atualizar a entidade
	 *
	 * @param entity @
	 */
	public void preSaveOrUpdate(T entity) {

	}

	/**
	 * Realiza um pré-processamento antes de salvar a entidade
	 *
	 * @param entity @
	 */
	public void preSave(T entity) {

	}

	/**
	 * Realiza um pré-processamento antes de atualizar a entidade
	 *
	 * @param entity @
	 */
	public void preUpdate(T entity) {

	}

	/**
	 * Persiste uma entidade no banco de dados.
	 * 
	 * @param obj Objeto a ser persistido. @
	 */
	public T save(T obj) {
		this.preSaveOrUpdate(obj);
		this.preSave(obj);
		this.validate(obj);
		T entity = repository.save(obj);
		loggerHelper.generateLog(this.getClass(), "SALVAR", inferirTipoGenericoNome(), obj.getId());
		return entity;
	}

	/**
	 * Altera uma entidade já existente no banco de dados.
	 * 
	 * @param obj Objeto a ser alterado. @
	 */
	public T update(T obj) {
		this.preSaveOrUpdate(obj);
		this.preUpdate(obj);
		this.validate(obj);

		T entity = repository.save(obj);
		loggerHelper.generateLog(this.getClass(), "ALTERAR", inferirTipoGenericoNome(), obj.getId());
		return entity;
	}

	/**
	 * Remove uma entidade no banco de dados.
	 * 
	 * @param obj Objeto a ser removido. @
	 */
	public void delete(T obj) {
		loggerHelper.generateLog(this.getClass(), "REMOVER", inferirTipoGenericoNome(), obj.getId());
		this.validateDelete(obj);
		repository.delete(obj);
	}

	/**
	 * Remove uma entidade no banco de dados a partir do seu ID.
	 * 
	 * @param id ID da entidade a ser removida. @
	 */
	public void deleteById(Integer id) {
		loggerHelper.generateLog(this.getClass(), "REMOVER", inferirTipoGenericoNome(), id);
		this.validateDelete(repository.findById(id).get());
		repository.deleteById(id);
	}

	public void softDelete(Integer id) {
		this.validateDelete(repository.findById(id).get());
		repository.softDelete(id);
	}

	/**
	 * Remove uma entidade no banco de dados a partir do seu ID. Usado em casos de
	 * teste
	 * 
	 * @param id ID da entidade a ser removida. @
	 */
	public void hardDeleteById(Integer id) {
		this.validateDelete(repository.findById(id).get());
		repository.hardDeleteById(id);
	}

	/**
	 * Encontra uma entidade no banco de dados a partir do seu ID.
	 * 
	 * @param id ID da entidade a ser procurada.
	 * @return Objeto referente a entidade.
	 */
	public Optional<T> findById(Integer id) {
		if (ativo) {
			loggerHelper.generateLog(this.getClass(), "BUSCAR", inferirTipoGenericoNome(), id);
			return repository.findById(id);
		} else {
			loggerHelper.generateLog(this.getClass(), "BUSCAR", inferirTipoGenericoNome(), id);
			Optional<T> result = repository.findInactiveById(id);
			ativo = true;
			return result;
		}
	}

	/**
	 * Encontra todos os objetos no banco de dados referentes a uma entidade.
	 * 
	 * @return Lista contendo todas as entidades.
	 */
	public List<T> findAll() {
		if (ativo) {
			loggerHelper.generateLog(this.getClass(), "LISTAR", inferirTipoGenericoNome());
			return repository.findAll();
		} else {
			loggerHelper.generateLog(this.getClass(), "LISTAR INATIVOS", inferirTipoGenericoNome());
			List<T> result = repository.findInactiveAll();
			ativo = true;
			return result;
		}
	}

	public Page<T> findAll(Example<T> example, Pageable pageable) {
		loggerHelper.generateLog(this.getClass(), "LISTAR (example, pageable)", inferirTipoGenericoNome());
		return repository.findAll(example, pageable);
	}

	public Page<T> findAll(Pageable pageable) {
		if (ativo) {
			loggerHelper.generateLog(this.getClass(), "LISTAR (pageable)", inferirTipoGenericoNome());
			return repository.findAll(pageable);
		} else {
			loggerHelper.generateLog(this.getClass(), "LISTAR INATIVO (pageable)", inferirTipoGenericoNome());
			Page<T> page = repository.findInactiveAll(pageable);
			ativo = true;
			return page;
		}
	}

	public List<T> findByExample(Example<T> example) {
		loggerHelper.generateLog(this.getClass(), "LISTAR (example)", inferirTipoGenericoNome());
		return repository.findAll(example);
	}

	/**
	 * Encontra o campo requisitado de todos os objetos do banco contidos na page.
	 * 
	 * @param pageable pagina
	 * @param field    campo a ser retornado
	 * @return campo requisitado dos objetos
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Page<Object> findAll(Pageable pageable, String field) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		loggerHelper.generateLog(this.getClass(), "LISTAR (pageable, field)", inferirTipoGenericoNome());
		Page<T> pageObj = repository.findAll(pageable);
		Method m = inferirTipoGenerico().getMethod("get" + StringUtils.capitalize(field));
		List<Object> arrayObj = new ArrayList<Object>();
		for (T obj : pageObj) {
			arrayObj.add(m.invoke(obj));
		}
		return new PageImpl<>(arrayObj);
	}

	/**
	 * Encontra o campo requisitado de todos os objetos do banco contidos na page
	 * que respeitam o filtro inserido
	 * 
	 * @param example  filtro
	 * @param pageable pagina
	 * @param field    campo a ser retornado
	 * @return Campo requisitado dos objetos filtrados
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Page<Object> findAll(Example<T> example, Pageable pageable, String field) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		loggerHelper.generateLog(this.getClass(), "LISTAR (example, pageable, field)", inferirTipoGenericoNome());
		Page<T> pageObj = repository.findAll(example, pageable);
		Method m = inferirTipoGenerico().getMethod("get" + StringUtils.capitalize(field));
		List<Object> arrayObj = new ArrayList<Object>();
		for (T obj : pageObj) {
			arrayObj.add(m.invoke(obj));
		}
		return new PageImpl<>(arrayObj);
	}

	/**
	 * Conta quantas entradas no banco de dados ainda estão ativas (em uso).
	 * 
	 * @return Número de entidades ativas.
	 */
	public long countAtivo() {
		return repository.countAtivo();
	}

	/**
	 * Consulta todos os registros existentes para um determinado modelo
	 * 
	 * @param field Nome do campo
	 * @param value Valor procurado
	 * @return Quantidade de Registros encontrados
	 */
	public Long countValue(Integer idexclude, String field, String value) {
		return this.countValue(this.inferirTipoGenerico(), idexclude, field, value);
	}

	/**
	 * Consulta todos os registros existentes para um determinado modelo
	 * 
	 * @param classz Classe do modelo que vai ser verificado
	 * @param field  Nome do campo
	 * @param value  Valor procurado
	 * @return Quantidade de Registros encontrados
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long countValue(Class classz, Integer idexclude, String field, String value) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		query.select(builder.count(query.from(classz)));
		// query.distinct(true);

		Root<T> root = query.from(classz);
		// Selecionar o field até mesmo de objeto
		Path<T> path = root;
		for (String part : field.split("\\.")) {
			path = path.get(part);
		}

		Predicate predicate = builder.and();
		predicate = builder.and(predicate, builder.equal(path, value));

		if (idexclude != null && idexclude != 0)
			predicate = builder.and(predicate, builder.notEqual(root.get("id"), idexclude));

		query.where(predicate);
		TypedQuery<Long> result = entityManager.createQuery(query);
		return result.getSingleResult();
	}

	private String inferirTipoGenericoNome() {
		return inferirTipoGenerico().getSimpleName();
	}

	@SuppressWarnings("unchecked")
	private Class<T> inferirTipoGenerico() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public List<T> findAll(APIQueryParams params) {
		ObjectToJPQL objectToJPQL = new ObjectToJPQL();
		if (params.getSort() == null)
			params.setSort("dataCriacao desc");

		// Adicionar filtro de ativo true
		if (params.getFilter() != null) {
			params.getFilter().put("ativo", ativo);
		} else {
			LinkedTreeMap<String, Object> filter = new LinkedTreeMap<String, Object>();
			filter.put("ativo", true);
			params.setFilter(filter);
		}

		params.setEntityName(inferirTipoGenericoNome());
		String jpql = objectToJPQL.build(params);

		loggerHelper.generateLog(this.getClass(), String.format("LISTAR {filter %s; page: %d, size: %d}",
				params.getFilter(), params.getPage(), params.getSize()), inferirTipoGenericoNome());

		Query query = objectToJPQL.execute(em);

		if (params.getSize() != null)
			query.setMaxResults(params.getSize());
		query.setFirstResult(params.getPage() != null ? params.getPage() * params.getSize() : 0);

		return query.getResultList();
	}

	@SuppressWarnings("unused")
	public Object count(APIQueryParams params) {
		ObjectToJPQL objectToJPQL = new ObjectToJPQL();
		params.setEntityName(inferirTipoGenericoNome());
		String jpql = objectToJPQL.build(params);

		loggerHelper.generateLog(this.getClass(), String.format("COUNT {filter %s; page: %d, size: %d}",
				params.getFilter(), params.getPage(), params.getSize()), inferirTipoGenericoNome());

		Query query = objectToJPQL.count(em);

		return query.getSingleResult();
	}

	public List<T> extractAttribute(List<T> collection, List<String> attrsNames)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		List<T> result = new ArrayList<T>();

		for (Object item : collection) {
			T obj = inferirTipoGenerico().getDeclaredConstructor().newInstance();
			Method getId = inferirTipoGenerico().getMethod("getId");

			obj.setId((Integer) getId.invoke(item));

			for (String attrName : attrsNames) {
				Method m = inferirTipoGenerico().getMethod("get" + StringUtils.capitalize(attrName));
				set(obj, attrName, m.invoke(item));
			}

			result.add(obj);
		}

		return result;
	}

	private boolean set(T obj, String attrName, Object attrValue) {
		Class<T> objClass = inferirTipoGenerico();
		if (objClass != null) {
			Field field;
			try {
				field = objClass.getDeclaredField(attrName);
				field.setAccessible(true);
				field.set(obj, attrValue);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}