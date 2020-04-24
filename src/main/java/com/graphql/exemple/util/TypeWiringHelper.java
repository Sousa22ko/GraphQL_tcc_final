package com.graphql.exemple.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;

@SuppressWarnings("rawtypes")
public class TypeWiringHelper {

	public static DataFetcher datafetcher;

	public static UnaryOperator<TypeRuntimeWiring.Builder> customTipewiring(List<String> fieldName,
			List<DataFetcher> customDataFetcher) {

		Map<String, DataFetcher> map = new HashMap<String, DataFetcher>();
		for (int i = 0; i < fieldName.size(); i++) {
			map.put(fieldName.get(i), customDataFetcher.get(i));
		}

		map.putAll(defaultMapTypeWiring());

		return typewiring -> typewiring.dataFetchers(map);
	}

	public static UnaryOperator<TypeRuntimeWiring.Builder> customTipewiring(String fieldName,
			DataFetcher customDataFetcher) {

		Map<String, DataFetcher> map = new HashMap<String, DataFetcher>();
		map.put(fieldName, customDataFetcher);

		map.putAll(defaultMapTypeWiring());

		return typewiring -> typewiring.dataFetchers(map);
	}

	public static UnaryOperator<TypeRuntimeWiring.Builder> defaultTypeWiring() throws Exception {
		return typewiring -> typewiring.dataFetchers(defaultMapTypeWiring());
	}

	private static Map<String, DataFetcher> defaultMapTypeWiring() {
		Map<String, DataFetcher> defaultMap = new HashMap<String, DataFetcher>();

		defaultMap.put(Constant.FIND_ALL, datafetcher);
		defaultMap.put(Constant.FIND_BY_ID, datafetcher);
		defaultMap.put(Constant.COUNT, datafetcher);
		return defaultMap;

	}
	
	protected static RuntimeWiring generateRunTW() {
		
		RuntimeWiring tipe = RuntimeWiring.newRuntimeWiring().build();
		
		return tipe;
	}
}
