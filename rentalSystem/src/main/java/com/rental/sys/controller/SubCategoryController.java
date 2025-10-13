package com.rental.sys.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RestResponse createSubCategory(@RequestPart("name") String name,
			@RequestPart("categoryId") Integer categoryId,
			@RequestPart(value = "image", required = false) MultipartFile image) {
		try {
			SubCategoryResponse saved = subCategoryService.createSubCategory(name, categoryId, image);
			return RestResponse.build().withSuccess("SubCategory created successfully", saved);
		} catch (Exception e) {
			return RestResponse.build().withError(e.getMessage());
		}
	}

	@Operation(summary = "Update subcategory", description = "Updates name, categoryId or image of a subcategory partially or fully.")
	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RestResponse updateSubCategory(@ModelAttribute SubCategoryUpdateRequestModel request) {
		try {
			SubCategoryResponse updated = subCategoryService.updateSubCategory(request.getId(), request.getName(),
					request.getCategoryId(), request.getImage());
			return RestResponse.build().withSuccess("SubCategory updated successfully", updated);
		} catch (Exception e) {
			return RestResponse.build().withError(e.getMessage());
		}
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

	@Operation(summary = "Get all subcategories (paginated)", description = "Fetch all subcategories with pagination.")
	@GetMapping(value = "/all", produces = "application/json")
	public RestResponse findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		try {
			List<SubCategoryResponse> subCategoryList = subCategoryService.findAllSubCategories(page, size);
			long totalRecord = subCategoryService.countAllSubCategories();
			return RestResponse.build().withSuccess("SubCategory list fetched successfully")
					.withTotalRecords(totalRecord).withPageNumber(page).withPageSize(size).withData(subCategoryList);
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