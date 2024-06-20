package com.example.recode.service;

import com.example.recode.domain.Review;
import com.example.recode.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("not found review"));
    }

    public void deleteById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
