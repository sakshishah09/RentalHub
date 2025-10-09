package com.rental.sys.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ObjectMapper objectMapper;

	private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Operation(summary = "Create a new product", description = "Creates a new product with image upload.")
	@PostMapping(value="/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RestResponse createProduct(@RequestPart("product") String productJson,
			@RequestPart("images") List<MultipartFile> images) {
		try {
			ProductSaveRequestModel productSaveRequestModel = objectMapper.readValue(productJson,
					ProductSaveRequestModel.class);
			ProductResponse productResponse = productService.createProduct(productSaveRequestModel, images);
			return RestResponse.build().withSuccess("Product created successfully", productResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.build().withError(e.getMessage());
		}
	}

	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RestResponse updateProduct(@RequestPart("product") String productJson,
			@RequestPart(value = "imagesToUpdate", required = false) Map<String, MultipartFile> imagesToUpdateRaw,
			@RequestPart(value = "newImages", required = false) List<MultipartFile> newImages) {
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

	@RequestMapping(method = RequestMethod.GET, value = "/filterBy", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse filterBy(
	        // Required params for pagination
	        @RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
	        @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,

	        // Filters based on Product entity fields
	        @RequestParam(value = "categoryName", required = false) String categoryName,  // Matches Category.name
	        @RequestParam(value = "subcategoryName", required = false) String subcategoryName,  // Matches Subcategory.name
	        @RequestParam(value = "productName", required = false) String productName,  // Matches Product.name
	        @RequestParam(value = "description", required = false) String description,  // Matches Product.description (exact or partial)
	        @RequestParam(value = "color", required = false) String color,  // Matches Product.color
	       // @RequestParam(value = "size", required = false) String size,  // Matches Product.size
	        @RequestParam(value = "minRentalPrice", required = false) BigDecimal minRentalPrice,  // For pricePerDay
	        @RequestParam(value = "maxRentalPrice", required = false) BigDecimal maxRentalPrice,  // For pricePerDay
	        @RequestParam(value = "minSalePrice", required = false) BigDecimal minSalePrice,  // For priceForSale
	        @RequestParam(value = "maxSalePrice", required = false) BigDecimal maxSalePrice,  // For priceForSale
	     //   @RequestParam(value = "available", required = false) Boolean available,  // Matches Product.available (true/false)
	       // @RequestParam(value = "isSold", required = false) Boolean isSold,  // Matches Product.isSold (true/false)
	        @RequestParam(value = "keyword", required = false) String keyword  // For full-text search across name, description, etc.
	) {
	    // Log all params (grouped for readability)
	    LOGGER.info("Fetching products by filter - Pagination: page={}, size={}", page, size);
	    LOGGER.info("Category/Subcategory filters: categoryName={}, subcategoryName={}", categoryName, subcategoryName);
	    LOGGER.info("Product details filters: productName={}, description={}, color={}, size={}", 
	            productName, description, color);
	    LOGGER.info("Price filters: minRentalPrice={}, maxRentalPrice={}, minSalePrice={}, maxSalePrice={}", 
	            minRentalPrice, maxRentalPrice, minSalePrice, maxSalePrice);
	    LOGGER.info("Availability filters: available={}, isSold={}, keyword={}",  keyword);

	    try {
	        // Assuming productService.filterBy is updated to handle these params
	        // (e.g., productService.filterBy(page, size, categoryName, subcategoryName, productName, description, 
	        //  color, size, minRentalPrice, maxRentalPrice, minSalePrice, maxSalePrice, available, isSold, keyword))
	        List<ProductResponse> productResponseModels = productService.filterBy(page, size, categoryName, subcategoryName, 
	                productName, description, color, minRentalPrice, maxRentalPrice, minSalePrice, maxSalePrice, 
	                keyword);
	        
	        // Assuming productService.countBy is updated similarly (without pagination)
	        long totalRecord = productService.countBy(categoryName, subcategoryName, productName, description, 
	                color,  minRentalPrice, maxRentalPrice, minSalePrice, maxSalePrice,  keyword);
	        
	        if (productResponseModels.isEmpty()) {
	            return RestResponse.build().withError("No products found matching the provided criteria.");
	        } else {
	            return RestResponse.build().withSuccess("Product list found successfully")
	                    .withTotalRecords(totalRecord)
	                    .withPageNumber(page)
	                    .withPageSize(size)
	                    .withData(productResponseModels);
	        }
	    } catch (Exception e) {
	        LOGGER.error("Failed to find product list due to: {}", e.getMessage(), e);
	        return RestResponse.build().withError("An error occurred while filtering products.");  // Avoid exposing full exception message
	    }
	}

//	    @RequestMapping(method = RequestMethod.GET, value = "/filterBy", produces = MediaType.APPLICATION_JSON_VALUE)
//	    public RestResponse filterBy(
//	            // Required params (as in your example)
//	            @RequestParam(value = "page", required = true,defaultValue = "0") Integer page,
//	            @RequestParam(value = "size", required = true,defaultValue = "10") Integer size,
//
//	            // Original params from your example
//	            @RequestParam(value = "type", required = false) String type,
//	            @RequestParam(value = "categoryName", required = false) String categoryName,
//	            @RequestParam(value = "productName", required = false) String productName,
//	            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
//	            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
//	            @RequestParam(value = "keyword", required = false) String keyword
//	            // Additional params for Product fields (to match logging/service calls)
//	              ) {
//	        // Log all params (grouped for readability; adjust as needed)
//	        LOGGER.info("Fetching products by filter - Pagination: page={}, size={}", page, size);
//	        LOGGER.info("Basic filters: type={}, categoryName={}, productName={}, minPrice={}, maxPrice={}, keyword={}", 
//	                type, categoryName, productName, minPrice, maxPrice, keyword);
//	    
//	        LOGGER.info("Fetching product by filter page:{},size:{},type:{},categoryName:{},productName:{},minPrice:{},maxPrice:{},keyword:{}",
//	                page, size, type, categoryName, productName, minPrice, maxPrice, keyword);
//	        try {
//	            List<ProductResponse> productResponseModels = productService.filterBy(page, size, type,
//	                    categoryName, productName, minPrice, maxPrice, keyword);
//	            long totalRecord = productService.countBy(type, categoryName, productName, minPrice, maxPrice, keyword);
//	            if (productResponseModels.isEmpty()) {
//	                return RestResponse.build().withError("No products found matching the provided criteria.");
//	            } else {
//	                return RestResponse.build().withSuccess("Product list found successfully").withTotalRecords(totalRecord)
//	                        .withPageNumber(page).withPageSize(size).withData(productResponseModels);
//	            }
//	        } catch (Exception e) {
//	            LOGGER.error("Failed to find product list due to: {}", e.getMessage(), e);
//	            return RestResponse.build().withError(e.getMessage());
//	        }
//	    }
	


}
