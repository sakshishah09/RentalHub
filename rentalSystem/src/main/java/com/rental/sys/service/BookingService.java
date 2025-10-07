package com.rental.sys.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rental.sys.convertor.model.BookingEntityToModel;
import com.rental.sys.entities.Booking;
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

import jakarta.transaction.Transactional;

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

//	public BookingResponse createBooking(BookingSaveRequestModel request) throws Exception {
//		Optional<User> userOptional = userRepo.findById(request.getUserId());
//		if (userOptional.isEmpty()) {
//			throw new Exception("The user does not exist.");
//		}
//		Optional<Product> productOptional = productRepo.findById(request.getProductId());
//		if (productOptional.isEmpty()) {
//			throw new Exception("The product does not exist.");
//		}
//		Product product = productOptional.get();
//		Booking booking = new Booking();
//		// Default Dates (today & tomorrow)
//		LocalDate fromDate = request.getFromDate() != null ? request.getFromDate() : LocalDate.now();
//		LocalDate toDate = request.getToDate() != null ? request.getToDate() : fromDate.plusDays(1);
//		booking.setUser(userOptional.get());
//		booking.setProduct(product);
//		booking.setFromDate(fromDate);
//		booking.setToDate(toDate);
//		booking.setStatus(request.getStatus());
//		BigDecimal baseAmount = product.getPricePerDay();
//		// Calculate number of days
//		long days = ChronoUnit.DAYS.between(fromDate, toDate);
//		if (days <= 0) {
//			days = 1; // at least 1 day
//		}
//		// Calculate total
//		BigDecimal totalAmount = baseAmount.multiply(BigDecimal.valueOf(days));
//		booking.setTotalAmount(totalAmount);
//		Booking savedBooking = bookingRepo.save(booking);
//		return mapToResponse(savedBooking);
//	}

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

	public BookingResponse FindBookingById(Long id) {

		Booking booking = bookingRepo.findById(id);

		return bookingEntityToModel.toModel(booking);

	}

	public Page<BookingResponse> findAllRentBookings(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Booking> bookings = bookingRepo.findByStatusIgnoreCase("RENT", pageable);
		return bookings.map(bookingEntityToModel::toModel);
	}

	public Page<BookingResponse> findAllBuyBookings(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Booking> bookings = bookingRepo.findByStatusIgnoreCase("BUY", pageable);
		return bookings.map(bookingEntityToModel::toModel);
	}

	public BookingResponse updateBooking(Long id, BookingSaveRequestModel bookingUpdateRequest) {
		// Step 1: Fetch existing booking
		Booking existingBooking = bookingRepo.findById(id.intValue())
				.orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

		// Step 2: Update allowed fields
		if (bookingUpdateRequest.getFromDate() != null) {
			existingBooking.setFromDate(bookingUpdateRequest.getFromDate());
		}

		if (bookingUpdateRequest.getToDate() != null) {
			existingBooking.setToDate(bookingUpdateRequest.getToDate());
		}

		if (bookingUpdateRequest.getStatus() != null && !bookingUpdateRequest.getStatus().isBlank()) {
			existingBooking.setStatus(bookingUpdateRequest.getStatus());
		}

		if (bookingUpdateRequest.getTotalAmount() != null) {
			existingBooking.setTotalAmount(bookingUpdateRequest.getTotalAmount());
		}

		// Optionally allow product or user update (if business logic allows)
		if (bookingUpdateRequest.getProductId() != null) {
			Product product = productRepo.findById(bookingUpdateRequest.getProductId()).orElseThrow(
					() -> new RuntimeException("Product not found with ID: " + bookingUpdateRequest.getProductId()));
			existingBooking.setProduct(product);
		}

		if (bookingUpdateRequest.getUserId() != null) {
			User user = userRepo.findById(bookingUpdateRequest.getUserId()).orElseThrow(
					() -> new RuntimeException("User not found with ID: " + bookingUpdateRequest.getUserId()));
			existingBooking.setUser(user);
		}

		existingBooking.setCreatedAt(existingBooking.getCreatedAt()); // preserve createdAt

		// Step 3: Save updated booking
		bookingRepo.save(existingBooking);

		// Step 4: Convert entity to response
		return bookingEntityToModel.toModel(existingBooking);
	}

	public BookingResponse updateBookingStatus(Long id, String status) {
		// Step 1: Fetch booking
		Booking booking = bookingRepo.findById(id.intValue())
				.orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

		// Step 2: Validate allowed statuses (admin rules)
		if (status == null || status.isBlank()) {
			throw new RuntimeException("Status cannot be empty");
		}

		String newStatus = status.toUpperCase();
		if (!newStatus.equals("COMPLETED") && !newStatus.equals("NOT_RETURNED") && !newStatus.equals("CANCELLED")) {
			throw new RuntimeException("Invalid status. Allowed: COMPLETED, NOT_RETURNED, CANCELLED");
		}

		// Step 3: Update status
		booking.setStatus(newStatus);

		// Step 4: Save
		bookingRepo.save(booking);

		// Step 5: Convert to response
		return bookingEntityToModel.toModel(booking);
	}

	private static final List<String> RENT_BLOCK_STATUSES = Arrays.asList("RESERVED","CONFIRMED","RENTED");

    @Transactional
    public Booking createBooking(BookingSaveRequestModel req) {
        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getIsSold()!=null && product.getIsSold()) throw new RuntimeException("Product already sold");

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate from = req.getFromDate();
        LocalDate to = req.getToDate();
        if (to.isBefore(from)) throw new RuntimeException("Invalid dates");

        // Check overlapping bookings
        List<Booking> overlap = bookingRepo.findOverlappingBookings(product.getId(), from, to, RENT_BLOCK_STATUSES);
        if (!overlap.isEmpty()) throw new RuntimeException("Product already booked for selected dates");

        // Check blocked dates
        List<ProductBlockedDate> blocked = blockedDateRepo.findByProductIdAndBlockedDateBetween(product.getId(), from, to);
        if (!blocked.isEmpty()) throw new RuntimeException("Product blocked for some dates");

        // Create booking
        Booking booking = new Booking();
        booking.setProduct(product);
        booking.setUser(user);
        booking.setFromDate(from);
        booking.setToDate(to);
        booking.setTotalAmount(req.getTotalAmount());
        booking.setStatus("RESERVED"); // or PENDING_PAYMENT
        booking.setCreatedAt(LocalDateTime.now());
        booking = bookingRepo.save(booking);

        // Block dates
        LocalDate d = from;
        while (!d.isAfter(to)) {
            blockedDateRepo.save(new ProductBlockedDate(product, d));
            d = d.plusDays(1);
        }

        return booking;
    }

    @Transactional
    public Order createOrder(OrderSaveRequestModel req) {
        Product product = productRepo.findById(req.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (product.getIsSold() != null && product.getIsSold())
            throw new RuntimeException("Product already sold");

        User user = userRepo.findById(req.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);
        order.setAmount(req.getAmount());
        order.setStatus("PAID");
        order.setCreatedAt(LocalDateTime.now());

        orderRepo.save(order);

        product.setIsSold(true);
        productRepo.save(product);

        return order;
    }

	
	
}
