package com.rental.sys.convertor.entities;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.entities.Category;
import com.rental.sys.entities.Product;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.ProductSaveRequestModel;
import com.rental.sys.repo.CategoryRepo;
import com.rental.sys.repo.ProductImageRepo;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.SubCategoryRepo;
import com.rental.sys.repo.UserRepo;

@Component
public class ProductModelToEntity {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductImageRepo productImageRepo ;
	@Autowired
	private CategoryRepo categoryRepo ;
	@Autowired
	private SubCategoryRepo subcategoryRepo ;
	@Autowired
	private UserRepo userRepo;
	
	public Product createProduct(ProductSaveRequestModel request,List<MultipartFile> images) throws Exception {
		Optional<Category> optionalCategory = categoryRepo.findById(request.getCategoryId());
		if (!optionalCategory.isPresent()) {
			throw new Exception("The category does not exist.");
		}
		Optional<Subcategory> optionalSubcategory = subcategoryRepo.findById(request.getSubcategoryId());
		if (!optionalSubcategory.isPresent()) {
			throw new Exception("The Subcategory does not exist.");
		}
	    Optional<User> optionalUser = userRepo.findById(request.getOwnerId());
		if (!optionalSubcategory.isPresent()) {
			throw new Exception("The User does not exist.");
		}
		Product product = new Product();
		product.setName(request.getName());
	    product.setDescription(request.getDescription());
	    product.setSize(request.getSize());
	    product.setColor(request.getColor());
	    product.setPricePerDay(request.getPricePerDay());
	    product.setPriceForSale(request.getPriceForSale());
	    product.setAvailable(request.isAvailable());
	    product.setCategory(optionalCategory.get());
	    product.setSubcategory(optionalSubcategory.get());
//	    List<ProductImage> images = new ArrayList<>();
//        if (imageFiles != null && !imageFiles.isEmpty()) {
//            for (MultipartFile file : imageFiles) {
//                String imagePath = fileService.uploadFile(file, "product-images");
//                ProductImage img = new ProductImage(imagePath, product);
//                images.add(img);
//            }
//        }
//        product.setImages(images);

	    return product ;   
	}
}
