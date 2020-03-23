package com.graphql.exemple.APIQL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.graphql.exemple.APIQL.type.ConnectorType;
import com.graphql.exemple.APIQL.type.OperationType;
import com.graphql.exemple.exception.APIQueryException;

public class ObjectToJPQL {

	private String template;
	private Boolean hasMoreThanOneCondition;
	private Boolean hasMoreThanOneJoin;
	private Map<String, Object> mapValues;

	/**
	 * PSQL based on filter
	 * 
	 * @param instance
	 * @param orderByFieldName
	 * @param clazz
	 * @param methods
	 * @return
	 */
	@SuppressWarnings("unused")
	public String build(APIQueryParams params) {
		mapValues = new HashMap<String, Object>();
		hasMoreThanOneCondition = false;
		hasMoreThanOneJoin = false;
		template = "SELECT a FROM #entityName a #join #where #orderby";
		setEntityName(params.getEntityName());
		Gson gson = new Gson();
		if (params.getFilter() != null) {
			where("a", params.getFilter());
		}
		orderby("a", params.getSort());
		configClousures();
		return template.replaceAll("  ", " ").trim();

	}

	@SuppressWarnings("unchecked")
	public void where(String prefix, LinkedTreeMap<String, Object> map) {

		if (map.get("value") != null && map.get("op") != null) { // Node

			OperationType operator = null;
			try {
				operator = OperationType.valueOf((String) map.get("op"));
			} catch (IllegalArgumentException ex) { // enum invalido
				System.err.println(
						"java.lang.IllegalArgumentException: No enum constant br.com.fotonica.apiql.OperationType."
								+ map.get("op"));
			}

			if (map.get("value") instanceof List) {
				System.out.println(operator);
				if (operator != OperationType.in) {
					throw new APIQueryException("Expect in operator to List type!");
				}
			}

			ConnectorType c = null;
			if (map.get("c") != null)
				c = map.get("c") instanceof ConnectorType ? (ConnectorType) map.get("c")
						: ConnectorType.valueOf((String) map.get("c"));
			buildCondition(prefix, map.get("value"), operator, c);
			return;
		}

		Set<Entry<String, Object>> set = map.entrySet();
		for (Entry<String, Object> element : set) { // Nav through JSON as MAP
			Object value = element.getValue();
			if (value instanceof LinkedTreeMap) { // Object
				where(String.format("%s.%s", prefix, element.getKey()), (LinkedTreeMap<String, Object>) value);
			} else if (value instanceof List) { // Array
				List<LinkedTreeMap<String, Object>> array = (List<LinkedTreeMap<String, Object>>) value;
				addJoin(String.format("%s.%s %s", prefix, element.getKey(), element.getKey(), element.getKey()));
				for (LinkedTreeMap<String, Object> obj : array) {
					where(element.getKey(), obj);
				}
			} else { // Node
				buildCondition(String.format("%s.%s", prefix, element.getKey()), value, null, null);
//				System.err.println(String.format("%s.%s: %s [%s]", prefix, element.getKey(), value, value.getClass().getSimpleName()));
			}
		}
	}

	/**
	 * Build condition
	 * 
	 * @param parentName
	 * @param field
	 * @param value
	 */
	public void buildCondition(String key, Object value, OperationType op, ConnectorType connector) {
		String _key = key.replaceAll("\\.", "");
		if (value instanceof Integer || value instanceof Double || value instanceof Boolean) {
			Object newValue = value instanceof Boolean || value instanceof Integer ? value
					: Integer.valueOf((int) Math.round((double) value));
			mapValues.put(_key, newValue);
			String condition = APIQueryTemplate.build(op != null ? op : OperationType.eq, key, ":" + _key);
			addCondition(condition, connector);
		} else if (value instanceof String) {
			mapValues.put(_key, APIQueryTemplate.getStringValue(op, (String) value));
			String condition = APIQueryTemplate.build(op, key, ":" + _key);
			addCondition(condition, connector);
		} else if (value instanceof List) {
			addJoin(String.format("%s %s", key, _key));
			mapValues.put(_key, value);
			String condition = APIQueryTemplate.build(op, _key, ":" + _key);
			addCondition(condition, connector);
		}
	}

	/**
	 * Add condition to psql template
	 * 
	 * @param condition
	 */
	public void addCondition(String condition, ConnectorType connector) {
		if (hasMoreThanOneCondition) {
			template = template.replace("#where", String.format("%s #where",
					String.format(" %s ", connector != null ? connector : "and") + condition));
		} else {
			template = template.replace("#where", String.format("WHERE %s #where", condition));
			hasMoreThanOneCondition = true;
		}
	}

	/**
	 * Add join to psql template
	 * 
	 * @param join
	 */
	public void addJoin(String join) {
		if (hasMoreThanOneJoin) {
			template = template.replace("#join", "#join " + join + " JOIN ");
		} else {
			template = template.replace("#join", "#join " + join);
			hasMoreThanOneJoin = true;
		}
	}

	/**
	 * Add order by clouse
	 * 
	 * @param parentName
	 * @param fieldName
	 */
	public void orderby(String parentName, String fieldName) {
		if (fieldName != null) {
			template = template.replace("#orderby", "ORDER BY " + parentName + "." + fieldName);
		} else {
			template = template.replace("#orderby", "");
		}
	}

	private void setEntityName(String entityName) {
		template = template.replace("#entityName", entityName);
	}

	public void configClousures() {
		template = hasMoreThanOneJoin ? template.replace("#join", "JOIN") : template.replace("#join", "");
		template = template.replace("#where", "");
	}

	public Query execute(EntityManager entityManager) {
		Query query = entityManager.createQuery(template);
		if (mapValues != null) {
			for (Map.Entry<String, Object> item : mapValues.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());
			}
		}
		return query;
	}

	public Query count(EntityManager entityManager) {
		template = template.replaceFirst("a", "COUNT(a)");
		Query query = entityManager.createQuery(template);
		if (mapValues != null) {
			for (Map.Entry<String, Object> item : mapValues.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());
			}
		}
		return query;
	}
}
