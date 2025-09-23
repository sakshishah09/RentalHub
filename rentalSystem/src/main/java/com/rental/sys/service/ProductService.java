package com.rental.sys.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.rental.sys.convertor.entities.ProductModelToEntity;
import com.rental.sys.convertor.model.ProductEntityToModel;
import com.rental.sys.entities.Product;
import com.rental.sys.model.request.ProductSaveRequestModel;
import com.rental.sys.model.request.ProductUpdateRequestModel;
import com.rental.sys.model.response.ProductResponse;
import com.rental.sys.repo.ProductRepo;

@Service
public class ProductService {	
     @Autowired
     private ProductRepo productRepo;
	 @Autowired
	 private ProductModelToEntity productModelToEntity;
	 @Autowired
	 private ProductEntityToModel productEntityToModel;
	 
	 public ProductResponse createProduct(ProductSaveRequestModel request, List<MultipartFile> images) throws Exception {
	 Product product = productModelToEntity.createProduct(request, images) ;
	 Product savedProduct=productRepo.save(product);
	 return productEntityToModel.getProductById(savedProduct);
	 }
	 public ProductResponse updateProduct(ProductUpdateRequestModel request, Map<Integer, MultipartFile> imagesToUpdate, List<MultipartFile> newImages) throws Exception {
		 Product product = productModelToEntity.updateProduct(request, imagesToUpdate, newImages) ;
		 Product savedProduct=productRepo.save(product);
		 return productEntityToModel.getProductById(savedProduct);
	 }
	 
	 public ProductResponse findById(Integer id) throws Exception {
			Optional<Product> productOptional = productRepo.findById(id);
			if (productOptional.isEmpty()) {
				throw new Exception("The product does not exist.");}
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
}