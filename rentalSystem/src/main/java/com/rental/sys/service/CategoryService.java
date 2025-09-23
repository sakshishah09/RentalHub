package com.rental.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.sys.entities.Category;
import com.rental.sys.repo.CategoryRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category createCategory(String name) {
        if (categoryRepo.existsByName(name)) {
            throw new RuntimeException("Category with this name already exists!");
        }
        Category category = new Category();
        category.setName(name);
        return categoryRepo.save(category);
    }
    
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepo.findById(id);
    }
    public Category updateCategory(Integer id,String name) {
    	Optional<Category> OptCategory = categoryRepo.findById(id);
    	if(OptCategory.isEmpty()) {
    	new RuntimeException("Category not found with id " + id);
    	}
    	Category category = OptCategory.get();
    	category.setName(name);
        return categoryRepo.save(category);
    }

    public void deleteCategory(Integer id) {
        categoryRepo.deleteById(id);
    }
}
