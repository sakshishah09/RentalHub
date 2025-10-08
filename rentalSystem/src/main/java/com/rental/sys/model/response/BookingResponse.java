package com.rental.sys.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.rental.sys.entities.Booking;
import com.rental.sys.entities.BookingStatus;

import lombok.Data;
@Data
public class BookingResponse {
	 private int id;
	 private int userId;
	 private int productId;
	 private String buyerName;
	 private String productName;
	 private LocalDate startDate;
	 private LocalDate endDate;
	 private Integer rentDurationDays;
	 private boolean IsPaymentDone;
	 private BookingStatus  status;
	 private BigDecimal totalAmount;
	 
	 public BookingResponse(Booking booking) {
	        if (booking == null) return;

	        this.id = booking.getId();

	        // defensive / null-safe access for LAZY associations
	        if (booking.getProduct() != null) {
	            try { this.productName = booking.getProduct().getName(); this.productId = booking.getProduct().getId(); } catch (Exception ex) { this.productName = null; }
	        }
	        if (booking.getUser() != null) {
	            try { this.buyerName = booking.getUser().getName(); } catch (Exception ex) { this.buyerName = null; }
	        }
	        this.startDate = booking.getFromDate();
	        this.endDate = booking.getToDate();
	        this.rentDurationDays = booking.getRentDurationDays();
	        this.totalAmount = booking.getTotalAmount();
	        this.status = booking.getStatus();
	    }

}
