package com.graphql.exemple.model.scalar;

import java.util.Date;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

public class DateScalar {

	public static final GraphQLScalarType DATE = new GraphQLScalarType("Date", "scalar para datas",
			new Coercing<String, String>() {

				@Override
				public String serialize(Object dataFetcherResult) {
					return serializeDate(dataFetcherResult);
				}

				@Override
				public String parseValue(Object input) {
					return parseValueDate(input);
				}

				@Override
				public String parseLiteral(Object input) {
					// TODO Auto-generated method stub
					return null;
				}
			});

	private static String serializeDate(Object data) {
		int year = Integer.parseInt(data.toString().substring(0, 4));
//		
		String month = data.toString().substring(5, 7);
		int day = Integer.parseInt(data.toString().substring(8, 10));

//		Calendar cal = new GregorianCalendar(year, month - 1, day);
//		return cal.getTime();
		return day + "/" + month + "/" + year;
	}

	private static String parseValueDate(Object input) {
		return "" + ((Date) input).getTime();
	}
}
