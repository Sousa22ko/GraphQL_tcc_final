package com.graphql.exemple.model.scalar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

public class DateScalar {
	
	public static final GraphQLScalarType DATE = new GraphQLScalarType("Date", "scalar para datas", new Coercing<String, Date>() {

		@Override
		public Date serialize(Object dataFetcherResult) {
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
	}) ;
	
	private static Date serializeDate(Object data) {
		int year = Integer.parseInt(data.toString().substring(0, 4));
		int month = Integer.parseInt(data.toString().substring(5, 7));
		int day = Integer.parseInt(data.toString().substring(8, 10));
		
		Calendar cal = new GregorianCalendar(year, month - 1, day);
		return cal.getTime();
	}
	
	private static String parseValueDate(Object input) {
		return "" + ((Date)input).getTime();
	}
}
