package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.RentalReturn;

public interface RentalReturnRepo extends JpaRepository<RentalReturn, Integer>{



}
