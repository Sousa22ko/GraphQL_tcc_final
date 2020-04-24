package com.graphql.exemple.core;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql.exemple.util.Constant;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

/**
 * 
 * @author manom
 *
 * @param <T> Tipo da entidade trabalhada
 * @param <R> Repository da entidade T
 * @param <X> Tipo de retorno (List<T>)
 */
@SuppressWarnings("unchecked")
public abstract class GenericDataFetcher<T extends GenericEntity, R extends GenericRepository<T>, X>
		implements DataFetcher<X> {

	@Autowired
	protected R repository;

	private Integer page;

	private Integer size;

	private Integer id;

	private T obj;

	protected DataFetchingEnvironment environment;

	protected String operationType;

	@Override
	public X get(DataFetchingEnvironment environment) {

		operationType = environment.getFields().get(0).getName();

		setFields(environment);

		if (operationType.equals(Constant.COUNT)) {
			return count();

		} else if (operationType.equals(Constant.FIND_BY_ID)) {
			return findById();

		} else if (operationType.equals(Constant.FIND_ALL)) {
			return findAll();

		} else if (operationType.equals(Constant.SAVE)) {
			return save();

		} else {
			X operation = customOperation();

			if (operation != null)
				return operation;
			else
				throw new UnsupportedOperationException(
						"tipo de query não suportada. revise o seu .graphql ou sua query");
		}
	}

	/**
	 * Implemente para adicionar uma operação adicional ao datafetcher
	 * 
	 * @return
	 */
	protected X customOperation() {
		return null;
	};

	private X count() {
		List<Long> aux = new ArrayList<Long>();
		aux.add(this.repository.countAtivo());
		return (X) aux;
	}

	private X findById() {
		return (X) this.repository.findById(id).get();
	}

	private X findAll() {
		List<T> result = this.repository.findAll();
		paginationController(result.size());

		return (X) result.subList(0, size);
//		return (X) result.subList((page * size), (page * size) + size);
	}

	private X save() {
		List<T> response = new ArrayList<T>();

		response.add(this.repository.save(this.obj));
		return (X) response;
	}

	private void paginationController(Integer resultSize) {

		if (page != null) {

			if ((page + 1) * size > resultSize) {

				size = resultSize - (page * size);
				page++;

			} else if (resultSize > 10) {

				int lenght = resultSize / 10;

				if (lenght - 1 > page) {
					page = lenght - 1;
					size = resultSize % 10;
				}
				size = 10;
			}

		} else {
			page = 0;

			if (size == null || size > resultSize)
				size = resultSize;

			if (size < 0)
				size = 0;

		}
	}

	private void setFields(DataFetchingEnvironment environment) {

		this.environment = environment;

		this.id = environment.getArgument("id");
		this.size = environment.getArgument("size");
		this.page = environment.getArgument("page");
		this.obj = conversor(environment.getArgument("obj"));
	}

	private T conversor(LinkedHashMap<String, Object> input) {

		ObjectMapper mapper = new ObjectMapper();
		T result = mapper.convertValue(input, inferirTipoGenerico());

		return result;
	}

	public Class<T> inferirTipoGenerico() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
