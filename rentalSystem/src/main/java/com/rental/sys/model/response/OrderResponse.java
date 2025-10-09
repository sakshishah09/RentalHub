package com.rental.sys.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderResponse {
	private Integer id;
	private String productName;
	private String buyerName;
	private String sellerName;
	private Integer quantity;
	private BigDecimal totalPrice;
	private String status;
	private LocalDateTime createdAt;
}
