package com.rental.sys.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rental.sys.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer>  {

	Booking findById(Long id);
	  List<Booking> findByStatusIgnoreCase(String status);
	  
	  //  Find bookings by status (e.g., RENT or BUY) with pagination
	    Page<Booking> findByStatusIgnoreCase(String status, Pageable pageable);

	    @Query("SELECT b FROM Booking b WHERE b.product.id = :productId AND b.status IN :statuses "
	            + "AND b.fromDate <= :requestedTo AND b.toDate >= :requestedFrom")
	       List<Booking> findOverlappingBookings(@Param("productId") Integer productId,
	                                             @Param("requestedFrom") LocalDate requestedFrom,
	                                             @Param("requestedTo") LocalDate requestedTo,
	                                             @Param("statuses") List<String> statuses);

}
