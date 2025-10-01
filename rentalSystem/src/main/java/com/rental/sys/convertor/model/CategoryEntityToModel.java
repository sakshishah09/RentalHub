package com.rental.sys.convertor.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rental.sys.entities.Category;
import com.rental.sys.model.response.CategoryResponse;

@Component
public class CategoryEntityToModel {
	
	public CategoryResponse getCategoryById(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setImagePath(category.getImagePath()); // path send karo
        return response;
    }

    public List<CategoryResponse> getFindAllConvert(List<Category> categories) {
        List<CategoryResponse> responses = new ArrayList<>();
        for (Category category : categories) {
            responses.add(getCategoryById(category));
        }
        return responses;
    }
}

