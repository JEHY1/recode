package com.example.recode.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminPaymentInfoResponse {

    private long paymentId;
    private String productName;
    private int productCount;
    private String userRealName;
    private int paymentPrice;
    private String paymentStatus;
    private String startDate;

    @Builder
    public AdminPaymentInfoResponse(long paymentId, String productName, int productCount, String userRealName, int paymentPrice, String paymentStatus, String startDate) {
        this.paymentId = paymentId;
        this.productName = productName;
        this.productCount = productCount;
        this.userRealName = userRealName;
        this.paymentPrice = paymentPrice;
        this.paymentStatus = paymentStatus;
        this.startDate = startDate;
    }
}
