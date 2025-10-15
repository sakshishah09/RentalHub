package com.rental.sys.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rental.sys.entities.Booking;
import com.rental.sys.entities.Orders;
import com.rental.sys.entities.RentalReturn;
import com.rental.sys.entities.User;
import com.rental.sys.model.response.AdminDashboardResponse;
import com.rental.sys.model.response.UserResponseForDashboard;
import com.rental.sys.repo.BookingRepo;
import com.rental.sys.repo.OrderRepository;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.RentalReturnRepo;
import com.rental.sys.repo.UserRepo;


@Service
public class AdminService {

    @Autowired private UserRepo userRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private BookingRepo bookingRepository;
    @Autowired private RentalReturnRepo rentalReturnRepository;
    @Autowired private ProductRepo productRepository;

    // 📊 Dashboard summary data
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalUsers", userRepository.count());
        map.put("totalSellers", userRepository.countByIsSellerTrue());
        map.put("totalBuyers", userRepository.countByIsSellerFalse());
        map.put("totalProducts", productRepository.count());
        map.put("totalOrders", orderRepository.count());
        map.put("totalBookings", bookingRepository.count());
        map.put("totalReturns", rentalReturnRepository.count());
        return map;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }
    public List<User> getAllSellers() { return userRepository.findByIsSellerTrue(); }
    public List<User> getAllBuyers() { return userRepository.findByIsSellerFalse(); }

    public List<Orders> getAllOrders() { return orderRepository.findAll(); }
    public List<Booking> getAllBookings() { return bookingRepository.findAll(); }
    public List<RentalReturn> getAllReturns() { return rentalReturnRepository.findAll(); }

    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Orders getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
    
    public AdminDashboardResponse getDashboard() {

        // Seller-wise detailed summary
        List<UserResponseForDashboard> sellersSummary = userRepository.getSellerSummaries();

        // System-level totals
        long totalUsers = userRepository.count();
        long totalSellers = sellersSummary.size();
        long totalBuyers = totalUsers - totalSellers;
        long totalProducts = productRepository.count();
        long totalBookings = bookingRepository.count();
        long totalOrders = orderRepository.count();
        long totalReturns = rentalReturnRepository.count();

        BigDecimal totalEarningsOverall = sellersSummary.stream()
                .map(UserResponseForDashboard::getTotalEarnings)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new AdminDashboardResponse(
                totalUsers,
                totalSellers,
                totalBuyers,
                totalProducts,
                totalBookings,
                totalOrders,
                totalReturns,
                totalEarningsOverall,
                sellersSummary
        );
    }
    
    public Page<UserResponseForDashboard>filterUsers(
            String role,
            String status,
            BigDecimal minEarnings,
            BigDecimal maxEarnings,
            LocalDate fromDate,
            LocalDate toDate,
            String search, int page,
            int size
    ) {
    	 Pageable pageable = PageRequest.of(page, size);
    	    List<UserResponseForDashboard> results = userRepository.filterUsers(role, status, minEarnings, maxEarnings, fromDate, toDate, search);

    	    // Manual pagination on List
    	    int start = (int) pageable.getOffset();
    	    int end = Math.min((start + pageable.getPageSize()), results.size());
    	    List<UserResponseForDashboard> pagedList = results.subList(start, end);

    	    return new PageImpl<>(pagedList, pageable, results.size());
    }
    
}
