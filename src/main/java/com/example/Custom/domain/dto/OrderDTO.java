package com.example.Custom.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
    private String paymentMethod;
    private LocalDateTime orderDate;
    private List<OrderItemDTO> orderItems;
}
