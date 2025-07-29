package com.poc.orderservice.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {

    private Long orderId;

    @NotBlank
    private String userId;

    @NotBlank
    private String stockId;

    @Min(1)
    private Integer quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    private Double totalAmount; // optional

    private String status;      // optional
}