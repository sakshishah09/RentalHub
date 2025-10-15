package com.rental.sys.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	 Optional<Role> findByName(String name);
}