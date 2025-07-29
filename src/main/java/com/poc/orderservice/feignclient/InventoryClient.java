package com.poc.orderservice.feignclient;

import com.poc.orderservice.request.InventoryRequestDto;
import com.poc.orderservice.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "inventory-service", url = "http://localhost:8081/api/inventory") // or use Eureka service name
public interface InventoryClient {

    @PostMapping("/create-inventory")
    ResponseEntity<ApiResponse> createInventory(@RequestBody InventoryRequestDto inventoryRequest);
}