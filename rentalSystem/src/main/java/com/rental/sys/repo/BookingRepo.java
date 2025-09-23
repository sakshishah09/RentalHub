package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rental.sys.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer>  {

}
