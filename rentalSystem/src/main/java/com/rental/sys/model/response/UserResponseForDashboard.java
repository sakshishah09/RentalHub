package com.rental.sys.model.response;

import java.math.BigDecimal;

import java.sql.Timestamp; // 

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserResponseForDashboard {
	
	private Integer userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String role; // SELLER / BUYER
    private Long totalProducts;
    private Long totalBookings;
    private Long totalOrders;      // ✅ new field
    private BigDecimal totalEarnings;
    private String status;
    private Timestamp createdAt;

}
