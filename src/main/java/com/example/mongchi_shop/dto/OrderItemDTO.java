package com.example.mongchi_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private int ino;
    private int pno;
    private String orderId;
    private String productName;
    private int cnt;
    private int unitPrice;
    private String fileName;
}
