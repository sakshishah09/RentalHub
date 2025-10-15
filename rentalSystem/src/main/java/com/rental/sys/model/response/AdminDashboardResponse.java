package com.rental.sys.model.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AdminDashboardResponse {
	
	 private long totalUsers;
	    private long totalSellers;
	    private long totalBuyers;
	    private long totalProducts;
	    private long totalBookings;
	    private long totalOrders;
	    private long totalReturns;
	    private BigDecimal totalEarningsOverall; // sum of all seller earnings

	    private List<UserResponseForDashboard> sellersSummary; 

}
