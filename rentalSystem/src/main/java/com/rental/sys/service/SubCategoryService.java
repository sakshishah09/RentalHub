package com.rental.sys.service;

import com.rental.sys.entities.Category;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.model.request.SubCategorySaveRequestModel;
import com.rental.sys.model.request.SubCategoryUpdateRequestModel;
import com.rental.sys.model.response.SubCategoryResponse;
import com.rental.sys.repo.CategoryRepo;
import com.rental.sys.repo.SubCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public SubCategoryResponse createSubCategory(SubCategorySaveRequestModel request) throws Exception {
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new Exception("Category not found"));
        Subcategory subCategory = new Subcategory();
        subCategory.setName(request.getName());
        subCategory.setCategory(category);
        Subcategory saved = subCategoryRepo.save(subCategory);
        return toResponse(saved);
    }

    public SubCategoryResponse updateSubCategory(SubCategoryUpdateRequestModel request) throws Exception {
    	Optional<Subcategory> subCategoryOptional  = subCategoryRepo.findById(request.getId());
    	if (subCategoryOptional.isEmpty()) {
            throw new Exception("The category does not exist.");
        }
    	Subcategory subCategory = subCategoryOptional.get();
               
        Category category = categoryRepo.findById(subCategory.getCategory().getId())
                .orElseThrow(() -> new Exception("Category not found"));
        subCategory.setName(request.getName());
        subCategory.setCategory(category);
        Subcategory updated = subCategoryRepo.save(subCategory);
        return toResponse(updated);
    }

    public Optional<SubCategoryResponse> getSubCategoryById(int id) {
        return subCategoryRepo.findById(id).map(this::toResponse);
    }

    public List<SubCategoryResponse> getSubCategoriesByCategory(int categoryId) {
        return subCategoryRepo.findByCategoryId(categoryId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteSubCategory(int id) {
        subCategoryRepo.deleteById(id);
    }

    private SubCategoryResponse toResponse(Subcategory subCategory) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(subCategory.getId());
        response.setName(subCategory.getName());
        response.setCategoryId(subCategory.getCategory().getId());
        response.setCategoryName(subCategory.getCategory().getName());
        return response;
    }
}
