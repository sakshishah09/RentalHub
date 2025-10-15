package com.rental.sys.model.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.entities.ProductType;

import lombok.Data;
@Data
public class ProductUpdateRequestModel {
	    private Integer id;
	    private String name;
	    private String description;
	    private String color;
	    private String size;
	    private BigDecimal pricePerDay;
	    private BigDecimal priceForSale;
	    private boolean available;
	    // Map of existing imageId -> new file (to replace)
	    private Map<Integer, MultipartFile> imagesToUpdate;

	    // New images to add
	    private List<MultipartFile> newImages;

	    private Integer categoryId;
	    private Integer subcategoryId;
	    private Integer userId;
	    private ProductType productType;
	    // getters and setters

}
