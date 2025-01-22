package com.example.apicocktail.controller;


import com.example.apicocktail.domain.Ingredient;

import com.example.apicocktail.exception.IngredientNotFoundException;
import com.example.apicocktail.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final Logger logger = LoggerFactory.getLogger(IngredientController.class);
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    //Para obtener todos los ingredientes
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        logger.info("BEGIN getAllIngredients");
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        logger.info("END getAllIngredients - Total ingredients fetched: {}", ingredients.size());
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    //Agregar un ingrediente nuevo
    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        logger.info("BEGIN addIngredient - Adding new ingredient: {}", ingredient);
        Ingredient newIngredient = ingredientService.saveIngredient(ingredient);
        logger.info("END addIngredient - Ingredient added: {}", newIngredient);
        return new ResponseEntity<>(newIngredient, HttpStatus.CREATED);
    }


    //Buscar categorias por nombre
    @GetMapping("/ingredient-name")
    public ResponseEntity<List<Ingredient>> getIngredientByName(@RequestParam("name") String name) {
        logger.info("BEGIN getIngredientByName - Searching ingredients by name: {}", name);
        List<Ingredient> ingredients = ingredientService.getIngredientByName(name);
        logger.info("END getIngredientByName - Ingredients fetched: {}", ingredients.size());
        return new ResponseEntity<>(ingredients, HttpStatus.OK);

    }

    //Buscar ingredientes por palabra clave
    @GetMapping("/{keyword}")
    public ResponseEntity<List<Ingredient>> getIngredientByKeyword(@PathVariable String keyword){
        logger.info("BEGIN getIngredientByKeyword - Searching ingredient with keyword: {}", keyword);
        try {
            List<Ingredient> ingredients = ingredientService.getIngredientByKeyword(keyword);
            logger.info("END getIngredientByKeyword - Total ingredients found: {}", ingredients.size());
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
    } catch (Exception e) {
            logger.error("Error in getIngredientByKeyword - Ingredients not found: {}", keyword,e);
            throw e;
        }
    }


    //Para actualizar un ingrediente por id
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredientDetails) throws IngredientNotFoundException {
        logger.info("BEGIN updateIngredient - Ingredient update by id: {}", id);
        try{
            Ingredient updateIngredient = ingredientService.updateIngredient(id, ingredientDetails);
            logger.info("END updateIngredient - Ingredient update by id: {}", updateIngredient.getId());
            return  new ResponseEntity<>(updateIngredient, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateIngredient - Ingredient not found by Id: {}", id, e);
            throw e;
        }

    }

    //Para actualiar parcialmente un ingrediente
    @PatchMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        logger.info("BEGIN updateIngredientPartial - Partially updating ingredient by Id: {}", id);
        try {
            Ingredient updatedIngredient = ingredientService.updateIngredientPartial(id, updates);
            logger.info("END updateIngredientPartial - Ingredient updated by Id: {}", updatedIngredient.getId());
            return ResponseEntity.ok(updatedIngredient);
        } catch (Exception e) {
            logger.error("Error in updateIngredientPartial - Ingredient not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para obtener un ingredient por id
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) throws IngredientNotFoundException {
        logger.info("BEGIN getIngredientById - Fetching ingredient by Id: {}", id);
        try {
            Ingredient ingredient = ingredientService.getIngredientById(id);
            logger.info("END getIngredientById - Ingredient found: {}", ingredient.getId());
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getIngredientById - Ingredient not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para eliminar un ingrediente por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) throws IngredientNotFoundException{
        logger.info("BEGIN deleteIngredient - Deleting ingredient by Id: {}", id);
        try {
            ingredientService.deleteIngredient(id);
            logger.info("END deleteIngredient - Ingredient deleted by Id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error in deleteIngredient - Ingredient not found by Id: {}", id, e);
            throw e;
        }
    }

    // Manejar excepciones de ingrediente no encontrado
    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<String> handleIngredientNotFoundException(IngredientNotFoundException exception) {
        logger.error("Handling IngredientNotFoundException - {}", exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }



}
