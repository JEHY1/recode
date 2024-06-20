package com.example.recode.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_regular_price", nullable = false)
    private int productRegularPrice;

    @Column(name = "product_discount_price")
    private Integer productDiscountPrice;

    @Column(name = "product_model", nullable = false)
    private String productModel;

    @Column(name = "product_size", nullable = false)
    private String productSize;

    @Column(name = "product_material", nullable = false)
    private String productMaterial;

    @Column(name = "product_representative_img_src", nullable = false)
    private String productRepresentativeImgSrc;

    @CreatedDate
    @Column(name = "product_registration_date", nullable = false)
    private LocalDateTime productRegistrationDate;

    @Column(name = "product_category", nullable = false)
    private String productCategory;

    @Column(name = "product_sold", nullable = false)
    private int productSold;

    @Column(name = "product_color", nullable = false)
    private String productColor;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @Builder
    public Product(long productId, String productName, int productRegularPrice, int productDiscountPrice, String productModel, String productSize, String productMaterial, String productRepresentativeImgSrc, LocalDateTime productRegistrationDate, String productCategory, int productSold, String productColor, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productRegularPrice = productRegularPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.productModel = productModel;
        this.productSize = productSize;
        this.productMaterial = productMaterial;
        this.productRepresentativeImgSrc = productRepresentativeImgSrc;
        this.productRegistrationDate = productRegistrationDate;
        this.productCategory = productCategory;
        this.productSold = productSold;
        this.productColor = productColor;
        this.productType = productType;
    }

    public void updateSold(int productSold){
        this.productSold = productSold;
    }
}
