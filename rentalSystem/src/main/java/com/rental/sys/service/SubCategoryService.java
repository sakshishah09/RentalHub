package com.rental.sys.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.convertor.entities.SubCategoryModelToEntity;
import com.rental.sys.convertor.model.SubCategoryEntityToModel;
import com.rental.sys.entities.Category;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.model.response.SubCategoryResponse;
import com.rental.sys.repo.CategoryRepo;
import com.rental.sys.repo.SubCategoryRepo;

@Service
public class SubCategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Autowired
	private SubCategoryModelToEntity subCategoryModelToEntity;

	@Autowired
	private SubCategoryEntityToModel subCategoryEntityToModel;

	// Create subcategory
	public SubCategoryResponse createSubCategory(String name, Integer categoryId, MultipartFile image)
			throws Exception {
		Subcategory subcategory = subCategoryModelToEntity.createSubCategory(name, categoryId, image);
		Subcategory saved = subCategoryRepo.save(subcategory);
		return subCategoryEntityToModel.convertToResponse(saved);
	}

	public SubCategoryResponse updateSubCategory(Integer id, String name, Integer categoryId, MultipartFile image) throws Exception {
	    Subcategory subcategory = subCategoryRepo.findById(id)
	            .orElseThrow(() -> new Exception("SubCategory not found with id " + id));

	    // Update name
	    if (name != null && !name.isEmpty()) {
	        subcategory.setName(name);
	    }

	    // Update category
	    if (categoryId != null) {
	        Category category = categoryRepo.findById(categoryId)
	                .orElseThrow(() -> new Exception("Category not found with id " + categoryId));
	        subcategory.setCategory(category);
	    }

	    // Update image
	    if (image != null && !image.isEmpty()) {
	        String imagePath = saveImage(image);
	        subcategory.setImagePath(imagePath);
	    }

	    Subcategory updated = subCategoryRepo.save(subcategory);
	    return subCategoryEntityToModel.convertToResponse(updated);
	}

	private String saveImage(MultipartFile image) throws Exception {
		String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
		Path path = Paths.get("storage/subcategory/" + filename);
		Files.createDirectories(path.getParent());
		Files.write(path, image.getBytes());
		return path.toString();
	}

	public List<SubCategoryResponse> findAllSubCategories(Integer page, Integer size) throws Exception {
		List<Subcategory> subcategoryList = subCategoryRepo.findAllSubCategories(PageRequest.of(page, size));
		return subCategoryEntityToModel.convertToList(subcategoryList);
	}

	public long countAllSubCategories() {
		return subCategoryRepo.count();
	}

	public List<SubCategoryResponse> getSubCategoriesByCategory(Integer categoryId) {
		List<Subcategory> list = subCategoryRepo.findByCategoryId(categoryId);
		return list.stream().map(subCategoryEntityToModel::convertToResponse).collect(Collectors.toList());
	}

	public void deleteSubCategory(Integer id) {
		subCategoryRepo.deleteById(id);
	}
}