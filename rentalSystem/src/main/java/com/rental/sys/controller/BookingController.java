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
	
	// 1️⃣ Create a new booking
    @Operation(summary = "Create a new booking", description = "Creates a new booking and sets status to PENDING until payment is done.")
    @PostMapping
    public RestResponse createBooking(@RequestBody BookingSaveRequestModel bookingSaveRequestModel) {
        try {
            BookingResponse response = bookingService.createBooking(bookingSaveRequestModel);
            return RestResponse.build().withSuccess("Booking created successfully", response);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }

    // 2️⃣ Request cancellation by user
    @Operation(summary = "Request booking cancellation", description = "User requests to cancel a booking.")
    @PostMapping("/{bookingId}/request-cancel/{userId}")
    public RestResponse requestCancel(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        try {
            BookingResponse response = bookingService.requestCancel(bookingId, userId);
            return RestResponse.build().withSuccess("Cancel request submitted", response);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }

    // 3️⃣ Handle cancellation by seller
    @Operation(summary = "Handle booking cancellation", description = "Seller approves or rejects a cancel request.")
    @PostMapping("/{bookingId}/handle-cancel/{sellerId}")
    public RestResponse handleCancel(@PathVariable Integer bookingId,
                                     @PathVariable Integer sellerId,
                                     @RequestParam boolean approve) {
        try {
            BookingResponse response = bookingService.handleCancelRequest(bookingId, approve, sellerId);
            String message = approve ? "Cancellation approved" : "Cancellation rejected";
            return RestResponse.build().withSuccess(message, response);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }

    // 4️⃣ Return product
    @Operation(summary = "Return booking product", description = "Mark a booking as returned or NOT_RETURNED if late.")
    @PostMapping("/{bookingId}/return")
    public RestResponse returnBooking(@PathVariable Integer bookingId) {
        try {
            BookingResponse response = bookingService.returnBooking(bookingId);
            return RestResponse.build().withSuccess("Booking returned successfully", response);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }
    @Operation(summary = "Get all bookings by buyer (paginated)")
    @GetMapping("/bookings/buyer/{buyerId}")
    public RestResponse getBookingsByBuyer(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        try {
            return RestResponse.build()
                    .withSuccess("Bookings fetched successfully",
                            bookingService.getBookingsByBuyer(userId, page, size, sortBy, direction));
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Get all bookings by seller (paginated)")
    @GetMapping("/bookings/seller/{sellerId}")
    public RestResponse getBookingsBySeller(
            @PathVariable Integer sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        try {
            return RestResponse.build()
                    .withSuccess("Bookings fetched successfully",
                            bookingService.getBookingsBySeller(sellerId, page, size, sortBy, direction));
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }
}
//    // 5️⃣ Get booking by ID
//    @Operation(summary = "Get booking by ID", description = "Fetch booking details by booking ID.")
//    @GetMapping("/{bookingId}")
//    public RestResponse getBooking(@PathVariable int bookingId) {
//        try {
//            BookingResponse response = bookingService.findBookingById(bookingId);
//            return RestResponse.build().withSuccess("Booking fetched successfully", response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return RestResponse.build().withError(e.getMessage());
//        }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	   

	
//	    @Operation(summary = "Create a new booking", description = "Creates a new booking and sets status to PENDING until payment is done.")
//	    @PostMapping("/booking")
//	    public RestResponse createBooking(@RequestBody BookingSaveRequestModel bookingSaveRequestModel) {
//	        try {
//	            System.out.println("Received bookingSaveRequestModel: " + bookingSaveRequestModel);
//	            BookingResponse response = bookingService.createBooking(bookingSaveRequestModel);
//	            return RestResponse.build().withSuccess("Booking created successfully", response);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return RestResponse.build().withError(e.getMessage());
//	        }
//	    }
//
//	    @Operation(summary = "Confirm booking payment", description = "Marks booking as BOOKED after successful payment.")
//	    @PutMapping("/booking/{id}/confirm-payment")
//	    public RestResponse confirmBookingPayment(@PathVariable Long id) {
//	        try {
//	            BookingResponse response = bookingService.confirmBookingPayment(id);
//	            return RestResponse.build().withSuccess("Payment confirmed, booking completed.", response);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return RestResponse.build().withError(e.getMessage());
//	        }
//	    }
//
//	    @Operation(summary = "Cancel booking", description = "Cancels the booking before payment or start date.")
//	    @PutMapping("/booking/{id}/cancel")
//	    public RestResponse cancelBooking(@PathVariable Long id) {
//	        try {
//	            BookingResponse response = bookingService.cancelBooking(id);
//	            return RestResponse.build().withSuccess("Booking cancelled successfully", response);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return RestResponse.build().withError(e.getMessage());
//	        }
//	    }
//
//	    @Operation(summary = "Return rented item", description = "Marks booking as RETURNED after rental completion.")
//	    @PutMapping("/booking/{id}/return")
//	    public RestResponse returnBooking(@PathVariable Long id) {
//	        try {
//	            BookingResponse response = bookingService.returnBooking(id);
//	            return RestResponse.build().withSuccess("Booking marked as returned.", response);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return RestResponse.build().withError(e.getMessage());
//	        }
//	    }
	


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
//	@Operation(summary = "Get booking by ID", description = "Retrieve booking details by ID.")
//	@GetMapping("/{id}")
//	public RestResponse getBookingById(@PathVariable Long id) {
//		try {
//			BookingResponse responseModel = bookingService.FindBookingById(id);
//			return RestResponse.build().withSuccess("Booking fetched successfully", responseModel);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return RestResponse.build().withError(e.getMessage());
//		}
//	}
//
//	@Operation(summary = "Get all rent bookings with pagination", description = "Retrieve all bookings where status = RENT, with pagination support.")
//	@GetMapping("/rent")
//	public RestResponse getRentBookings(@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "10") int size) {
//		return RestResponse.build().withSuccess("Rent bookings fetched successfully",
//				bookingService.findAllRentBookings(page, size));
//	}
//
//	@Operation(summary = "Get all buy bookings with pagination", description = "Retrieve all bookings where status = BUY, with pagination support.")
//	@GetMapping("/buy")
//	public RestResponse getBuyBookings(@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "10") int size) {
//		return RestResponse.build().withSuccess("Buy bookings fetched successfully",
//				bookingService.findAllBuyBookings(page, size));
//	}
//
//	@Operation(summary = "Update booking", description = "Update an existing booking by ID.")
//	@PutMapping("/{id}")
//	public RestResponse updateBooking(@PathVariable Long id,
//			@RequestBody BookingSaveRequestModel bookingUpdateRequest) {
//		try {
//			BookingResponse updatedBooking = bookingService.updateBooking(id, bookingUpdateRequest);
//			return RestResponse.build().withSuccess("Booking updated successfully", updatedBooking);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return RestResponse.build().withError(e.getMessage());
//		}
//	}
//
//	
//	PENDING: “Waiting for confirmation”
//	RENTED/ONGOING: “Currently rented”
//	COMPLETED: “Rental completed”
//
//	NOT_RETURNED: “Overdue / Not returned”
//
//	CANCELLED: “Cancelled”
//	
//	@Operation(summary = "Update booking status by admin", description = "Admin can update booking status like COMPLETED, NOT_RETURNED, CANCELLED.")
//	@PutMapping("/{id}/status")
//	public RestResponse updateBookingStatus(
//	        @PathVariable Long id,
//	        @RequestBody BookingStatusUpdateRequest request) {
//	    try {
//	        BookingResponse updatedBooking = bookingService.updateBookingStatus(id, request.getStatus());
//	        return RestResponse.build().withSuccess("Booking status updated successfully", updatedBooking);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return RestResponse.build().withError(e.getMessage());
//	    }
//	}
//
//	
////this is for rent
//	    @PostMapping("/booking")
//	    @Operation(summary="Create rental booking", description="Creates a rental booking if product available")
//	    public RestResponse createBooking(@RequestBody BookingSaveRequestModel req) {
//	        try {
//	            Booking booking = bookingService.createBooking(req);
//	            return RestResponse.build().withSuccess("Booking created successfully", booking);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return RestResponse.build().withError(e.getMessage());
//	        }
//	    }
//	
//// this is for buy
//	    @PostMapping("/order")
//	    @Operation(summary="Create order (buy)", description="Creates a buy order if product not sold")
//	    public RestResponse createOrder(@RequestBody OrderSaveRequestModel req) {
//	        try {
//	            Order order = bookingService.createOrder(req);
//	            return RestResponse.build().withSuccess("Order created successfully", order);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return RestResponse.build().withError(e.getMessage());
//	      	
