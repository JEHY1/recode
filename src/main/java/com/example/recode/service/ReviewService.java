package com.example.recode.service;

import com.example.recode.domain.Review;
import com.example.recode.domain.ReviewImg;
import com.example.recode.dto.ReviewResponse;
import com.example.recode.repository.ProductRepository;
import com.example.recode.repository.ReviewImgRepository;
import com.example.recode.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final UserService userService;
    private final ProductService productService;

    public Review getReviewFindById(Long reviewId) { // reviewId로 Review 가져오기
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("not found review"));
    }

    public List<Review> getReviewFindAll() { // List<Review> 가져오기
        return reviewRepository.findAll();
    }

    public List<ReviewImg> getReviewImgFindByReviewId(long reviewId) { // reviewId로 List<ReviewImg> 가져오기
        return reviewImgRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("not found reviewimgs"));
    }

    public void deleteById(Long reviewId) { // reviewId로 Review 삭제
        reviewRepository.deleteById(reviewId);
    }

    public List<ReviewResponse> getAllReviewInfo() { // List<ReviewResponse> 가져오기

        List<ReviewResponse> list = new ArrayList<>();

        getReviewFindAll().forEach(review -> list.add(new ReviewResponse(review, userService.getUsername(review.getUserId()), productService.findProductByProductId(review.getProductId()).getProductName())));

        return list;
    }
}
