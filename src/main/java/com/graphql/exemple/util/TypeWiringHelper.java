package com.graphql.exemple.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import graphql.schema.DataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;

@SuppressWarnings("rawtypes")
public class TypeWiringHelper {

	public static DataFetcher one;
	public static DataFetcher list;

	public static UnaryOperator<TypeRuntimeWiring.Builder> customTipewiring(List<String> fieldName,
			List<DataFetcher> dataFetcher, DataFetcher one, DataFetcher list) throws Exception {
		if (fieldName.size() != dataFetcher.size()) {
			throw new Exception("field name e dataFecthers não são compativeis");
		}

		Map<String, DataFetcher> map = new HashMap<String, DataFetcher>();
		for (int i = 0; i < fieldName.size(); i++) {
			map.put(fieldName.get(i), dataFetcher.get(i));
		}
		
		map.putAll(defaultMapTypeWiring());

		return typewiring -> typewiring.dataFetchers(map);
	}

	public static UnaryOperator<TypeRuntimeWiring.Builder> defaultTypeWiring() throws Exception {
		return typewiring -> typewiring.dataFetchers(defaultMapTypeWiring());
	}

	private static Map<String, DataFetcher> defaultMapTypeWiring() {
		Map<String, DataFetcher> defaultMap = new HashMap<String, DataFetcher>();

		defaultMap.put(Constant.findAll, list);
		defaultMap.put(Constant.findById, one);
		return defaultMap;

	}
}
