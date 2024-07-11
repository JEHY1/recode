package com.example.recode.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOrderCheckAllInfoResponse {

    private List<OrderCheckResponse> orderCheckInfoList;
    private int nonDepositCount;
    private int deliveryReadyCount;
    private int deliveryInCount;
    private int deliveryCompleteCount;

    @Builder
    public ProductOrderCheckAllInfoResponse(List<OrderCheckResponse> orderCheckInfoList, int nonDepositCount, int deliveryReadyCount, int deliveryInCount, int deliveryCompleteCount) {
        this.orderCheckInfoList = orderCheckInfoList;
        this.nonDepositCount = nonDepositCount;
        this.deliveryReadyCount = deliveryReadyCount;
        this.deliveryInCount = deliveryInCount;
        this.deliveryCompleteCount = deliveryCompleteCount;
    }
}
