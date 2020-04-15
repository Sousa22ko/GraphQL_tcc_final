package com.graphql.exemple.service.datafetcher.cargo;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.GenericDataFetcher;
import com.graphql.exemple.model.Cargo;
import com.graphql.exemple.repository.CargoRepository;

@Component
public class FindByIdCargoDataFetcher extends GenericDataFetcher<Cargo, CargoRepository, Cargo> {

}
