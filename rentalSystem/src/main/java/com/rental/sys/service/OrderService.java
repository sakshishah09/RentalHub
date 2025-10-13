package com.rental.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rental.sys.convertor.model.OrderEntityToModel;
import com.rental.sys.entities.Order;
import com.rental.sys.entities.OrderStatus;
import com.rental.sys.entities.Product;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.OrderSaveRequestModel;
import com.rental.sys.model.response.OrderResponse;
import com.rental.sys.repo.OrderRepository;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.UserRepo;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

	@Autowired
	private ProductRepo productRepository;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderEntityToModel orderEntityToModel;

	public OrderResponse placeOrder(OrderSaveRequestModel request) throws Exception {
		Product product = productRepository.findById(request.getProductId())
				.orElseThrow(() -> new Exception("Product not found"));

		User buyer = userRepository.findById(request.getUserId()).orElseThrow(() -> new Exception("Buyer not found"));

		User seller = product.getUser();
		if (seller == null)
			throw new Exception("Product seller not found");

		BigDecimal total = product.getPricePerDay().multiply(BigDecimal.valueOf(request.getQuantity()));

		Order order = new Order();
		order.setProduct(product);
		order.setBuyer(buyer);
		order.setSeller(seller);
		order.setQuantity(request.getQuantity());
		order.setTotalPrice(total);
		order.setStatus(OrderStatus.PLACED);

		order = orderRepo.save(order);
		return orderEntityToModel.convert(order);
	}

	// Cancel order
	public OrderResponse cancelOrder(Integer orderId, Integer sellerId) throws Exception {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new Exception("Order not found"));

		if (!order.getSeller().getId().equals(sellerId)) {
			throw new Exception("You are not authorized to cancel this order");
		}

		order.setStatus(OrderStatus.CANCELLED);
		order = orderRepo.save(order);
		return orderEntityToModel.convert(order);
	}

	public List<OrderResponse> getOrdersByBuyer(Integer buyerId, Integer page, Integer size) throws Exception {
		List<Order> orderList = orderRepo.findByBuyerId(buyerId, PageRequest.of(page, size));
		return orderEntityToModel.getFindAllConvert(orderList);
	}

	public long countOrdersByBuyer(Integer buyerId) {
		return orderRepo.countByBuyerId(buyerId);
	}

	public List<OrderResponse> getOrdersBySeller(Integer sellerId, Integer page, Integer size) throws Exception {
		List<Order> orderList = orderRepo.findBySellerId(sellerId, PageRequest.of(page, size));
		return orderEntityToModel.getFindAllConvert(orderList);
	}

	public long countOrdersBySeller(Integer sellerId) {
		return orderRepo.countBySellerId(sellerId);
	}
}