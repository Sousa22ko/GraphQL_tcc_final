package com.graphql.exemple.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

/**
 * Classe responsável por encapsular violações de regras de negócio e
 * retorná-las como uma exceção. Faz uso de uma lista de FieldErrors, de modo a
 * associar os erros ao campo onde o erro aconteceu.
 * 
 * @author Talison Costa
 *
 */
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Lista de erros por campo
	 */
	private List<FieldError> erros = new ArrayList<FieldError>();

	public NegocioException(String msg) {
		super(msg);
	}

	public List<FieldError> getErros() {
		return erros;
	}

	public void setErros(List<FieldError> erros) {
		this.erros = erros;
	}

	public void addFieldError(FieldError error) {
		this.erros.add(error);
	}
	
	public List<String> getDefaultMessages() {
		List<String> defaultMessages = new ArrayList<String>();
		for (FieldError err : erros) {
			defaultMessages.add(getDefaultMessage(err.toString()));
		}
		return defaultMessages;
	}
	
	public static String getDefaultMessage(String fieldErrorToString) {
		String defaultMessage = fieldErrorToString.substring(fieldErrorToString.indexOf("default message [") + 17);
		defaultMessage = defaultMessage.substring(0, defaultMessage.indexOf("]"));
		return defaultMessage;
	}

}
