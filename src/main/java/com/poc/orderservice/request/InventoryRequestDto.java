package com.poc.orderservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InventoryRequestDto {

    private String userId;

    private Long stockId;

    private Double totalAmount;

    private Integer quantity;

    private String status;

}

