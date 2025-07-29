package com.poc.orderservice.serviceimpl;

import com.poc.orderservice.entity.Order;
import com.poc.orderservice.enums.OrderStatus;
import com.poc.orderservice.exception.ResourceNotFoundException;
import com.poc.orderservice.feignclient.InventoryClient;
import com.poc.orderservice.repository.OrderRepository;
import com.poc.orderservice.request.InventoryRequestDto;
import com.poc.orderservice.request.OrderRequestDto;
import com.poc.orderservice.response.ApiResponse;
import com.poc.orderservice.response.OrderResponseDto;
import com.poc.orderservice.service.OrderService;
import com.poc.orderservice.utils.GenericMapper;
import com.poc.orderservice.validation.OrderValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private List<String> errorList = new ArrayList<>();

    private final GenericMapper genericMapper;

    private final InventoryClient inventoryClient;

    @Override
    public ApiResponse createOrder(OrderRequestDto orderRequestDto) {

        if (!validateOrder(orderRequestDto)) {
            Order order = genericMapper.convert(orderRequestDto, Order.class);
            order.setStatus(OrderStatus.VALIDATION_FAILED.name());
            orderRepository.save(order);

            return ApiResponse.response("Order Reject", true, "Order Rejected due to Validation", orderRequestDto);
        }

        Order order = genericMapper.convert(orderRequestDto, Order.class);
        order.setStatus(OrderStatus.VALIDATED.name());
        order = orderRepository.save(order);

        InventoryRequestDto inventoryRequestDto = createInventoryRequestDto(order);
        ApiResponse apiResponse = inventoryClient.createInventory(inventoryRequestDto).getBody();

        if (apiResponse.isSuccess()) {
            order.setStatus(OrderStatus.SUCCESS.name());
            orderRepository.save(order);
            OrderResponseDto orderResponseDto = genericMapper.convert(order, OrderResponseDto.class);
            return ApiResponse.response("Order Create Successfully", true, "Order Created Successfully", orderResponseDto);
        }

        order.setStatus(OrderStatus.FAILED.name());
        orderRepository.save(order);

        return ApiResponse.response("Order Not Created", false, "Order Rejected due to Insufficient Stocks", orderRequestDto);

    }

    @Override
    public ApiResponse getAllOrders() {
        List<OrderResponseDto> orderList = orderRepository.findAll()
                .stream()
                .map(order -> genericMapper.convert(order, OrderResponseDto.class))
                .toList();

        return ApiResponse.response("Order Details Found", true, null, orderList);
    }

    @Override
    public ApiResponse getOrderById(Long orderId) {

        if (ObjectUtils.isEmpty(orderId)) {
            return ApiResponse.response("Order Details Not Found", false, "Please Provide Valid OrderId", null);
        }

        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new ResourceNotFoundException("Order Details Not found :" + orderId));

        OrderResponseDto orderResponseDto = genericMapper.convert(order, OrderResponseDto.class);
        return ApiResponse.response("Order Details Found", true, "Order Details", orderResponseDto);
    }

    public boolean validateOrder(OrderRequestDto orderRequestDto) {

        errorList = OrderValidation.validateOrder(orderRequestDto);

        if (!CollectionUtils.isEmpty(errorList)) {
            return false;
        }
        return true;
    }

    public InventoryRequestDto createInventoryRequestDto(Order order) {

        return InventoryRequestDto
                .builder()
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .stockId(Long.valueOf(order.getStockId()))
                .status(order.getStatus())
                .userId(order.getUserId())
                .build();

    }
}
