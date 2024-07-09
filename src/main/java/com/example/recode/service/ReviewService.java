package com.example.recode.service;

import com.example.recode.domain.Notice;
import com.example.recode.domain.Review;
import com.example.recode.domain.ReviewImg;
import com.example.recode.dto.*;
import com.example.recode.repository.ProductRepository;
import com.example.recode.repository.ReviewImgRepository;
import com.example.recode.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ReviewImgService reviewImgService; // reviewImgService 필드추가

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


    public Page<Review> getAllReview(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Page<ReviewResponse> reviewViewList(Pageable pageable) { // 페이징 처리한 Page<ReviewResponse> 가져옴
        Page<Review> reviewList = reviewRepository.findAll(pageable); // 페이징 처리한 Page<Review>
        Page<ReviewResponse> reviewViewList = reviewList.map(review -> new ReviewResponse(review, userService.getUsername(review.getUserId()), productService.findProductByProductId(review.getProductId()).getProductName()));
        return reviewViewList;
    }

    public void deleteByIds(List<Long> reviewIds) { // reviewIds 리스트로 Review 삭제
        for (Long reviewId : reviewIds) {
            reviewRepository.deleteById(reviewId);
        }
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    public ReviewWithImagesResponse getReviewWithImages(Long reviewId) {
        Review review = getReviewFindById(reviewId);
        List<ReviewImg> reviewImgs = reviewImgService.getReviewImgFindByReviewId(reviewId);
        List<String> imgUrls = new ArrayList<>();
        if (reviewImgs != null) {
            for (ReviewImg img : reviewImgs) {
                imgUrls.add(img.getReviewImgSrc());
            }
        }
        return new ReviewWithImagesResponse(review, imgUrls);
    }

    public Page<ReviewWithImagesResponse> getAllReviewWithImages(Pageable pageable) {
        Page<Review> reviews = getAllReview(pageable);
        return reviews.map(review -> {
            List<ReviewImg> reviewImgs = getReviewImgFindByReviewId(review.getReviewId());
            List<String> imgUrls = new ArrayList<>();
            if (reviewImgs != null) {
                for (ReviewImg img : reviewImgs) {
                    imgUrls.add(img.getReviewImgSrc());
                }
            }
            return new ReviewWithImagesResponse(review, imgUrls);
        });
    }
}
