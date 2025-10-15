package com.rental.sys.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rental.sys.entities.Booking;
import com.rental.sys.entities.Orders;
import com.rental.sys.entities.RentalReturn;
import com.rental.sys.entities.User;
import com.rental.sys.model.response.UserResponseForDashboard;
import com.rental.sys.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	// Dashboard summary data
	@GetMapping("/dashboard")
	public Map<String, Object> getDashboardSummary() {
		return adminService.getDashboardSummary();
	}

	// All users (buyers + sellers)
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return adminService.getAllUsers();
	}

	// All sellers
	@GetMapping("/sellers")
	public List<User> getAllSellers() {
		return adminService.getAllSellers();
	}

	// All buyers
	@GetMapping("/buyers")
	public List<User> getAllBuyers() {
		return adminService.getAllBuyers();
	}

	// All orders (sales)
	@GetMapping("/orders")
	public List<Orders> getAllOrders() {
		return adminService.getAllOrders();
	}

	// All bookings (rentals)
	@GetMapping("/bookings")
	public List<Booking> getAllBookings() {
		return adminService.getAllBookings();
	}

	// All rental returns
	@GetMapping("/returns")
	public List<RentalReturn> getAllReturns() {
		return adminService.getAllReturns();
	}

	// Get booking/order details by ID
	@GetMapping("/bookings/{id}")
	public Booking getBookingById(@PathVariable Integer id) {
		return adminService.getBookingById(id);
	}

	@GetMapping("/orders/{id}")
	public Orders getOrderById(@PathVariable Integer id) {
		return adminService.getOrderById(id);
	}

	// Delete/block user
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable Integer id) {
		adminService.deleteUser(id);
		return "User deleted successfully!";
	}

	// Filter
	@GetMapping("/users/filter")
	public ResponseEntity<Page<UserResponseForDashboard>> filterUsers(@RequestParam(required = false) String role,
			@RequestParam(required = false) String status, @RequestParam(required = false) BigDecimal minEarnings,
			@RequestParam(required = false) BigDecimal maxEarnings,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
			@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		{
			Page<UserResponseForDashboard> filteredUsers = adminService.filterUsers(role, status, minEarnings,
					maxEarnings, fromDate, toDate, search, page, size);
			return ResponseEntity.ok(filteredUsers);
		}
	}
}