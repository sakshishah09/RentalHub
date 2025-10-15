package com.rental.sys.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rental.sys.entities.User;
import com.rental.sys.model.response.UserResponseForDashboard;

public interface UserRepo extends JpaRepository<User, Integer> {
	@Query("select e from User e where e.email=:email")
	User findByEmail(@Param("email") String email);

	@Query("select e from User e where e.phoneNumber=:phoneNumber")
	User findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	@Query("select e from User e where (e.email=:username or e.phoneNumber=:username) and e.password=:password")
	User findByUsernamePassword(@Param("username") String username, @Param("password") String password);

	@Query("select e from User e")
	List<User> findAllUser(Pageable pageable);

	List<User> findByIsSellerTrue();

	List<User> findByIsSellerFalse();

	long countByIsSellerTrue();

	long countByIsSellerFalse();

//	@Query("""
//			    SELECT new com.rental.sys.model.response.UserResponseForDashboard(
//			        u.id,
//			        u.name,
//			        u.email,
//			        u.phoneNumber,
//			        CASE WHEN u.isSeller = TRUE THEN 'SELLER' ELSE 'BUYER' END,
//			        COUNT(DISTINCT p.id),
//			        COUNT(DISTINCT b.id),v
//			        COUNT(DISTINCT o.id),
//			        COALESCE(SUM(o.totalPrice) + SUM(b.totalAmount), 0),
//			        u.status,
//			        u.createdAt
//			    )
//			    FROM User u
//			    LEFT JOIN Product p ON p.user = u
//			    LEFT JOIN Booking b ON b.seller = u
//			    LEFT JOIN Order o ON o.seller = u
//			    WHERE u.isSeller = TRUE
//			    GROUP BY u.id, u.name, u.email, u.phoneNumber, u.isSeller, u.status, u.createdAt
//			    ORDER BY COALESCE(SUM(o.totalPrice) + SUM(b.totalAmount), 0) DESC
//			""")
//
//	List<UserResponseForDashboard> getSellerSummaries();
//
//	// ✅ Filter Users (search + role + status + date + earnings)
//	@Query("""
//			    SELECT new com.rental.sys.model.response.UserResponseForDashboard(
//			        u.id,
//			        u.name,
//			        u.email,
//			        u.phoneNumber,
//			        CASE WHEN u.isSeller = TRUE THEN 'SELLER' ELSE 'BUYER' END,
//			        COUNT(DISTINCT p.id),
//			        COUNT(DISTINCT b.id),
//			        COUNT(DISTINCT o.id),
//			        COALESCE(SUM(o.totalPrice) + SUM(b.totalAmount), 0),
//			        u.status,
//			        u.createdAt
//			    )
//			    FROM User u
//			    LEFT JOIN u.products p
//			    LEFT JOIN u.bookings b
//			    LEFT JOIN u.orders o
//			    WHERE (:role IS NULL OR (CASE WHEN u.isSeller = TRUE THEN 'SELLER' ELSE 'BUYER' END) = :role)
//			    AND (:status IS NULL OR u.status = :status)
//			    AND (:minEarnings IS NULL OR COALESCE(SUM(o.totalPrice) + SUM(b.totalAmount), 0) >= :minEarnings)
//			    AND (:maxEarnings IS NULL OR COALESCE(SUM(o.totalPrice) + SUM(b.totalAmount), 0) <= :maxEarnings)
//			    AND (:fromDate IS NULL OR u.createdAt >= :fromDate)
//			    AND (:toDate IS NULL OR u.createdAt <= :toDate)
//			    AND (
//			        :search IS NULL OR
//			        LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
//			        LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR
//			        u.phoneNumber LIKE CONCAT('%', :search, '%')
//			    )
//			    GROUP BY u.id, u.name, u.email, u.phoneNumber, u.isSeller, u.status, u.createdAt
//			    ORDER BY u.createdAt DESC
//			""")
//	List<UserResponseForDashboard> filterUsers(@Param("role") String role, @Param("status") String status,
//			@Param("minEarnings") BigDecimal minEarnings, @Param("maxEarnings") BigDecimal maxEarnings,
//			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("search") String search);

	// ✅ Seller Dashboard Summary (Fixed & Clean)
	@Query("""
		       SELECT new com.rental.sys.model.response.UserResponseForDashboard(
		           u.id,
		           u.name,
		           u.email,
		           u.phoneNumber,
		           CASE WHEN u.isSeller = TRUE THEN 'SELLER' ELSE 'BUYER' END,
		           COUNT(DISTINCT p.id),
		           COUNT(DISTINCT b.id),
		           COUNT(DISTINCT o.id),
		           COALESCE(SUM(o.totalPrice), 0) + COALESCE(SUM(b.totalAmount), 0),
		           u.status,
		           u.createdAt
		       )
		       FROM User u
		       LEFT JOIN Product p ON p.user = u
		       LEFT JOIN Booking b ON b.seller = u
		       LEFT JOIN Orders o ON o.seller = u
		       WHERE u.isSeller = TRUE
		       GROUP BY u.id, u.name, u.email, u.phoneNumber, u.isSeller, u.status, u.createdAt
		       ORDER BY (COALESCE(SUM(o.totalPrice), 0) + COALESCE(SUM(b.totalAmount), 0)) DESC
		       """)
		List<UserResponseForDashboard> getSellerSummaries();


	@Query("""
		       SELECT new com.rental.sys.model.response.UserResponseForDashboard(
		           u.id,
		           u.name,
		           u.email,
		           u.phoneNumber,
		           CASE WHEN u.isSeller = TRUE THEN 'SELLER' ELSE 'BUYER' END,
		           COUNT(DISTINCT p.id),
		           COUNT(DISTINCT b.id),
		           COUNT(DISTINCT o.id),
		           (COALESCE(SUM(o.totalPrice), 0) + COALESCE(SUM(b.totalAmount), 0)),
		           u.status,
		           u.createdAt
		       )
		       FROM User u
		       LEFT JOIN Product p ON p.user = u
		       LEFT JOIN Booking b ON b.seller = u
		       LEFT JOIN Orders o ON o.seller = u
		       WHERE (:role IS NULL OR (CASE WHEN u.isSeller = TRUE THEN 'SELLER' ELSE 'BUYER' END) = :role)
		       AND (:status IS NULL OR u.status = :status)
		       AND (:fromDate IS NULL OR u.createdAt >= :fromDate)
		       AND (:toDate IS NULL OR u.createdAt <= :toDate)
		       AND (
		           :search IS NULL OR
		           LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
		           LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR
		           u.phoneNumber LIKE CONCAT('%', :search, '%')
		       )
		       GROUP BY u.id, u.name, u.email, u.phoneNumber, u.isSeller, u.status, u.createdAt
		       HAVING 
		           (:minEarnings IS NULL OR (COALESCE(SUM(o.totalPrice), 0) + COALESCE(SUM(b.totalAmount), 0)) >= :minEarnings)
		       AND (:maxEarnings IS NULL OR (COALESCE(SUM(o.totalPrice), 0) + COALESCE(SUM(b.totalAmount), 0)) <= :maxEarnings)
		       ORDER BY u.createdAt DESC
		       """)
		List<UserResponseForDashboard> filterUsers(
		        @Param("role") String role,
		        @Param("status") String status,
		        @Param("minEarnings") BigDecimal minEarnings,
		        @Param("maxEarnings") BigDecimal maxEarnings,
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate,
		        @Param("search") String search
		);



}
