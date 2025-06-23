package com.example.Custom.domain.dto;


import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private String color;
    private String size;
    private String imageUrl;
}