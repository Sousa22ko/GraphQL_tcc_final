package com.graphql.exemple.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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

	protected DataFetchingEnvironment environment;

	protected String field;

	@Override
	public X get(DataFetchingEnvironment environment) {

		field = environment.getFields().get(0).getName();

		setFields(environment);

		if (field.equals(Constant.count)) {
			return count();

		} else if (field.equals(Constant.findById)) {
			return findById();

		} else if (field.equals(Constant.findAll)) {
			return findAll();

		} else {
			X operation = customOperation();
			
			// operation != null ? return operation : throw new UnsupportedOperationException("tipo de query não suportada. revise o seu .graphql ou sua query");
			
			if (operation != null)
				return operation;
			else
				throw new UnsupportedOperationException("tipo de query não suportada. revise o seu .graphql ou sua query");
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
	}

}
