package com.example.mongchi_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int ono;
    private String orderId; // 주문번호
    private String emailId; // 회원아이디
    private String orderName; // 주문자이름
    private String orderDate; // 주문날짜
    private int totalAmount; // 주문금액
    private String phone; // 연락처
    private String zipCode; // 우편번호
    private String address01; // 주소1
    private String address02; // 주소2
    private String orderStatus; // 주문상태
}
