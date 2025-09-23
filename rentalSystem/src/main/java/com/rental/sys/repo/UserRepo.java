package com.rental.sys.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rental.sys.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query("select e from User e where e.email=:email")
	 User findByEmail(@Param("email") String email);
	
	@Query("select e from User e where e.phoneNumber=:phoneNumber")
	 User findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
	@Query("select e from User e where (e.email=:username or e.phoneNumber=:username) and e.password=:password")
	User findByUsernamePassword(@Param("username") String username, @Param("password") String password);
	
	@Query("select e from User e")
	List<User> findAllUser(Pageable pageable);


}
