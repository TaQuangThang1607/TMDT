package com.example.Custom.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DesignRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private String size;
    private String color;
    private String imagePath;
    private String description;
    private String status; 
        public Long getPrice() {
        return price;
    }
    private java.time.LocalDateTime createdAt;
    public void setPrice(Long price) {
        this.price = price;
    }
}
