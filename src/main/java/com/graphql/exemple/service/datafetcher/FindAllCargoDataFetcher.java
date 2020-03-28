package com.graphql.exemple.service.datafetcher;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.datafecher.GenericListDataFetcher;
import com.graphql.exemple.model.Cargo;
import com.graphql.exemple.repository.CargoRepository;

@Component
public class FindAllCargoDataFetcher extends GenericListDataFetcher<Cargo, CargoRepository, ArrayList<Cargo>> {

	public FindAllCargoDataFetcher() {
		super(ArrayList.class);
	}
}
