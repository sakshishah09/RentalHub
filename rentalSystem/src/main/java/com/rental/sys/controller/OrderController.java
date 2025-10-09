package com.rental.sys.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rental.sys.model.request.OrderSaveRequestModel;
import com.rental.sys.model.response.OrderResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @PostMapping("/place")
    @Operation(summary = "Place a new order", description = "Buyer places an order for a product")
    public RestResponse placeOrder(@RequestBody OrderSaveRequestModel request) {
        try {
            OrderResponse response = orderService.placeOrder(request);
            return RestResponse.build().withSuccess("Order placed successfully", response);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    // 🔴 2️⃣ Cancel order by seller
    @PutMapping("/{orderId}/cancel")
    @Operation(summary = "Cancel order", description = "Seller cancels an order they own")
    public RestResponse cancelOrder(@PathVariable Integer orderId, @RequestParam Integer sellerId) {
        try {
            OrderResponse response = orderService.cancelOrder(orderId, sellerId);
            return RestResponse.build().withSuccess("Order cancelled successfully", response);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }


    @Operation(summary = "Get all orders by buyer (paginated)")
    @RequestMapping(method = RequestMethod.GET, value = "/buyer/{buyerId}", produces = "application/json")
    public RestResponse getOrdersByBuyer(
            @PathVariable Integer buyerId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        try {
            List<OrderResponse> orderResponse = orderService.getOrdersByBuyer(buyerId, page, size);
            long totalRecord = orderService.countOrdersByBuyer(buyerId);

            return RestResponse.build()
                    .withSuccess("Order list found successfully")
                    .withTotalRecords(totalRecord)
                    .withPageNumber(page)
                    .withPageSize(size)
                    .withData(orderResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Get all orders by seller (paginated)")
    @RequestMapping(method = RequestMethod.GET, value = "/seller/{sellerId}", produces = "application/json")
    public RestResponse getOrdersBySeller(
            @PathVariable Integer sellerId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        try {
            List<OrderResponse> orderResponse = orderService.getOrdersBySeller(sellerId, page, size);
            long totalRecord = orderService.countOrdersBySeller(sellerId);

            return RestResponse.build()
                    .withSuccess("Order list found successfully")
                    .withTotalRecords(totalRecord)
                    .withPageNumber(page)
                    .withPageSize(size)
                    .withData(orderResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }
}
