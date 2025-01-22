package com.example.apicocktail.service;

import com.example.apicocktail.domain.Category;
import com.example.apicocktail.exception.CategoryNotFoundException;
import com.example.apicocktail.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    //Para obtener todas las categorías
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    //Para obtener los nombres de las categorías
    public List<Category> getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

    //Para obtener la categoría por palabra clave
    public List<Category> getCategoryByKeyword(String keyword){
        return categoryRepository.findByNameContainingIgnoreCase(keyword);
    }

    //Para obtener las 5 primeras categorias
    public List<Category> getCategoryByTop5(){
        return categoryRepository.findByTop5ByOrderByNameAsc();
    }

    //Para guardar una categoria
    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    //Para actualizar categoria por id
    public Category updateCategory(Long id, Category categoryDetails) throws CategoryNotFoundException {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Event not found with id: " + id));

        //Actualizar los campos de la categoria existente con los nuevos valores
        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public Category updateCategoryPartial(Long id, Map<String, Object> updates) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found by id: " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Category.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, category, value);
            }
        });

        return categoryRepository.save(category);
    }

    // Obtener una categoría por ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    // Eliminar una categoría por ID
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
