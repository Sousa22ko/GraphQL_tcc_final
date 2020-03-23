package com.graphql.exemple.APIQL;

import com.google.gson.internal.LinkedTreeMap;
import com.graphql.exemple.APIQL.type.MethodType;

public class APIQueryParams implements Cloneable {

	private LinkedTreeMap<String, Object> filter;
	private Integer page;
	private Integer size;
	private String sort;
	private Boolean distinct;
	private MethodType[] methods;
	private String entityName;

	@Override
	public APIQueryParams clone() throws CloneNotSupportedException {
		APIQueryParams params = new APIQueryParams();
		params.setFilter(filter);
		params.setPage(page);
		params.setSize(size);
		params.setSort(sort);
		params.setDistinct(distinct);
		params.setMethods(methods);
		params.setEntityName(entityName);
		return params;
	}

	public LinkedTreeMap<String, Object> getFilter() {
		return filter;
	}

	public void setFilter(LinkedTreeMap<String, Object> filter) {
		this.filter = filter;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	public MethodType[] getMethods() {
		return methods;
	}

	public void setMethods(MethodType[] methods) {
		this.methods = methods;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
