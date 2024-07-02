package com.example.recode.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewDto {
    private Long reviewId;
    private Long userId;
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private LocalDate reviewCreateDate;
    private int reviewViews;
    private int reviewScore;

}

