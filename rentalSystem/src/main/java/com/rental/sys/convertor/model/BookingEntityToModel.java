package com.rental.sys.convertor.model;

import org.springframework.stereotype.Component;

import com.rental.sys.entities.Booking;
import com.rental.sys.entities.Product;
import com.rental.sys.entities.User;
import com.rental.sys.model.response.BookingResponse;
import com.rental.sys.model.response.ProductResponse;
import com.rental.sys.model.response.UserResponse;

@Component
public class BookingEntityToModel {

	
	public BookingResponse toModel(Booking booking) {
        if (booking == null) return null;

        BookingResponse model = new BookingResponse();
        model.setId(booking.getId());
     //   model.setCreateDate(booking.getCreatedAt());
        model.setFromDate(booking.getFromDate());
        model.setToDate(booking.getToDate());
        model.setStatus(booking.getStatus());
        model.setTotalAmount(booking.getTotalAmount());

        // Convert user summary
        User user = booking.getUser();
        if (user != null) {
            UserResponse userSummary = new UserResponse();
            userSummary.setId(user.getId());
            userSummary.setName(user.getName());
            userSummary.setEmail(user.getEmail());
           // model.setUserId(userSummary);
        }

        // Convert product summary
        Product product = booking.getProduct();
        if (product != null) {
            ProductResponse productSummary = new ProductResponse();
            productSummary.setId(product.getId());
            productSummary.setName(product.getName());
            productSummary.setPricePerDay(product.getPricePerDay());
          //  model.setProduct(productSummary);
        }

        return model;
    }
	
	
	
}
