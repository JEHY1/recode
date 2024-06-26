package com.example.recode.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AdminUserRequest {

    private Long userId;
    private String userPassword;
    private String userRealName;
    private String userPhone;
    private String userEmail;
    private String userRole;

    private List<AdminAddressRequest> addresses;


}
