package com.rental.sys.convertor.entities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.entities.Category;
import com.rental.sys.repo.CategoryRepo;

@Component
public class CategoryModelToEntity {

    @Autowired
    private CategoryRepo categoryRepo;

    private final String CATEGORY_IMG_DIR = "storage/categoryImages/";

    // Create Category
    public Category createCategory(String name, MultipartFile image) throws Exception {
        Category category = new Category();
        category.setName(name);
        // Image save
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(CATEGORY_IMG_DIR + fileName);
            // folder create if not exists
            Files.createDirectories(filePath.getParent());
            // write file to local storage
            Files.write(filePath, image.getBytes());
            category.setImagePath(filePath.toString()); // DB me path save
        }
        return category;
    }
    // Update Category
//    public Category updateCategory(CategoryUpdateRequestModel request, MultipartFile newImage) throws Exception {
//        Optional<Category> optionalCategory = categoryRepo.findById(request.getId());
//        if (!optionalCategory.isPresent()) {
//            throw new Exception("Category not found");
//        }
//        Category category = optionalCategory.get();
//        category.setName(request.getName());
//        // Update image if new one is provided
//        if (newImage != null && !newImage.isEmpty()) {
//            String fileName = UUID.randomUUID() + "_" + newImage.getOriginalFilename();
//            Path filePath = Paths.get(CATEGORY_IMG_DIR + fileName);
//            Files.createDirectories(filePath.getParent());
//            Files.write(filePath, newImage.getBytes());
//            category.setImagePath(filePath.toString());
//        }
//        return category;
//    }
}