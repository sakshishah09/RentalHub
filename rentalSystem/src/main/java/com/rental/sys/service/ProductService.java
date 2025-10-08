package com.rental.sys.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.Repository.Custom.ProductRepositoryImpl;
import com.rental.sys.convertor.entities.ProductModelToEntity;
import com.rental.sys.convertor.model.ProductEntityToModel;
import com.rental.sys.entities.Product;
import com.rental.sys.model.request.ProductSaveRequestModel;
import com.rental.sys.model.request.ProductUpdateRequestModel;
import com.rental.sys.model.response.ProductResponse;
import com.rental.sys.repo.ProductRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductModelToEntity productModelToEntity;
	@Autowired
	private ProductEntityToModel productEntityToModel;
	@Autowired
	private ProductRepositoryImpl productCustomRepo;

	public ProductResponse createProduct(ProductSaveRequestModel request, List<MultipartFile> images) throws Exception {
		Product product = productModelToEntity.createProduct(request, images);
		Product savedProduct = productRepo.save(product);
		return productEntityToModel.getProductById(savedProduct);
	}

	public ProductResponse updateProduct(ProductUpdateRequestModel request, Map<Integer, MultipartFile> imagesToUpdate,
			List<MultipartFile> newImages) throws Exception {
		Product product = productModelToEntity.updateProduct(request, imagesToUpdate, newImages);
		Product savedProduct = productRepo.save(product);
		return productEntityToModel.getProductById(savedProduct);
	}

	public ProductResponse findById(Integer id) throws Exception {
		Optional<Product> productOptional = productRepo.findById(id);
		if (productOptional.isEmpty()) {
			throw new Exception("The product does not exist.");
		}
		Product product = productOptional.get();
		return productEntityToModel.getProductById(product);
	}

	public List<ProductResponse> findAllProduct(Integer page, Integer size) throws Exception {
		List<Product> productList = productRepo.findAllProduct(PageRequest.of(page, size));
		return productEntityToModel.getFindAllConvert(productList);
	}

	public long countAllProduct() throws Exception {
		return productRepo.count();
	}

	public List<ProductResponse> filterBy(Integer page, Integer size, String type, String categoryName,
			String productName, String description, String color, BigDecimal minPrice, BigDecimal maxPrice,
			BigDecimal minSalePrice, BigDecimal maxSalePrice, String keyword) {
// Pass ALL params to repo for complete filtering
		List<Product> productList = productCustomRepo.filterBy(page, size, type, categoryName, productName, description,
				color, minPrice, maxPrice, minSalePrice, maxSalePrice, keyword);
		return productEntityToModel.getFindAllConvert(productList);
	}

	public long countBy(String type, String categoryName, String productName, String description, String color,
			BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minSalePrice, BigDecimal maxSalePrice,
			String keyword) {
// Pass ALL params to repo for accurate count
		long count = productCustomRepo.countBy(type, categoryName, productName, description, color, minPrice, maxPrice,
				minSalePrice, maxSalePrice, keyword);
		log.info("Count Query Result: {}", count);
		return count;
	}
}