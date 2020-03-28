package com.graphql.exemple.service.datafetcher;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.datafecher.GenericListDataFetcher;
import com.graphql.exemple.model.Cargo;
import com.graphql.exemple.repository.CargoRepository;

@Component
public class FindByIdCargoDataFetcher extends GenericListDataFetcher<Cargo, CargoRepository, Cargo> {

}
