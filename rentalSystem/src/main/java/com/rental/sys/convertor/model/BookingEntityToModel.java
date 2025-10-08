package com.rental.sys.convertor.model;

import org.springframework.stereotype.Component;

import com.rental.sys.entities.Booking;
import com.rental.sys.model.response.BookingResponse;


@Component
public class BookingEntityToModel {

	    public BookingResponse toModel(Booking booking) {
	        if (booking == null) return null;
	        BookingResponse model = new BookingResponse(booking);
	        model.setId(booking.getId());
	        model.setStartDate(booking.getFromDate());
	        model.setEndDate(booking.getToDate());
	        model.setStatus(booking.getStatus());
	        model.setTotalAmount(booking.getTotalAmount());
	        model.setIsPaymentDone(booking.getIsPaymentDone());
            model.setBuyerName(booking.getUser().getName());
            model.setProductName(booking.getProduct().getName());
            model.setRentDurationDays(booking.getRentDurationDays());
	        return model;
	    }
	}
