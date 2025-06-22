package com.example.Custom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterParams {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Long categoryId;
    private String size;
    private String color;
    private String material;
    private Integer minStock;
    private Integer minSold;
}
