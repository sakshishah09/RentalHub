package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rental.sys.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query("select e from User e where e.email=:email")
	 User findByEmail(@Param("email") String email);
	
	@Query("select e from User e where e.phoneNumber=:phoneNumber")
	 User findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
