package com.rental.sys.model.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderSaveRequestModel {

	private Integer productId;
	private Integer userId;
	private BigDecimal amount;
	private Integer quantity;
	private BigDecimal totalPrice;

}
