package com.rental.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.sys.entities.Category;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.repo.CategoryRepo;
import com.rental.sys.repo.SubCategoryRepo;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Subcategory createSubCategory(Subcategory subCategory) {
        Category category = categoryRepo.findById(subCategory.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id "));

        subCategory.setCategory(category);
        return subCategoryRepo.save(subCategory);
    }

    public List<Subcategory> getSubCategoriesByCategory(Integer categoryId) {
        return subCategoryRepo.findByCategoryId(categoryId);
    }

    public Optional<Subcategory> getSubCategoryById(Integer id) {
        return subCategoryRepo.findById(id);
    }

    public Subcategory updateSubCategory(Integer id, Subcategory subCategoryDetails) {
        return subCategoryRepo.findById(id).map(existing -> {
            existing.setName(subCategoryDetails.getName());
            return subCategoryRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("SubCategory not found with id " + id));
    }

    public void deleteSubCategory(Integer id) {
        subCategoryRepo.deleteById(id);
    }
}

