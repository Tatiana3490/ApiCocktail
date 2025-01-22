package com.example.apicocktail.controller;

import com.example.apicocktail.domain.Category;
import com.example.apicocktail.domain.Cocktail;
import com.example.apicocktail.exception.CategoryNotFoundException;
import com.example.apicocktail.exception.CocktailNotFoundException;
import com.example.apicocktail.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/category")
public class CategoryCotroller {

    private final Logger logger = LoggerFactory.getLogger(CategoryCotroller.class);
    private final CategoryService categoryService;

    @Autowired
    public CategoryCotroller(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //Para obtener todos las categorias
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        logger.info("BEGIN getAllCategories");
        List<Category> categories = categoryService.getAllCategories();
        logger.info("END getAllCategories - Total categories fetched: {}", categories.size());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //Agregar una categoria nueva
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        logger.info("BEGIN addCategory - Adding new category: {}", category.getName());
        Category newCategory = categoryService.saveCategory(category);
        logger.info("END addCategory - Category added with ID: {}", newCategory.getId());
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }


    //Buscar categorias por nombre
    @GetMapping("/category-name")
    public ResponseEntity<List<Category>> getCategoryByName(@RequestParam String name){
        logger.info("BEGIN getCategoryByName - Searching category with name: {}", name);
        List<Category> categories = categoryService.getCategoryByName(name);
        logger.info("END getCategoryByName - Total categories found: {}", categories.size());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //Buscar categoria por palabra clave
    @GetMapping("/{keyword}")
    public ResponseEntity<List<Category>> getCategoryByKeyword(@PathVariable String keyword){
        logger.info("BEGIN getCategoryByKeyword - Searching category with keyword: {}", keyword);
        try {
            List<Category> categories = categoryService.getCategoryByKeyword(keyword);
            logger.info("END getCategoryByKeyword - Total categories found: {}", categories.size());
            return new ResponseEntity<>(categories, HttpStatus.OK);
    } catch (Exception e) {
            logger.error("Error in getCategoryByKeyword - Category not found: {}", keyword,e);
            throw e;
        }
    }

    //Buscar las 5 primeras categorias
    @GetMapping("/top-5-categories")
    public ResponseEntity<List<Category>> getCategoryByTop5(){
        logger.info("BEGIN getCategoryByTop5 - Searching category with top 5 categories");
        List<Category> categories = categoryService.getCategoryByTop5();
        logger.info("END getCategoryByTop5 - Total categories found: {}", categories.size());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //Para actualizar una categoria  por id
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) throws CategoryNotFoundException {
        logger.info("BEGIN updateCategory - Category update by id: {}", id);
        try{
            Category updateCategory = categoryService.updateCategory(id, categoryDetails);
            logger.info("END updateCategory - Category update by id: {}", updateCategory.getId());
            return  new ResponseEntity<>(updateCategory, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateCategory - Category not found by id: {}", id, e);
            throw e;
        }

    }

    //Para actualiar parcialmente una categoria
    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        logger.info("BEGIN updateCategoryPartial - Partially updating category by Id: {}", id);
        try {
            Category updatedCategory = categoryService.updateCategoryPartial(id, updates);
            logger.info("END updateCocktailPartial - Cocktail updated by Id: {}", updatedCategory.getId());
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            logger.error("Error in updateCategoryPartial - Category not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para obtener una categoria por id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        logger.info("BEGIN getCategoryById - Fetching category by Id: {}", id);
        try {
            Category category = categoryService.getCategoryById(id);
            logger.info("END getCategoryById - Category found: {}", category.getId());
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getCategoryById - Category not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para eliminar una categoria por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException{
        logger.info("BEGIN deleteCategory - Deleting category by Id: {}", id);
        try {
            categoryService.deleteCategory(id);
            logger.info("END deleteCategory - Category deleted by Id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error in deleteCategory - Category not found by Id: {}", id, e);
            throw e;
        }
    }

    // Manejar excepciones de categoria no encontrada
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        logger.error("Handling CategoryNotFoundException - {}", exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }


}
