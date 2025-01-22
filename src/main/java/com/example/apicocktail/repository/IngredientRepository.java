package com.example.apicocktail.repository;


import com.example.apicocktail.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository  extends CrudRepository<Ingredient, Long> {

    List<Ingredient> findByName(String name); //parea buscar ingredientes por nombre
    List<Ingredient> findIngredientsByKeyword(@Param("keyword") String keyword); //para buscar ingredientes por palabra clave
    List<Ingredient> findAll();
}
