package com.rental.sys.model.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingSaveRequestModel {
	
	private Integer userId;
	private Integer productId;
	private LocalDate fromDate;  // optional
	private LocalDate toDate;    // optional
    private String status;
	// Getters & Setters
}
