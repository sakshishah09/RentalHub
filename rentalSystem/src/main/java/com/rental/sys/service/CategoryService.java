package com.rental.sys.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.convertor.entities.CategoryModelToEntity;
import com.rental.sys.convertor.model.CategoryEntityToModel;
import com.rental.sys.entities.Category;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.model.response.CategoryDetailsResponse;
import com.rental.sys.model.response.CategoryResponse;
import com.rental.sys.model.response.ProductResponse;
import com.rental.sys.model.response.SubCategoryResponse;
import com.rental.sys.repo.CategoryRepo;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.SubCategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepository;

	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Autowired
	private ProductRepo productRepository;

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

	public CategoryDetailsResponse getCategoryDetails(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found"));

		List<Subcategory> subcategories = subCategoryRepo.findByCategoryId(categoryId);

		List<SubCategoryResponse> subcategoryResponses = subcategories.stream().map(sub -> {
			// Use the ProductResponse constructor that accepts Product entity
			List<ProductResponse> products = productRepository.findBySubcategoryId(sub.getId()).stream()
					.map(ProductResponse::new)
					.collect(Collectors.toList());

			SubCategoryResponse subResp = new SubCategoryResponse();
			subResp.setId(sub.getId());
			subResp.setName(sub.getName());
			subResp.setCategoryId(category.getId());
			subResp.setCategoryName(category.getName());
			subResp.setProducts(products);
			return subResp;
		}).collect(Collectors.toList());

		CategoryDetailsResponse response = new CategoryDetailsResponse();
		response.setId(category.getId());
		response.setName(category.getName());
		response.setImagePath(category.getImagePath());
		response.setSubcategories(subcategoryResponses);

		return response;
	}
}