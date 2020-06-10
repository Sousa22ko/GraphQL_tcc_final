package com.graphql.exemple.core;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class GenericConstant {

	/**
	 * Método para recuperar a chave de uma constante, através do valor da mesma
	 */
	public String getKey(String value) {
		return getMapKeyValue()
			.entrySet()
			.stream()
			.filter(
				entry -> Objects.equals(entry.getValue(), value))
			.findFirst().get().getKey();
	}
	
	/**
	 * Método para recuperar as chave das constantes
	 */
	public List<String> getKeys() {
		return getMapKeyValue()
			.entrySet()
			.stream()
			.map(
				entry -> entry.getKey())
			.collect(Collectors.toList());
	}

	/**
	 * Método para recuperar o valor de uma constante, através da chave da mesma
	 */
	public String getValue(String key) {
		return getMapKeyValue().get(key);
	}

	/**
	 * Método para recuperar apenas os valores(values) das Constantes
	 */
	public List<Object> getValues(){
		Field[] fields = this.getClass().getDeclaredFields();
		return Arrays.asList(fields).stream().map(field -> {
			try {
				return field.get(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	/**
	 * Método para recuperar o conjunto de valores: {chave: valor}
	 *
	 * Ex: {'RH': 'Recursos Humanos'}
	 */
	public Map<String, String> getMapKeyValue(){
		Field[] fields = this.getClass().getDeclaredFields();
		Map<String, String> values = new HashMap<String, String>();
		for(Field field: Arrays.asList(fields)) {
			try {
				values.put(field.getName(), (String) field.get(this));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return values;
	}
}
