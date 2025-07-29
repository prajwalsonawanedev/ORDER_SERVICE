package com.poc.orderservice.service;

import com.poc.orderservice.request.OrderRequestDto;
import com.poc.orderservice.response.ApiResponse;

public interface OrderService {

    ApiResponse createOrder(OrderRequestDto orderRequestDto);

    ApiResponse getAllOrders();

    ApiResponse getOrderById(Long orderId);

}
