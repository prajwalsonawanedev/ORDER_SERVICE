package com.poc.orderservice.enums;

public enum OrderStatus {
    CREATED("CREATED"),
    VALIDATED("VALIDATED"),
    REJECTED("REJECTED"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    VALIDATION_FAILED("VALIDATION_FAILED"),
    INVENTORY_FAILED("INVENTORY_FAILED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}