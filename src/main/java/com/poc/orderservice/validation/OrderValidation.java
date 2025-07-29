package com.poc.orderservice.validation;


import com.poc.orderservice.request.OrderRequestDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public interface OrderValidation {

    static List<String> validateOrder(OrderRequestDto orderRequest) {

        List<String> errorList = new ArrayList<>();

        if (orderRequest == null) {
            errorList.add("Order details cannot be null");
            return errorList;
        }

        if (!StringUtils.hasText(orderRequest.getUserId())) {
            errorList.add("Please provide a valid User ID");
        }
        if (!StringUtils.hasText(orderRequest.getStockId())) {
            errorList.add("Please provide a valid Product ID");
        }
        if (orderRequest.getQuantity() == null || orderRequest.getQuantity() < 1) {
            errorList.add("Order quantity must be at least 1");
        }
        if (orderRequest.getPrice() == null || orderRequest.getPrice() <= 0.0) {
            errorList.add("Order price must be greater than 0.0");
        }
        if (orderRequest.getTotalAmount() != null && orderRequest.getTotalAmount() < 0.0) {
            errorList.add("Total amount cannot be negative");
        }
        if (orderRequest.getStatus() != null && !StringUtils.hasText(orderRequest.getStatus())) {
            errorList.add("If provided, status must not be empty");
        }

        return errorList;
    }
}
