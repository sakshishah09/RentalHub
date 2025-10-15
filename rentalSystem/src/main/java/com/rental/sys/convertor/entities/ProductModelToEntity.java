package com.rental.sys.convertor.entities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.entities.Category;
import com.rental.sys.entities.Product;
import com.rental.sys.entities.ProductImage;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.ProductSaveRequestModel;
import com.rental.sys.model.request.ProductUpdateRequestModel;
import com.rental.sys.repo.CategoryRepo;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.SubCategoryRepo;
import com.rental.sys.repo.UserRepo;

@Component
public class ProductModelToEntity {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private SubCategoryRepo subcategoryRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ProductRepo productRepo;

	private final String PRODUCT_IMG_DIR = "storage/productImages/";

	public Product createProduct(ProductSaveRequestModel request, List<MultipartFile> images) throws Exception {
		Optional<Category> optionalCategory = categoryRepo.findById(request.getCategoryId());
		if (!optionalCategory.isPresent()) {
			throw new Exception("The category does not exist.");
		}
		Optional<Subcategory> optionalSubcategory = subcategoryRepo.findById(request.getSubcategoryId());
		if (!optionalSubcategory.isPresent()) {
			throw new Exception("The Subcategory does not exist.");
		}
		Optional<User> optionalUser = userRepo.findById(request.getUserId());
		if (!optionalUser.isPresent()) {
			throw new Exception("The User does not exist.");
		}
		User user = optionalUser.get();
		if (!Boolean.TRUE.equals(user.getIsSeller())) {
            throw new Exception("User is not approved as a seller. Please contact admin for approval.");
        }
		// Create product
		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setSize(request.getSize());
		product.setColor(request.getColor());
		product.setPricePerDay(request.getPricePerDay());
		product.setPriceForSale(request.getPriceForSale());
		product.setProductType(request.getProductType());
		product.setCategory(optionalCategory.get());
		product.setSubcategory(optionalSubcategory.get());
		product.setUser(user);
		for (MultipartFile image : images) {
			if (!image.isEmpty()) {
				String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
				Path filePath = Paths.get(PRODUCT_IMG_DIR + fileName);
				Files.write(filePath, image.getBytes());
				ProductImage productImage = new ProductImage();
				productImage.setImageUrl(filePath.toString());
				productImage.setProduct(product);
				product.getImages().add(productImage); // 🔑 maintain bidirectional relationship
			}
		}
		return product;
	}

	public Product updateProduct(ProductUpdateRequestModel request, Map<Integer, MultipartFile> imagesToUpdate,
			List<MultipartFile> newImages) throws Exception {

		Optional<Product> optionalProduct = productRepo.findById(request.getId());
		if (!optionalProduct.isPresent()) {
			throw new Exception("Product not found");
		}
		Product product = optionalProduct.get();

		// Update product details
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setColor(request.getColor());
		product.setSize(request.getSize());
		product.setPricePerDay(request.getPricePerDay());
		product.setPriceForSale(request.getPriceForSale());
		product.setAvailable(request.isAvailable());
		product.setProductType(request.getProductType());


        // Update existing images selectively
		if (imagesToUpdate != null) {
			for (ProductImage image : product.getImages()) {
				MultipartFile file = imagesToUpdate.get(image.getId());
				if (file != null && !file.isEmpty()) {
					String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
					Path filePath = Paths.get(PRODUCT_IMG_DIR + fileName);
					Files.write(filePath, file.getBytes());
					image.setImageUrl(filePath.toString());
				}
			}
		}
       // Add new images
		if (newImages != null) {
			for (MultipartFile file : newImages) {
				if (!file.isEmpty()) {
					String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
					Path filePath = Paths.get(PRODUCT_IMG_DIR + fileName);
					Files.write(filePath, file.getBytes());
					ProductImage productImage = new ProductImage();
					productImage.setImageUrl(filePath.toString());
					productImage.setProduct(product);
					product.getImages().add(productImage);
				}
			}
		}
		return product;
	}
}
