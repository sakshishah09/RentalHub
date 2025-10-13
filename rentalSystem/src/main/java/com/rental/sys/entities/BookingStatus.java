package com.rental.sys.entities;

public enum BookingStatus {
	
	    PENDING_PAYMENT, // booking created but not paid
	    CONFIRMED,       // payment done and booking valid
	    CANCEL_REQUESTED,// user requested cancel
	    CANCELLED,       // seller approved cancellation
	    REJECTED,        // seller rejected cancellation
	    RENTED,          // product handed to user
	    RETURNED,        // product returned successfully
	    NOT_RETURNED     // return date passed but product not returned
}
