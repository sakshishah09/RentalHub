package com.rental.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rental.sys.entities.Category;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create a new category", description = "Adds a new category to the system.")
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public RestResponse createCategory(@RequestParam("name") String name) {
        try {
            Category savedCategory = categoryService.createCategory(name);
            return RestResponse.build().withSuccess("Category created successfully", savedCategory);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
    
    @Operation(summary = "Get all categories", description = "Fetch all categories available in the system.")
    @GetMapping(value = "/all", produces = "application/json")
    public RestResponse getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return RestResponse.build().withSuccess("Categories fetched successfully", categories);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Get category by ID", description = "Fetch a category by its ID.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public RestResponse getCategoryById(@PathVariable Integer id) {
        try {
            return categoryService.getCategoryById(id)
                    .map(category -> RestResponse.build().withSuccess("Category fetched successfully", category))
                    .orElse(RestResponse.build().withError("Category not found"));
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Update category", description = "Update an existing category by ID.")
    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public RestResponse updateCategory(@PathVariable Integer id,@RequestParam("name") String name) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, name);
            return RestResponse.build().withSuccess("Category updated successfully", updatedCategory);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Delete category", description = "Delete an existing category by ID.")
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public RestResponse deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            return RestResponse.build().withSuccess("Category deleted successfully");
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
}

