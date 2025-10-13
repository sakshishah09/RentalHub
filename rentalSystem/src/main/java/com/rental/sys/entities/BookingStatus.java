package com.rental.sys.entities;

public enum BookingStatus {
<<<<<<< HEAD
	
	    PENDING_PAYMENT, // booking created but not paid
	    CONFIRMED,       // payment done and booking valid
	    CANCEL_REQUESTED,// user requested cancel
	    CANCELLED,       // seller approved cancellation
	    REJECTED,        // seller rejected cancellation
	    RENTED,          // product handed to user
	    RETURNED,        // product returned successfully
	    NOT_RETURNED     // return date passed but product not returned
}
=======

	PENDING_PAYMENT, // booking created but not paid
	CONFIRMED, // payment done and booking valid
	CANCEL_REQUESTED, // user requested cancel
	CANCELLED, // seller approved cancellation
	REJECTED, // seller rejected cancellation
	RENTED, // product handed to user
	RETURNED, // product returned successfully
	NOT_RETURNED // return date passed but product not returned

   //  PENDING,   // Booking created but payment not done
   //  BOOKED,    // Payment done successfully
   //  RETURNED,  // Item returned after rental
   //  CANCELLED  // Booking cancelled before start
}
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)
