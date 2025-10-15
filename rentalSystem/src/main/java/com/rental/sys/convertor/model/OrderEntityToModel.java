package com.rental.sys.convertor.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rental.sys.entities.Orders;
import com.rental.sys.model.response.OrderResponse;

@Component
public class OrderEntityToModel {

    public List<OrderResponse> getFindAllConvert(List<Orders> orders) {
        return orders.stream().map(this::convert).collect(Collectors.toList());
    }

    public OrderResponse convert(Orders order) {
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