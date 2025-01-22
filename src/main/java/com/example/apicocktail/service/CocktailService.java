package com.example.apicocktail.service;


import com.example.apicocktail.domain.Category;
import com.example.apicocktail.domain.Cocktail;
import com.example.apicocktail.exception.CategoryNotFoundException;
import com.example.apicocktail.exception.CocktailNotFoundException;
import com.example.apicocktail.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;


    @Autowired
    public CocktailService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    //Para obtener todos los cocteles
    public List<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }

    //Para buscar por el nombre del coctel
    public List<Cocktail> getCocktailsByName(String name) {
        return cocktailRepository.findByName(name);
    }

    //Para buscar cocteles por categoría
    public List<Cocktail> getCocktailsByCategory(String category) {
        return cocktailRepository.findByCategory(category);
    }

    //Para buscar cocteles por nombre y categoría
    public List<Cocktail> getCocktailsByNameAndCategory(String name, String category) {
        return cocktailRepository.findByNameAndCategory(name, category);
    }

    //Para buscar cocteles por usuario
    public List<Cocktail> getCocktailsByUsername(String username) {
        return cocktailRepository.findByUser(username);
    }

    //Para obtener un coctel por Id
    public Cocktail getCocktailById(Long id) throws CocktailNotFoundException {
        return cocktailRepository.findById(id)
                .orElseThrow(()-> new CocktailNotFoundException("Cocktail not found by id: " +id));
    }

    //Para guardar un coctel
    public Cocktail saveCocktail(Cocktail cocktail){
        return cocktailRepository.save(cocktail);
    }

    //Para actualizar coctel por id
    public Cocktail updateCocktail(Long id, Cocktail cocktailDetails) throws CocktailNotFoundException {
        Cocktail existingCocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new CocktailNotFoundException("Cocktail not found by id: " + id));

        //Actualizar los campos de la categoria existente con los nuevos valores
        existingCocktail.setId(cocktailDetails.getId());
        existingCocktail.setDescription(cocktailDetails.getDescription());
        existingCocktail.setDegrees(cocktailDetails.getDegrees());
        existingCocktail.setLatitude(cocktailDetails.getLatitude());
        existingCocktail.setLongitude(cocktailDetails.getLongitude());
        existingCocktail.setUser(cocktailDetails.getUser());
        existingCocktail.setCategory(cocktailDetails.getCategory());
        existingCocktail.setReleaseDate(cocktailDetails.getReleaseDate());
        existingCocktail.setName(cocktailDetails.getName());
        existingCocktail.setIngredients(cocktailDetails.getIngredients());


        return cocktailRepository.save(existingCocktail);
    }

    public Cocktail updateCocktailPartial(Long id, Map<String, Object> updates) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cocktail not found by id: " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Cocktail.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, cocktail, value);
            }
        });

        return cocktailRepository.save(cocktail);
    }

    //Para eliminar un coctel por id
    public void deleteCocktail(Long id) throws CocktailNotFoundException{
        if (!cocktailRepository.existsById(id)) {
            throw new CocktailNotFoundException("Cocktail not found by id: " + id);
        }
        cocktailRepository.deleteById(id);
    }






}
