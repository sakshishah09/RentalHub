package com.rental.sys.model.response;

import lombok.Data;

@Data
public class WishlistResponse {
	 private int id;
	 private int userId;
	 private int productId;
	 private String productName;  // optional
	 private String productDescription; // optional
	 private String productAvailable;
}
