package com.poc.orderservice.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Long orderId;

    private String userId;

    private String stockId;

    private Integer quantity;

    private Double price;

    private Double totalAmount;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
