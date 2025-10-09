package com.rental.sys.convertor.model;

import org.springframework.stereotype.Component;

import com.rental.sys.entities.Order;
import com.rental.sys.model.response.OrderResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEntityToModel {

    public List<OrderResponse> getFindAllConvert(List<Order> orders) {
        return orders.stream().map(this::convert).collect(Collectors.toList());
    }

    public OrderResponse convert(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setProductName(order.getProduct().getName());
        response.setBuyerName(order.getBuyer().getName());
        response.setSellerName(order.getSeller().getName());
        response.setQuantity(order.getQuantity());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}