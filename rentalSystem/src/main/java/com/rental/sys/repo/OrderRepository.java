package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {}


