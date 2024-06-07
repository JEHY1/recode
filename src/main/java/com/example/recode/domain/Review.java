package com.example.recode.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "review_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", updatable = false)
    private long reviewId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "review_title", nullable = false)
    private String reviewTitle;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @CreatedDate
    @Column(name = "review_create_date", nullable = false)
    private String reviewCreateDate;

    @Column(name = "review_score", nullable = false)
    private String reviewScore;

    @Column(name = "review_views", nullable = false)
    private String reviewViews;

    @Builder
    public Review(long reviewId, long userId, long productId, String reviewTitle, String reviewContent, String reviewCreateDate, String reviewScore, String reviewViews) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.productId = productId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewCreateDate = reviewCreateDate;
        this.reviewScore = reviewScore;
        this.reviewViews = reviewViews;
    }
}
