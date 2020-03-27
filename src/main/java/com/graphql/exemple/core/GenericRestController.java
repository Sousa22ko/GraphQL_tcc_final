package com.graphql.exemple.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.internal.LinkedTreeMap;
import com.graphql.exemple.APIQL.APIQueryParams;
import com.graphql.exemple.APIQL.ObjectToJPQL;
import com.graphql.exemple.exception.NegocioException;
import com.graphql.exemple.util.JSONProcessor;

/**
 * Controller genérico usado para requisições que seguem o padrão REST.
 * 
 * @author Mateus Antonio, Talison Costa.
 *
 * @param <T> Entidade que o controller gerencia.
 * @param <S> Serviço que o controller usa para armazenar os dados.
 */
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS,
		RequestMethod.DELETE })
public class GenericRestController<T extends GenericEntity, S extends GenericService> {

	@Autowired
	protected S service;

	ObjectToJPQL objectToJPQL = new ObjectToJPQL();

	/**
	 * Retorna todos os elementos da entidade.
	 * 
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@GetMapping
	@SuppressWarnings("unchecked")
	public List<T> findAll(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "distinct", required = false) Boolean distinct,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "field", required = false) String field)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			SecurityException, InstantiationException {

		APIQueryParams params = new APIQueryParams();
		if (filter != null && !filter.isEmpty()) {
			params.setFilter(JSONProcessor.toObject(filter, LinkedTreeMap.class));
		}
		params.setPage(page);
		params.setSize(size);
		params.setSort(sort);

		List<String> fieldList = null;
		if (field != null) {
			if (field.equalsIgnoreCase("null") || field.isEmpty()) {
				field = null;
			} else {
				field = field.replaceAll("[{}]", "");
				fieldList = new ArrayList<String>(Arrays.asList(field.split(",")));
				fieldList = fieldList.stream().map(f -> f.trim()).collect(Collectors.toList());
			}
		}

		return field != null ? service.extractAttribute(service.findAll(params), fieldList) : service.findAll(params);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/qtn")
	public Object count(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "ativo", required = false) Boolean ativo)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			SecurityException, InstantiationException {

		APIQueryParams params = new APIQueryParams();

		if (filter != null && !filter.isEmpty()) {
			params.setFilter(JSONProcessor.toObject(filter, LinkedTreeMap.class));
		}

		if (ativo != null)
			service.setAtivo(ativo);

		return service.count(params);
	}

	/**
	 * Retorna o elemento representado pelo ID informado.
	 * 
	 * @param id ID do elemento a ser encontrado.
	 */
	@GetMapping("/{id}")
	@SuppressWarnings("unchecked")
	public ResponseEntity<T> findById(@PathVariable Integer id,
			@RequestParam(value = "ativo", required = false) Boolean ativo) {
		if (ativo != null)
			service.setAtivo(ativo);
		Optional<T> entity = service.findById(id);
		if (!entity.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(entity.get());
	}

	/**
	 * Realiza a persistência de uma entidade. Retorna a entidade após salva no
	 * banco (já com um ID).
	 * 
	 * @param entity Entidade a ser salva.
	 */
	@SuppressWarnings("unchecked")
	@PostMapping
	public @ResponseBody ResponseEntity<Object> save(@Valid @RequestBody T entity) throws NegocioException {
		T result = (T) service.save(entity);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	/**
	 * Altera uma entidade já existente no banco.
	 * 
	 * @param entity Entidade a ser alterada. Deve conter um ID.
	 * @throws NegocioException
	 */
	@SuppressWarnings("unchecked")
	@PutMapping
	public @ResponseBody ResponseEntity<Object> update(@Valid @RequestBody T entity) throws NegocioException {
		T result = (T) service.update(entity);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	/**
	 * Remove a entidade correspondente ao ID informado.
	 * 
	 * @param id ID da entidade a ser removida.
	 * @throws NegocioException
	 */
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Integer id) throws NegocioException {
		service.softDelete(id);
	}

	protected Class<T> inferirTipoGenerico() {
		@SuppressWarnings("unchecked")
		Class<T> class1 = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return class1;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/{id}/restory")
	public T restory(@PathVariable("id") Integer id) throws NegocioException {
		T entity = (T) service.findById(id).get();
		entity.setAtivo(true);
		return (T) service.save(entity);
	}
}
