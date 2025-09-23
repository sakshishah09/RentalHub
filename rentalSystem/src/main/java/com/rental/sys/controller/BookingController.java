package com.rental.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rental.sys.model.request.BookingSaveRequestModel;
import com.rental.sys.model.response.BookingResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class BookingController {
	@Autowired
	private BookingService bookingService;
	
	@Operation(summary = "create a new booking", description = "Creates a new booking.")
	@PostMapping(value = "/booking")
	public RestResponse createBooking(@RequestBody BookingSaveRequestModel bookingSaveRequestModel) {
	    try {
	        System.out.println("Received bookingSaveRequestModel: ");
	        BookingResponse responseModel = bookingService.createBooking(bookingSaveRequestModel);
	        return RestResponse.build().withSuccess("Booking created successfully", responseModel);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return RestResponse.build().withError(e.getMessage());
	    }
	}
	
}