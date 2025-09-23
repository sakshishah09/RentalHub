package com.rental.sys.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.sys.entities.Booking;
import com.rental.sys.entities.Product;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.BookingSaveRequestModel;
import com.rental.sys.model.response.BookingResponse;
import com.rental.sys.repo.BookingRepo;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.UserRepo;
@Service
public class BookingService {
	 @Autowired
	 private BookingRepo bookingRepo;
	 @Autowired
	 private ProductRepo productRepo;
	 @Autowired
	 private UserRepo userRepo;

	public BookingResponse createBooking(BookingSaveRequestModel request) throws Exception {
		Optional<User> userOptional = userRepo.findById(request.getUserId());
		if (userOptional.isEmpty()) {
			throw new Exception("The user does not exist.");
		}
		Optional<Product> productOptional = productRepo.findById(request.getProductId());
		if (productOptional.isEmpty()) {
			throw new Exception("The product does not exist.");
		}
		Product product = productOptional.get();
	        Booking booking = new Booking();
	        // Default Dates (today & tomorrow)
	        LocalDate fromDate = request.getFromDate() != null ? request.getFromDate() : LocalDate.now();
	        LocalDate toDate = request.getToDate() != null ? request.getToDate() : fromDate.plusDays(1);
	        booking.setUser(userOptional.get());
	        booking.setProduct(product);
	        booking.setFromDate(fromDate);
	        booking.setToDate(toDate);
	        booking.setStatus(request.getStatus());
	        BigDecimal baseAmount = product.getPricePerDay();
	        // Calculate number of days
	        long days = ChronoUnit.DAYS.between(fromDate, toDate);
	        if (days <= 0) {
	            days = 1; // at least 1 day
	        }
	        // Calculate total
	        BigDecimal totalAmount = baseAmount.multiply(BigDecimal.valueOf(days));
	        booking.setTotalAmount(totalAmount);
	        Booking savedBooking = bookingRepo.save(booking);
	        return mapToResponse(savedBooking);
}     
	        private BookingResponse mapToResponse(Booking booking) {
	            BookingResponse response = new BookingResponse();
	            response.setId(booking.getId());
	            response.setUserId(booking.getUser().getId());
	            response.setProductId(booking.getProduct().getId());
	            response.setFromDate(booking.getFromDate());
	            response.setToDate(booking.getToDate());
	            response.setStatus(booking.getStatus());
	            response.setTotalAmount(booking.getTotalAmount());
	            return response;
	        }
}
