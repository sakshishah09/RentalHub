package com.rental.sys.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.convertor.entities.CategoryModelToEntity;
import com.rental.sys.convertor.model.CategoryEntityToModel;
import com.rental.sys.entities.Category;
import com.rental.sys.model.response.CategoryResponse;
import com.rental.sys.repo.CategoryRepo;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    @Autowired
    private CategoryModelToEntity categoryModelToEntity;
    @Autowired
    private CategoryEntityToModel categoryEntityToModel;

    // Create category
    public CategoryResponse createCategory(String name, MultipartFile image) throws Exception {
        Category category = categoryModelToEntity.createCategory(name, image);
        Category savedCategory = categoryRepository.save(category);
        return categoryEntityToModel.getCategoryById(savedCategory);
    }

    // Update category
//    public CategoryResponse updateCategory(CategoryUpdateRequestModel request, MultipartFile newImage) throws Exception {
//        Category category = categoryModelToEntity.updateCategory(request, newImage);
//        Category savedCategory = categoryRepository.save(category);
//        return categoryEntityToModel.getCategoryById(savedCategory);
//    }

    // Find by ID
    public CategoryResponse findById(Integer id) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new Exception("The category does not exist.");
        }
        Category category = categoryOptional.get();
        return categoryEntityToModel.getCategoryById(category);
    }

    // Find all categories with pagination
    public List<CategoryResponse> findAllCategories(Integer page, Integer size) throws Exception {
        List<Category> categoryList = categoryRepository.findAllCategories(PageRequest.of(page, size));
        return categoryEntityToModel.getFindAllConvert(categoryList);
    }
    // Count all categories
    public long countAllCategories() throws Exception {
        return categoryRepository.count();
    }
}
