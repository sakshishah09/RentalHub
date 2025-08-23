package com.rental.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.convertor.entities.ProductModelToEntity;
import com.rental.sys.entities.Product;
import com.rental.sys.model.request.ProductSaveRequestModel;
import com.rental.sys.repo.ProductRepo;

@Service
public class ProductService {	
     @Autowired
     private ProductRepo productRepo;
	 @Autowired
	 ProductModelToEntity productModelToEntity;
	 public void createProduct(ProductSaveRequestModel request, List<MultipartFile> images) throws Exception {
	 Product product = productModelToEntity.createProduct(request, images) ;
	 productRepo.save(product);
	}

}
