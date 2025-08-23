package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Location;

public interface LocationRepo extends JpaRepository<Location, Integer> {

}
