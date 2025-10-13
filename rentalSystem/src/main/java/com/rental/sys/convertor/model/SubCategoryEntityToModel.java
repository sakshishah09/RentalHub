package com.rental.sys.convertor.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.model.response.SubCategoryResponse;

@Component
public class SubCategoryEntityToModel {

    public SubCategoryResponse convertToResponse(Subcategory sub) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(sub.getId());
        response.setName(sub.getName());
        response.setCategoryId(sub.getCategory().getId());
        response.setCategoryName(sub.getCategory().getName());
        response.setImagePath(sub.getImagePath());
        return response;
    }

    public List<SubCategoryResponse> convertToList(List<Subcategory> subs) {
        List<SubCategoryResponse> responses = new ArrayList<>();
        for (Subcategory sub : subs) {
            responses.add(convertToResponse(sub));
        }
        return responses;
    }
}
