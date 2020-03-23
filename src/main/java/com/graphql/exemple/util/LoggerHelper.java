package com.graphql.exemple.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Helper com templates de mensagens para logging
 * 
 * @author Mateus Antonio
 *
 */
@Component
public class LoggerHelper {

	@Autowired
	HttpServletRequest request;

	@SuppressWarnings("rawtypes")
	/**
	 * Gera uma entrada no log a partir das informações
	 * 
	 * @param usuario  Usuário responsável pela ação
	 * @param c        Classe que invocou a operação
	 * @param operacao Tipo de operação
	 * @param entidade Entidade na qual a operação está associada
	 */
	public void generateLog(Class c, String operacao, String entidade) {
		Logger logger = LoggerFactory.getLogger(c);
		logger.warn("Usuário: [" + getUsuarioLogado() + "] - " + "Operação: [" + operacao + "] - " + "Entidade: ["
				+ entidade + "]");
	}

	@SuppressWarnings("rawtypes")
	/**
	 * Gera uma entrada no log a partir das informações
	 * 
	 * @param usuario  Usuário responsável pela ação
	 * @param c        Classe que invocou a operação
	 * @param operacao Tipo de operação
	 * @param entidade Entidade na qual a operação está associada
	 * @param id       ID da entidade
	 */
	public void generateLog(Class c, String operacao, String entidade, Integer id) {
		Logger logger = LoggerFactory.getLogger(c);
		logger.warn("Usuário: [" + getUsuarioLogado() + "] - " + "Operação: [" + operacao + "] - " + "Entidade: ["
				+ entidade + "] - " + "ID: [" + id + "]");
	}

	@SuppressWarnings("rawtypes")
	/**
	 * Gera uma entrada no log a partir das informações
	 * 
	 * @param usuario  Usuário responsável pela ação
	 * @param c        Classe que invocou a operação
	 * @param operacao Tipo de operação
	 */
	public void generateLog(Class c, String operacao) {
		Logger logger = LoggerFactory.getLogger(c);
		logger.warn("Usuário: [" + getUsuarioLogado() + "] - " + "Operação: [" + operacao + "]");
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	private String getUsuarioLogado() {
		return "teste";
	}

}
