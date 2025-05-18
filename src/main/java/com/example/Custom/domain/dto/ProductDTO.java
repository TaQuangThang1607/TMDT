package com.example.Custom.domain.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String size;
    private String color;
    private String material;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
    private String imageUrl;
}
