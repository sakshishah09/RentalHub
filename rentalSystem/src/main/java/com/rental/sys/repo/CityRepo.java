package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.City;

public interface CityRepo extends JpaRepository<City, Integer> {

}
