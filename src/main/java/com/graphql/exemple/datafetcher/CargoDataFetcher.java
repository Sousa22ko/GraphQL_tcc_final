package com.graphql.exemple.datafetcher;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.graphql.exemple.core.GenericDataFetcher;
import com.graphql.exemple.model.Cargo;
import com.graphql.exemple.repository.CargoRepository;

@Component
public class CargoDataFetcher extends GenericDataFetcher<Cargo, CargoRepository, ArrayList<Cargo>> {

}
