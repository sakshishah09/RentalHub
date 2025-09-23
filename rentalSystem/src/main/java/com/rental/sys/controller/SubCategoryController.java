package com.rental.sys.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rental.sys.entities.Subcategory;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.SubCategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @Operation(summary = "Create a new subcategory", description = "Adds a new subcategory under a category.")
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public RestResponse createSubCategory(@RequestBody Subcategory subCategory) {
        try {
            Subcategory savedSubCategory = subCategoryService.createSubCategory(subCategory);
            return RestResponse.build().withSuccess("SubCategory created successfully", savedSubCategory);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
    @Operation(summary = "Get subcategory by ID", description = "Fetch a subcategory by its ID.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public RestResponse getSubCategoryById(@PathVariable Integer id) {
        try {
            return subCategoryService.getSubCategoryById(id)
                    .map(subCategory -> RestResponse.build().withSuccess("SubCategory fetched successfully", subCategory))
                    .orElse(RestResponse.build().withError("SubCategory not found"));
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Get subcategories by category ID", description = "Fetch all subcategories under a specific category.")
    @GetMapping(value = "/category/{categoryId}", produces = "application/json")
    public RestResponse getSubCategoriesByCategoryId(@PathVariable Integer categoryId) {
        try {
            List<Subcategory> subCategories = subCategoryService.getSubCategoriesByCategory(categoryId);
            return RestResponse.build().withSuccess("SubCategories fetched successfully", subCategories);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Update subcategory", description = "Update an existing subcategory by ID.")
    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public RestResponse updateSubCategory(@PathVariable Integer id, @RequestBody Subcategory subCategory) {
        try {
            Subcategory updatedSubCategory = subCategoryService.updateSubCategory(id, subCategory);
            return RestResponse.build().withSuccess("SubCategory updated successfully", updatedSubCategory);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Delete subcategory", description = "Delete an existing subcategory by ID.")
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public RestResponse deleteSubCategory(@PathVariable Integer id) {
        try {
            subCategoryService.deleteSubCategory(id);
            return RestResponse.build().withSuccess("SubCategory deleted successfully");
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
}
