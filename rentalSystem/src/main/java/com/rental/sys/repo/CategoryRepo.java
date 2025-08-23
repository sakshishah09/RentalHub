package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
