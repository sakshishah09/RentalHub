package com.rental.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.sys.model.request.ProductSaveRequestModel;
import com.rental.sys.model.request.ProductUpdateRequestModel;
import com.rental.sys.model.response.ProductResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ProductController {
	@Autowired
    private ProductService productService;	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Operation(summary = "Create a new product", description = "Creates a new product with image upload.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse createProduct(@RequestPart("product")String productJson, @RequestPart("images") List<MultipartFile> images) {
		try {
			ProductSaveRequestModel productSaveRequestModel = objectMapper.readValue(productJson, ProductSaveRequestModel.class);
			ProductResponse productResponse = productService.createProduct(productSaveRequestModel, images);
			 return RestResponse.build().withSuccess("Product created successfully", productResponse);
		}catch(Exception e){
			e.printStackTrace();
			 return RestResponse.build().withError(e.getMessage());
		}
	}
	 @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public RestResponse updateProduct(
	        @RequestPart("product") String productJson,
	        @RequestPart(value = "imagesToUpdate", required = false) Map<String, MultipartFile> imagesToUpdateRaw,
	        @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages
	    ) {
	        try {
	            // Convert JSON to ProductUpdateRequestModel
	            ProductUpdateRequestModel request = objectMapper.readValue(productJson, ProductUpdateRequestModel.class);
	            // Convert String keys to Integer in imagesToUpdate
	            Map<Integer, MultipartFile> imagesToUpdate = null;
	            if (imagesToUpdateRaw != null) {
	                imagesToUpdate = new HashMap<>();
	                for (Map.Entry<String, MultipartFile> entry : imagesToUpdateRaw.entrySet()) {
	                    imagesToUpdate.put(Integer.parseInt(entry.getKey()), entry.getValue());
	                }
	            }
	            ProductResponse updatedProduct = productService.updateProduct(request, imagesToUpdate, newImages);
	            return RestResponse.build().withSuccess("Product updated successfully", updatedProduct);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return RestResponse.build().withError(e.getMessage());
	        }
	    }
	@RequestMapping(method = RequestMethod.GET, value = "/findById", produces = "application/json")
	public RestResponse findById(@RequestParam("id") Integer id) {
		try {
			ProductResponse productResponse = productService.findById(id);
			return RestResponse.build().withSuccess("product found successfully", productResponse);
		} catch (Exception e) {
			return RestResponse.build().withError(e.getMessage());
		}
	}
	@RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = "application/json")
	public RestResponse findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
//		LOGGER.info("Fetching all product");
		try {
			List<ProductResponse> productResponse = productService.findAllProduct(page, size);
			long totalRecord = productService.countAllProduct();
			return RestResponse.build().withSuccess("product list found successfully").withTotalRecords(totalRecord)
					.withPageNumber(page).withPageSize(size).withData(productResponse);
		} catch (Exception e) {
//			LOGGER.error("Failed to find user list due to: {}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}
	}
	
}
