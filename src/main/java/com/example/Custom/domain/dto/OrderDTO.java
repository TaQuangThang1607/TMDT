package com.example.Custom.domain.dto;


import lombok.Data;

@Data
public class OrderDTO {
    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;
    private String receiverNote;
}
