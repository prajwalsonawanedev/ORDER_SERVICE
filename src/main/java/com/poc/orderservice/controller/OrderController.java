package com.poc.orderservice.controller;

import com.poc.orderservice.request.OrderRequestDto;
import com.poc.orderservice.response.ApiResponse;
import com.poc.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-service")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createorder")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        ApiResponse apiResponse = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse> getAllOrders() {
        ApiResponse apiResponse = orderService.getAllOrders();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/orderById")
    public ResponseEntity<ApiResponse> getOrderById(@RequestParam Long orderId) {
        ApiResponse apiResponse = orderService.getOrderById(orderId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
