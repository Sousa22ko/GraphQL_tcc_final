package com.graphql.exemple.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.graphql.exemple.core.GenericGraphQLService;
import com.graphql.exemple.service.datafetcher.FindAllCargoDataFetcher;
import com.graphql.exemple.service.datafetcher.FindByIdCargoDataFetcher;

@Service
public class CargoGraphQLService extends GenericGraphQLService<FindAllCargoDataFetcher, FindByIdCargoDataFetcher> {

	@Value("classpath:cargo.graphql")
	Resource resource;

	@Override
	@PostConstruct
	protected void loadGraphQLSchema() throws IOException {
		File file = resource.getFile();
		register(file);
	}
}
