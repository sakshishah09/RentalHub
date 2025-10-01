package com.rental.sys.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rental.sys.entities.Subcategory;
import com.rental.sys.model.request.SubCategorySaveRequestModel;
import com.rental.sys.model.request.SubCategoryUpdateRequestModel;
import com.rental.sys.model.response.SubCategoryResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.SubCategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @Operation(summary = "Create a new subcategory", description = "Adds a new subcategory under a category.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public RestResponse createSubCategory(@RequestBody SubCategorySaveRequestModel request) {
        try {
            SubCategoryResponse saved = subCategoryService.createSubCategory(request);
            return RestResponse.build().withSuccess("SubCategory created successfully", saved);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Get subcategory by ID", description = "Fetch a subcategory by its ID.")
    @GetMapping("/{id}")
    public RestResponse getSubCategoryById(@PathVariable Integer id) {
        return subCategoryService.getSubCategoryById(id)
                .map(subCategory -> RestResponse.build().withSuccess("SubCategory fetched successfully", subCategory))
                .orElse(RestResponse.build().withError("SubCategory not found"));
    }

    @Operation(summary = "Get subcategories by category ID", description = "Fetch all subcategories under a specific category.")
    @GetMapping("/category/{categoryId}")
    public RestResponse getSubCategoriesByCategoryId(@PathVariable Integer categoryId) {
        try {
            List<SubCategoryResponse> subCategories = subCategoryService.getSubCategoriesByCategory(categoryId);
            return RestResponse.build().withSuccess("SubCategories fetched successfully", subCategories);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Update subcategory", description = "Update an existing subcategory by ID.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public RestResponse updateSubCategory(@RequestBody SubCategoryUpdateRequestModel request) {
        try {
            SubCategoryResponse updated = subCategoryService.updateSubCategory(request);
            return RestResponse.build().withSuccess("SubCategory updated successfully", updated);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Delete subcategory", description = "Delete an existing subcategory by ID.")
    @DeleteMapping("/{id}")
    public RestResponse deleteSubCategory(@PathVariable Integer id) {
        try {
            subCategoryService.deleteSubCategory(id);
            return RestResponse.build().withSuccess("SubCategory deleted successfully");
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
}
