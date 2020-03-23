package com.graphql.exemple.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.internal.LinkedTreeMap;
import com.graphql.exemple.APIQL.APIQueryParams;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.model.Setor;
import com.graphql.exemple.service.PessoaService;
import com.graphql.exemple.util.JSONProcessor;

@RestController
@RequestMapping("/rh/colaborador")
public class ColaboradorController extends GenericRestController<Pessoa, PessoaService> {

	@GetMapping("/{id}/setor")
	@ResponseBody
	public Setor findSetor(@PathVariable("id") Integer id) {
		return service.findSetor(id);
	}

	@GetMapping("/findBySetor/{id}")
	@ResponseBody
	public List<Pessoa> findBySetor(@PathVariable("id") Integer id) {
		if (id != null) {
			List<Pessoa> result = this.service.findBySetor(id);
			return result;
		} else
			return null;
	}

	/**
	 * 
	 * @param filter
	 * @param sort
	 * @param distinct
	 * @param page
	 * @param size
	 * @param field
	 * @param to          retorna a versão transfer object dos funcionários
	 * @param papelFilter habilita o filtro de funcionários do RH (chefe de setor
	 *                    lista apenas os funcionarios de seu setor, admin e chefe
	 *                    do SGP listam todos os funcionarios e usuarios comuns
	 *                    listam apenas eles mesmo) *sobrescreve o campo filter
	 *                    quando habilitado
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/findAll")
	public List<Object> findAll(String filter, String sort, Boolean distinct, Integer page, Integer size, String field,
			Boolean to, Boolean papelFilter) throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SecurityException, InstantiationException {

		APIQueryParams params = new APIQueryParams();
		if (filter != null && !filter.isEmpty()) {
			params.setFilter(JSONProcessor.toObject(filter, LinkedTreeMap.class));
		}

		params.setPage(page);
		params.setSize(size);
		params.setSort(sort);

		return this.service.findAll(params, to, papelFilter);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/count")
	public Object count(Boolean papelFilter, String filter) throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SecurityException, InstantiationException {

		APIQueryParams params = new APIQueryParams();
		if (filter != null && !filter.isEmpty()) {
			params.setFilter(JSONProcessor.toObject(filter, LinkedTreeMap.class));
		}

//		if (papelFilter) {
//			params = this.service.preFilter(new APIQueryParams());
//		}

		return this.service.count(params);
	}

}
