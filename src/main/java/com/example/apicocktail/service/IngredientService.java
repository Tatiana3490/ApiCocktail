package com.example.apicocktail.service;


import com.example.apicocktail.domain.Cocktail;
import com.example.apicocktail.domain.Ingredient;
import com.example.apicocktail.exception.CocktailNotFoundException;
import com.example.apicocktail.exception.IngredientNotFoundException;
import com.example.apicocktail.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;


    @Autowired
    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    //Para obtener todos los ingredientes
    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }

    //Para obtener el nombre del ingrediente
    public List<Ingredient> getIngredientByName(String name){
        return ingredientRepository.findByName(name);
    }


    //Para obtener los ingredientes por palabra clave
    public List<Ingredient> getIngredientByKeyword(String keyword){
        return ingredientRepository.findIngredientsByKeyword(keyword);
    }

    //Para guardar un ingrediente
    public Ingredient saveIngredient(Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    //Para actualizar un ingrediente por id
    public Ingredient updateIngredient(Long id, Ingredient ingredientDetails) throws IngredientNotFoundException {
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found by id: " + id));

        //Actualizar los campos de los ingredientes existentes con los nuevos valores
        existingIngredient.setName(ingredientDetails.getName());
        existingIngredient.setDescription(ingredientDetails.getDescription());

        return ingredientRepository.save(existingIngredient);
    }

    public Ingredient updateIngredientPartial(Long id, Map<String, Object> updates) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found by id: " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Ingredient.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, ingredient, value);
            }
        });

        return ingredientRepository.save(ingredient);
    }

    //Para eliminar un ingrediente por id
    public void deleteIngredient(Long id) throws IngredientNotFoundException{
        if (!ingredientRepository.existsById(id)) {
            throw new IngredientNotFoundException("Ingredient not found by id: " + id);
        }
        ingredientRepository.deleteById(id);
    }

    //Para obtener un ingrediente por Id
    public Ingredient getIngredientById(Long id){
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found by id: " + id));
    }


}
