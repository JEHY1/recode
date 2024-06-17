package com.example.recode.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductDetailViewResponse {

    private long productId;
    private String productName;
    private String productModel;
    private String productRepImg;
    private List<String> productDetailImgs;
    private String color;
    private String type;
    private int regularPrice;
    private Integer discountPrice;

    @Builder
    public ProductDetailViewResponse(long productId ,String productName, String productModel, String productRepImg, List<String> productDetailImgs,
                                     String color, String type, int regularPrice, Integer discountPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productModel = productModel;
        this.productRepImg = productRepImg;
        this.productDetailImgs = productDetailImgs;
        this.color = color;
        this.type = type;
        this.regularPrice = regularPrice;
        this.discountPrice = discountPrice;
    }
}
