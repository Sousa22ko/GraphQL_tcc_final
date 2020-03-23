package com.graphql.exemple.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * <h2>Deserializador de datas customizados</h2>
 * <p>
 * O deserializador customizado deserializa strings em datas seguindo
 * determinados formatos pré-definidos. Sendo eles:
 * </p>
 * 
 * <ul>
 * <li>dd-MM-yyyy</li>
 * <li>dd/MM/yyyy</li>
 * <li>MMM dd, yyyy, HH:mm:ss a</li>
 * </ul>
 * <br/>
 * 
 * @author Lucas Simonetti
 */
@Component
public class CustomDateDeserializer extends StdDeserializer<Date> {

	/**
	 * <p>
	 * Construtor vazio
	 * </p>
	 */
	public CustomDateDeserializer() {
		this(null);
	}

	/**
	 * <p>
	 * Contrutor com classe
	 * </p>
	 */
	protected CustomDateDeserializer(Class<?> vc) {
		super(vc);
	}

	private static final long serialVersionUID = 1L;

	// Formatter com o formato default
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	/**
	 * <p>
	 * O método <code>deserialize(...) throws ...</code> recebe um objeto json e
	 * deserializa as strings dos campos data em objetos Date utilizando um
	 * SimpleDateFormat e os padrões pré-definidos
	 * </p>
	 * 
	 * @param jsonparser O parser do objeto jason recebido
	 * @param context    O contexto sob o qual a deserialização foi chamada
	 * @return Um objeto data formado pela String deserializada
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		String date = jsonparser.getText();
		
		if(date.equals(""))
			return null;

		// Expressões regulares para reconhecer o formato da data que foi recebida
		String space = "\\x{0020}+";
		boolean format1 = date.matches("([A-Z][a-z]{2}" + space + "[0-9]{1,2}," + space + "[0-9]{4}," + space
				+ "[0-9]{1,2}:[0-9]{2}:[0-9]{2}" + space + "[A-Za-z]{2}" + ")");
		boolean format2 = date.matches("([0-9]{2}/[0-9]{2}/[0-9]{4})");
		boolean format3 = date.matches("([0-9]{4}-[0-9]{2}-[0-9]{2})");
		boolean format4 = date.matches("([0-9]{2}/[0-9]{2}/[0-9]{4}"+space+"[0-9]{1,2}:[0-9]{2}:[0-9]{2})");

		// Escolhendo outro formato baseado na data recebida
		if (format1)
			formatter = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss a", Locale.US); // Locale.US utilizado para que
																						// sejam reconhecidos os meses
																						// em
																						// inglês (Feb, Apr, May, Aug,
																						// Sep,
																						// Oct, Dec)
		else if (format2)
			formatter = new SimpleDateFormat("dd/MM/yyyy");

		else if (format3)
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		else if (format4)
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}