package com.rental.sys.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.rental.sys.convertor.model.BookingEntityToModel;
import com.rental.sys.entities.Booking;
import com.rental.sys.entities.BookingStatus;
import com.rental.sys.entities.Product;
import com.rental.sys.entities.ProductBlockedDate;
import com.rental.sys.entities.Order;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.BookingSaveRequestModel;
import com.rental.sys.model.request.OrderSaveRequestModel;
import com.rental.sys.model.response.BookingResponse;
import com.rental.sys.repo.BookingRepo;
import com.rental.sys.repo.OrderRepository;
import com.rental.sys.repo.ProductBlockedDateRepo;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.UserRepo;
import com.rental.sys.response.PageResponse;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
@Service
public class BookingService {
	@Autowired
	private BookingRepo bookingRepo;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ProductBlockedDateRepo blockedDateRepo;
	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private BookingEntityToModel bookingEntityToModel;
	
	@Transactional
	public BookingResponse createBooking(BookingSaveRequestModel req) {
	    // 1️⃣ Validate user & product
	    User user = userRepo.findById(req.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    Product product = productRepo.findById(req.getProductId())
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    if (product.getIsSold() != null && product.getIsSold())
	        throw new RuntimeException("Product already sold");

	    LocalDate from = req.getFromDate();
	    LocalDate to = req.getToDate();
	    if (to.isBefore(from)) throw new RuntimeException("Invalid date range");

	    // 2️⃣ Check for overlapping bookings
	    List<Booking> overlap = bookingRepo.findOverlappingBookings(product.getId(), from, to,
	            List.of("CONFIRMED", "RENTED"));
	    if (!overlap.isEmpty())
	        throw new RuntimeException("Product already booked for selected dates");

	    // 3️⃣ Calculate total
	    long days = ChronoUnit.DAYS.between(from, to);
	    if (days <= 0) days = 1;
	    BigDecimal totalAmount = product.getPricePerDay().multiply(BigDecimal.valueOf(days));

	    // 4️⃣ Create booking
	    Booking booking = new Booking();
	    booking.setProduct(product);
	    booking.setUser(user);
	    booking.setSeller(product.getUser());
	    booking.setFromDate(from);
	    booking.setToDate(to);
	    booking.setTotalAmount(totalAmount);
	    booking.setStatus(BookingStatus.PENDING_PAYMENT);
	    booking.setCreatedAt(LocalDateTime.now());
	    booking.setIsPaymentDone(false);

	    booking = bookingRepo.save(booking);

	    // 5️⃣ Simulate payment (for now)
	    boolean mockPaymentSuccess = true; // simulate a successful payment
	    if (mockPaymentSuccess) {
	        booking.setIsPaymentDone(true);
	        booking.setStatus(BookingStatus.CONFIRMED);
	        bookingRepo.save(booking);
	    }

	    return bookingEntityToModel.toModel(booking);
	}
	public BookingResponse requestCancel(Integer bookingId, Integer userId) {
	    Booking booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));
	    if (!booking.getUser().equals(userId))
	        throw new RuntimeException("Unauthorized cancel request");

	    if (!booking.getStatus().equals(BookingStatus.CONFIRMED))
	        throw new RuntimeException("Only confirmed bookings can be cancelled");

	    booking.setStatus(BookingStatus.CANCEL_REQUESTED);
	    return bookingEntityToModel.toModel(bookingRepo.save(booking));
	}

	public BookingResponse handleCancelRequest(Integer bookingId, boolean approve, Integer sellerId) {
	    Booking booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    Integer sellerOfProduct = booking.getProduct().getUser().getId();
	    if (!sellerOfProduct.equals(sellerId))
	        throw new RuntimeException("Unauthorized seller");

	    if (approve)
	        booking.setStatus(BookingStatus.CANCELLED);
	    else
	        booking.setStatus(BookingStatus.REJECTED);

	    return bookingEntityToModel.toModel(bookingRepo.save(booking));
	}

	public BookingResponse returnBooking(Integer bookingId) {
	    Booking booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    LocalDate today = LocalDate.now();
	    if (today.isAfter(booking.getToDate())) {
	        booking.setStatus(BookingStatus.NOT_RETURNED);
	        // Simulate deduction of late fees
	        BigDecimal fine = BigDecimal.valueOf(100); // flat late fee for now
	        booking.setTotalAmount(booking.getTotalAmount().add(fine));
	    } else {
	        booking.setStatus(BookingStatus.RETURNED);
	    }

	    return bookingEntityToModel.toModel(bookingRepo.save(booking));
	}
	

	public PageResponse<BookingResponse> getBookingsByBuyer(Integer buyerId, int page, int size, String sortBy, String direction) {
	    Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	    Pageable pageable = PageRequest.of(page, size, sort);
	    Page<Booking> bookingPage = bookingRepo.findByUserId(buyerId, pageable);

	    return new PageResponse<>(
	            bookingPage.getContent().stream().map(BookingResponse::new).collect(Collectors.toList()),
	            bookingPage.getNumber(),
	            bookingPage.getSize(),
	            bookingPage.getTotalElements(),
	            bookingPage.getTotalPages()
	    );
	}

	public PageResponse<BookingResponse> getBookingsBySeller(Integer sellerId, int page, int size, String sortBy, String direction) {
	    Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	    Pageable pageable = PageRequest.of(page, size, sort);
	    Page<Booking> bookingPage = bookingRepo.findBySellerId(sellerId, pageable);
	    return new PageResponse<>(
	            bookingPage.getContent().stream().map(BookingResponse::new).collect(Collectors.toList()),
	            bookingPage.getNumber(),
	            bookingPage.getSize(),
	            bookingPage.getTotalElements(),
	            bookingPage.getTotalPages()
	    );
	}

}
