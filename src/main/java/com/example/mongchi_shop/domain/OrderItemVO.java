package com.example.mongchi_shop.domain;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemVO {
    private int ino;
    private int pno;
    private String orderId;
    private String productName;
    private int cnt;
    private int unitPrice;
    private String fileName;
}
