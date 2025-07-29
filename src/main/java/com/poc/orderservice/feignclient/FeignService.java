package com.poc.orderservice.feignclient;

import com.poc.orderservice.request.InventoryRequestDto;
import com.poc.orderservice.request.OrderRequestDto;
import com.poc.orderservice.response.ApiResponse;
import com.poc.orderservice.response.OrderResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeignService {
    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventory_CB", fallbackMethod = "inventoryFallback")
    public ApiResponse createInventory(InventoryRequestDto inventoryRequestDto) {
        return inventoryClient.createInventory(inventoryRequestDto).getBody();
    }

    public ApiResponse<OrderResponseDto> inventoryFallback(OrderRequestDto orderRequestDto, Throwable ex) {
        return ApiResponse.response("Fallback triggered", false, "Inventory Service is unavailable", null);
    }

}
