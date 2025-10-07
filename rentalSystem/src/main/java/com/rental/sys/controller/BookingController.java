package com.rental.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rental.sys.entities.Booking;
import com.rental.sys.entities.Order;
import com.rental.sys.model.request.BookingSaveRequestModel;
import com.rental.sys.model.request.BookingStatusUpdateRequest;
import com.rental.sys.model.request.OrderSaveRequestModel;
import com.rental.sys.model.response.BookingResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class BookingController {
	@Autowired
	private BookingService bookingService;
	
	

//	@Operation(summary = "create a new booking", description = "Creates a new booking.")
//	@PostMapping(value = "/booking")
//	public RestResponse createBooking(@RequestBody BookingSaveRequestModel bookingSaveRequestModel) {
//		try {
//			System.out.println("Received bookingSaveRequestModel: ");
//			BookingResponse responseModel = bookingService.createBooking(bookingSaveRequestModel);
//			return RestResponse.build().withSuccess("Booking created successfully", responseModel);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return RestResponse.build().withError(e.getMessage());
//		}
//
//	}

	// FIND BY ID
	@Operation(summary = "Get booking by ID", description = "Retrieve booking details by ID.")
	@GetMapping("/{id}")
	public RestResponse getBookingById(@PathVariable Long id) {
		try {
			BookingResponse responseModel = bookingService.FindBookingById(id);
			return RestResponse.build().withSuccess("Booking fetched successfully", responseModel);
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.build().withError(e.getMessage());
		}
	}

	@Operation(summary = "Get all rent bookings with pagination", description = "Retrieve all bookings where status = RENT, with pagination support.")
	@GetMapping("/rent")
	public RestResponse getRentBookings(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return RestResponse.build().withSuccess("Rent bookings fetched successfully",
				bookingService.findAllRentBookings(page, size));
	}

	@Operation(summary = "Get all buy bookings with pagination", description = "Retrieve all bookings where status = BUY, with pagination support.")
	@GetMapping("/buy")
	public RestResponse getBuyBookings(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return RestResponse.build().withSuccess("Buy bookings fetched successfully",
				bookingService.findAllBuyBookings(page, size));
	}

	@Operation(summary = "Update booking", description = "Update an existing booking by ID.")
	@PutMapping("/{id}")
	public RestResponse updateBooking(@PathVariable Long id,
			@RequestBody BookingSaveRequestModel bookingUpdateRequest) {
		try {
			BookingResponse updatedBooking = bookingService.updateBooking(id, bookingUpdateRequest);
			return RestResponse.build().withSuccess("Booking updated successfully", updatedBooking);
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.build().withError(e.getMessage());
		}
	}

	
//	PENDING: “Waiting for confirmation”
//	RENTED/ONGOING: “Currently rented”
//	COMPLETED: “Rental completed”
//
//	NOT_RETURNED: “Overdue / Not returned”
//
//	CANCELLED: “Cancelled”
	
	@Operation(summary = "Update booking status by admin", description = "Admin can update booking status like COMPLETED, NOT_RETURNED, CANCELLED.")
	@PutMapping("/{id}/status")
	public RestResponse updateBookingStatus(
	        @PathVariable Long id,
	        @RequestBody BookingStatusUpdateRequest request) {
	    try {
	        BookingResponse updatedBooking = bookingService.updateBookingStatus(id, request.getStatus());
	        return RestResponse.build().withSuccess("Booking status updated successfully", updatedBooking);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return RestResponse.build().withError(e.getMessage());
	    }
	}

	
//this is for rent
	    @PostMapping("/booking")
	    @Operation(summary="Create rental booking", description="Creates a rental booking if product available")
	    public RestResponse createBooking(@RequestBody BookingSaveRequestModel req) {
	        try {
	            Booking booking = bookingService.createBooking(req);
	            return RestResponse.build().withSuccess("Booking created successfully", booking);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return RestResponse.build().withError(e.getMessage());
	        }
	    }
	
// this is for buy
	    @PostMapping("/order")
	    @Operation(summary="Create order (buy)", description="Creates a buy order if product not sold")
	    public RestResponse createOrder(@RequestBody OrderSaveRequestModel req) {
	        try {
	            Order order = bookingService.createOrder(req);
	            return RestResponse.build().withSuccess("Order created successfully", order);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return RestResponse.build().withError(e.getMessage());
	        }
	    }
	

}