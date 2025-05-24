package com.example.Custom.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Long id;
    private double totalPrice;
    private List<Item> details;

    @Data
    public static class Item {
        private String productName;
        private int quantity;
        private double price;
    }
}
