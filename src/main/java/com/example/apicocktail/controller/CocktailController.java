package com.example.apicocktail.controller;

import com.example.apicocktail.domain.Cocktail;
import com.example.apicocktail.exception.CocktailNotFoundException;
import com.example.apicocktail.service.CocktailService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final Logger logger = LoggerFactory.getLogger(CocktailController.class);
    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService){
        this.cocktailService = cocktailService;
    }

    //Para obtener todos los cocteles
    @GetMapping
    public ResponseEntity<List<Cocktail>> getAllCocktails(){
        logger.info("BEGIN getAllCocktails");
        List<Cocktail> cocktails = cocktailService.getAllCocktails();
        logger.info("END getAllCocktails - Total cocktails fetched: {}", cocktails.size());
        return new ResponseEntity<>(cocktails, HttpStatus.OK);
    }

    //Agregar un coctel nuevo
    @PostMapping
    public ResponseEntity<Cocktail> addCocktail(@RequestBody Cocktail cocktail) {
        logger.info("BEGIN addCocktail - Adding new cocktail: {}", cocktail.getName());
        Cocktail newCocktail = cocktailService.saveCocktail(cocktail);
        logger.info("END addCocktail - Cocktail added with ID: {}", newCocktail.getId());
        return new ResponseEntity<>(newCocktail, HttpStatus.CREATED);
    }

    //Buscar cocteles por nombre
    @GetMapping("/name")
    public ResponseEntity<List<Cocktail>> getCocktailsByName(@RequestParam String name){
        logger.info("BEGIN getCocktailsByname - Searching cocktails with name: {}", name);
        List<Cocktail> cocktails = cocktailService.getCocktailsByName(name);
        logger.info("END getCocktailsByName - Total cocktails found: {}", cocktails.size());
        return new ResponseEntity<>(cocktails, HttpStatus.OK);
    }

    //Buscar cocteles por categoria
    @GetMapping("/category")
    public ResponseEntity<List<Cocktail>> getCocktailsByCategory(@RequestParam String category){
        logger.info("BEGIN getCocktailsByCategory - Searching cocktails with category: {}", category);
        List<Cocktail> cocktails = cocktailService.getCocktailsByCategory(category);
        logger.info("END getCocktailsByCategory - Total cocktails found: {}", cocktails.size());
        return new ResponseEntity<>(cocktails, HttpStatus.OK);
    }

    //Buscar cocteles por nombre y categoria
    @GetMapping("/nameandcategory")
    public ResponseEntity<List<Cocktail>> getCocktailsByNameAndCategory(@RequestParam String name, @RequestParam String category){
        logger.info("BEGIN getCocktailsByNameAndCategory - Searching cocktails by name {} and by category {}: ", name, category);
        List<Cocktail> cocktails = cocktailService.getCocktailsByNameAndCategory(name, category);
        logger.info("END getCocktailsByNameAndCategory - Total cocktails found: {}", cocktails.size());
        return new ResponseEntity<>(cocktails, HttpStatus.OK);
    }

    //Buscar cocteles por usuario
    @GetMapping("/username")
    public ResponseEntity<List<Cocktail>> getCocktailsByUsername(@RequestParam String username){
        logger.info("BEGIN getCocktailsByUsername - username: {}", username);
        List<Cocktail> cocktails = cocktailService.getCocktailsByUsername(username);
        logger.info("END getCocktailsByUsername - username found: {}", cocktails.size());
        return new ResponseEntity<>(cocktails, HttpStatus.OK);
    }


    //Para actualizar un coctel por id
    @PutMapping("/{id}")
    public ResponseEntity<Cocktail> updateCocktail(@PathVariable Long id, @RequestBody Cocktail cocktailDetails) throws CocktailNotFoundException {
        logger.info("BEGIN updateCocktail - Cocktail update with name: {}", id);
        try{
            Cocktail updateCocktail = cocktailService.updateCocktail(id, cocktailDetails);
            logger.info("END updateCocktail - Cocktail update with name: {}", updateCocktail.getId());
            return  new ResponseEntity<>(updateCocktail, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateCocktail - Cocktail not found by id: {}", id, e);
            throw e;
        }

    }

    //Para actualiar parcialmente un coctel
    @PatchMapping("/{id}")
    public ResponseEntity<Cocktail> updateCocktail(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        logger.info("BEGIN updateCocktailPartial - Partially updating cocktail by Id: {}", id);
        try {
            Cocktail updatedCocktail = cocktailService.updateCocktailPartial(id, updates);
            logger.info("END updateCocktailPartial - Cocktail updated by Id: {}", updatedCocktail.getId());
            return ResponseEntity.ok(updatedCocktail);
        } catch (Exception e) {
            logger.error("Error in updateCocktailPartial - Cocktail not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para obtener un coctel por id
    @GetMapping("/{id}")
    public ResponseEntity<Cocktail> getCocktailById(@PathVariable Long id) throws CocktailNotFoundException {
        logger.info("BEGIN getCocktailById - Fetching cocktail by Id: {}", id);
        try {
            Cocktail cocktail = cocktailService.getCocktailById(id);
            logger.info("END getCocktailById - Cocktail found: {}", cocktail.getId());
            return new ResponseEntity<>(cocktail, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getCocktailById - Cocktail not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para eliminar un coctel por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCocktail(@PathVariable Long id) throws CocktailNotFoundException{
        logger.info("BEGIN deleteCocktail - Deleting cocktail by Id: {}", id);
        try {
            cocktailService.deleteCocktail(id);
            logger.info("END deleteCocktail - Cocktail deleted by Id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error in deleteCocktail - Cocktail not found by Id: {}", id, e);
            throw e;
        }
    }

    // Manejar excepciones de coctel no encontrado
    @ExceptionHandler(CocktailNotFoundException.class)
    public ResponseEntity<String> handleCocktailNotFoundException(CocktailNotFoundException exception) {
        logger.error("Handling CocktailNotFoundException - {}", exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }





}
