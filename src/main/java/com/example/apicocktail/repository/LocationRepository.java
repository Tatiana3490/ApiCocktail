package com.example.apicocktail.repository;


import com.example.apicocktail.domain.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository  extends CrudRepository<Location, Long> {

    //metodo para buscar por ciudad
    List<Location> findByCity(String city);


    //metodo para buscar por estado
    List<Location> findByState(String state);

    // metodo para buscar por país
    List<Location> findByCountry(String country);

    // metodo para buscar por estado y pais
    List<Location> findByStateAndCountry(String state, String country);

    //metodo para buscar por estado, pais y ciudad
    List<Location> findByStateAndCountryAndCity(String state, String country, String city);

    //mertodo para buscar todas las localizaciones
    List<Location> findAll();
}
