package com.example.recode.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    @Id
    @Column(name = "address_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "address_postal_code", nullable = false)
    private String addressPostalCode;

    @Column(name = "address_road_name_address", nullable = false)
    private String addressRoadNameAddress;

    @Column(name = "address_detail_address", nullable = false)
    private String addressDetailAddress;

    @Column(name = "address_recipient_name", nullable = false)
    private String addressRecipientName;

    @Column(name = "address_recipient_phone", nullable = false)
    private String addressRecipientPhone;

    @Column(name = "address_delivery_request", nullable = false)
    private String addressDeliveryRequest;

    @Column(name = "address_front_door_secret")
    private String addressFrontDoorSecret;

    @Column(name = "address_delivery_box_num")
    private String addressDeliveryBoxNum;

    @Builder
    public Address(long addressId, long userId, String addressPostalCode, String addressRoadNameAddress, String addressDetailAddress, String addressRecipientName, String addressRecipientPhone, String addressDeliveryRequest, String addressFrontDoorSecret, String addressDeliveryBoxNum) {
        this.addressId = addressId;
        this.userId = userId;
        this.addressPostalCode = addressPostalCode;
        this.addressRoadNameAddress = addressRoadNameAddress;
        this.addressDetailAddress = addressDetailAddress;
        this.addressRecipientName = addressRecipientName;
        this.addressRecipientPhone = addressRecipientPhone;
        this.addressDeliveryRequest = addressDeliveryRequest;
        this.addressFrontDoorSecret = addressFrontDoorSecret;
        this.addressDeliveryBoxNum = addressDeliveryBoxNum;
    }
}
