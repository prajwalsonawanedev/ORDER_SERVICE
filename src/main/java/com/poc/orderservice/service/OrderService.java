package com.poc.orderservice.service;

import com.poc.orderservice.request.OrderRequestDto;
import com.poc.orderservice.response.ApiResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    ApiResponse createOrder(OrderRequestDto orderRequestDto);

    ApiResponse getAllOrders(Pageable pageable);

    ApiResponse getOrderById(Long orderId);

}
