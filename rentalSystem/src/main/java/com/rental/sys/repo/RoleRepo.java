package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}