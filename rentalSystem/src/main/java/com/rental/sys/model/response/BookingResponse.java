package com.rental.sys.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class BookingResponse {
	 private int id;
	 private int userId;
	 private int productId;

	 private LocalDate fromDate;
	 private LocalDate toDate;
	 private String status;
	 private BigDecimal totalAmount;
}
