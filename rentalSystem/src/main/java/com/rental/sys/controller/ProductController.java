package com.rental.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.model.request.ProductSaveRequestModel;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
    private ProductService productService ;	

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse createProduct(
            @RequestPart("product") ProductSaveRequestModel request,
            @RequestPart("images") List<MultipartFile> images) throws Exception {
    	productService.createProduct(request, images);
				return null;
    	
    }

}
