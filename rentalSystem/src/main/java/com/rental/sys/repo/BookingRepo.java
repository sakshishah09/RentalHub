package com.rental.sys.repo;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rental.sys.entities.Booking;
import com.rental.sys.entities.BookingStatus;

public interface BookingRepo extends JpaRepository<Booking, Integer>  {

	// ✅ Fix 1: use correct return type and primary key type
    Booking findById(int id);
    
    // ✅ Fix 2: remove IgnoreCase since BookingStatus is an Enum
    Page<Booking> findByUserId(Integer userId, Pageable pageable);

    // ✅ All bookings for products owned by a seller (paginated)
    Page<Booking> findBySellerId(Integer sellerId, Pageable pageable);


    @Query("""
        SELECT b FROM Booking b
        WHERE b.product.id = :productId
          AND b.status IN :statuses
          AND b.fromDate <= :requestedTo
          AND b.toDate >= :requestedFrom
    """)
    List<Booking> findOverlappingBookings(
            @Param("productId") Integer productId,
            @Param("requestedFrom") LocalDate requestedFrom,
            @Param("requestedTo") LocalDate requestedTo,
            @Param("statuses") List<String> statuses
    );
}
