package com.rental.sys.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingSaveRequestModel {
	
	private int userId;
	private int productId;
	private LocalDate fromDate;
	private LocalDate toDate;
	
}