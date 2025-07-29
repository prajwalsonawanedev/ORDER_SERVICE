package com.poc.orderservice.controller;

import com.poc.orderservice.request.OrderRequestDto;
import com.poc.orderservice.response.ApiResponse;
import com.poc.orderservice.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.createOrder(orderRequestDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders(@PageableDefault(page = 0, size = 100)
                                                    @SortDefault.SortDefaults({
                                                            @SortDefault(sort = "inventoryId", direction = Sort.Direction.DESC)})
                                                    Pageable pageable) {
        return new ResponseEntity<>(orderService.getAllOrders(pageable), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

}
