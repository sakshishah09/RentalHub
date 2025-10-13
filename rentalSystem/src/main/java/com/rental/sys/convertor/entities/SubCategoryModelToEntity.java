package com.rental.sys.convertor.entities;

import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.entities.Category;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.repo.CategoryRepo;

@Component
public class SubCategoryModelToEntity {
	 @Autowired
	    private CategoryRepo categoryRepo;

	    private final String SUBCATEGORY_IMG_DIR = "storage/subcategoryImages/";

	    public Subcategory createSubCategory(String name, Integer categoryId, MultipartFile image) throws Exception {
	        Category category = categoryRepo.findById(categoryId)
	                .orElseThrow(() -> new Exception("Category not found"));

	        Subcategory subcategory = new Subcategory();
	        subcategory.setName(name);
	        subcategory.setCategory(category);

	        // Save image
	        if (image != null && !image.isEmpty()) {
	            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
	            Path filePath = Paths.get(SUBCATEGORY_IMG_DIR + fileName);
	            Files.createDirectories(filePath.getParent());
	            Files.write(filePath, image.getBytes());
	            subcategory.setImagePath(filePath.toString());
	        }

	        return subcategory;
	    }
}
