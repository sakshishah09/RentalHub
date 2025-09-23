package com.rental.sys.model.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class ProductUpdateRequestModel {
	    private int id;
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

	    private int categoryId;
	    private int subcategoryId;
	    private int userId;

	    // getters and setters

}
