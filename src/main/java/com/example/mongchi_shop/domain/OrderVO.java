package com.example.mongchi_shop.domain;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private int ono;
    private String orderId; // 주문번호
    private String emailId; // 회원아이디
    private String orderName; // 주문자이름
    private String orderDate; // 주문날짜
    private int totalAmount; // 주문 금액
    private String phone; // 연락처
    private String zipCode; // 우편번호
    private String address01; // 주소1
    private String address02; // 주소2
    private String orderStatus; // 주문상태
}
