package com.example.apicocktail.repository;


import com.example.apicocktail.domain.Cocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Long> {

    List<Cocktail> findAll(); //para obtener todos los cocteles
    List<Cocktail> findByName(String name); //para buscar cocteles por nombre
    List<Cocktail> findByCategory(String category); //para buscar cocteles por categoria
    List<Cocktail> findByNameAndCategory(String name, String category); //para buscar por nombre y categoria
    List<Cocktail> findByUser(String username); //para buscar cocteles por usuario





}
