package com.rental.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.model.response.CategoryDetailsResponse;
import com.rental.sys.model.response.CategoryResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create a new category", description = "Adds a new category to the system.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse createCategory(
            @RequestPart("name") String name,
            @RequestPart("image") MultipartFile image) {
        try {
            CategoryResponse categoryResponse = categoryService.createCategory(name, image);
            return RestResponse.build()
                    .withSuccess("Category created successfully", categoryResponse);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
    
    @Operation(summary = "Get all categories", description = "Fetch all categories available in the system.")
    @GetMapping(value = "/all", produces = "application/json")
    public RestResponse findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        try {
            List<CategoryResponse> categoryResponse = categoryService.findAllCategories(page, size);
            return RestResponse.build().withSuccess("Categories fetched successfully", categoryResponse);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Get category by ID", description = "Fetch a category by its ID.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public RestResponse getCategoryById(@PathVariable Integer id) {
        try {
        	CategoryResponse categoryResponse = categoryService.findById(id);
            return RestResponse.build().withSuccess("Category fetched successfully", categoryResponse) ;     
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public RestResponse getCategoryDetails(@PathVariable Integer id) {
        try {
            // Returns category + sub-categories + products
            CategoryDetailsResponse response = categoryService.getCategoryDetails(id);
            return RestResponse.build().withSuccess("Category details fetched", response);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
    
//    @Operation(summary = "Update category", description = "Update an existing category by ID.")
//    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
//    public RestResponse updateCategory(@PathVariable Integer id,@RequestParam("name") String name) {
//        try {
//            Category updatedCategory = categoryService.updateCategory(id, name);
//            return RestResponse.build().withSuccess("Category updated successfully", updatedCategory);
//        } catch (Exception e) {
//            return RestResponse.build().withError(e.getMessage());
//        }
//    }

//    @Operation(summary = "Delete category", description = "Delete an existing category by ID.")
//    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
//    public RestResponse deleteCategory(@PathVariable Integer id) {
//        try {
//            categoryService.deleteCategory(id);
//            return RestResponse.build().withSuccess("Category deleted successfully");
//        } catch (Exception e) {
//            return RestResponse.build().withError(e.getMessage());
//        }
//    }
}