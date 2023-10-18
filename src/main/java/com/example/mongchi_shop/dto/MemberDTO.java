package com.example.mongchi_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private int mno;
    private String emailId;
    private String password;
    private String memberName;
    private String phone;
    private String zipCode;
    private String address01;
    private String address02;
    private String address03;
    private String addDate;
    private String birthday;
    private String uuid;
    private String admin;

}
