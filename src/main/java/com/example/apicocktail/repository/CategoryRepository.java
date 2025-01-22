package com.example.apicocktail.repository;


import com.example.apicocktail.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAll(); //para obtener todos las categorias;
    List<Category> findByName(String name); //para buscar por nombre de categoria
    List<Category> findByNameContainingIgnoreCase(String keyword); //para buscar palabra clave
    List<Category> findByTop5ByOrderByNameAsc(); //para buscar las primeras 5 categorias


}
