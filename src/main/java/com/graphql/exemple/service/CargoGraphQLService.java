package com.graphql.exemple.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.io.Resources;
import com.graphql.exemple.core.GenericGraphQLService;
import com.graphql.exemple.service.datafetcher.cargo.CargoDataFetcher;

@Service
public class CargoGraphQLService extends GenericGraphQLService<CargoDataFetcher> {

	@Override
	@PostConstruct
	protected void loadResource() throws IOException {
		resourcePath = Resources.getResource("cargo.graphql");
	}
}
