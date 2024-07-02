package com.example.recode.service;

import com.example.recode.domain.Review;
import com.example.recode.domain.ReviewImg;
import com.example.recode.dto.ReviewDto;
import com.example.recode.dto.ReviewImgDto;
import com.example.recode.dto.ReviewResponse;
import com.example.recode.repository.ProductRepository;
import com.example.recode.repository.ReviewImgRepository;
import com.example.recode.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    public Page<Review> getReviews(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page, size));
    }
    public Optional<ReviewDto> getReviewById(Long id) {
        return reviewRepository.findById(id).map(this::convertToReviewDto);
    }

//    public Optional<ReviewImgDto> getReviewImgById(Long id) {
//        Optional<Review> reviewOpt = reviewRepository.findById(id);
//        if (reviewOpt.isEmpty()) {
//            return Optional.empty();
//        }
//
//        Review review = reviewOpt.get();
//        List<String> imgUrls = reviewImgRepository.findByReviewId(review.getReviewId())
//                .orElse(new ArrayList<>())
//                .stream()
//                .map(ReviewImg::getImgUrl)
//                .collect(Collectors.toList());
//
//        return Optional.of(convertToReviewImgDto(review, imgUrls));
//    }

    private ReviewDto convertToReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(review.getReviewId());
        reviewDto.setUserId(review.getUserId());
        reviewDto.setProductId(review.getProductId());
        reviewDto.setReviewTitle(review.getReviewTitle());
        reviewDto.setReviewContent(review.getReviewContent());
        reviewDto.setReviewCreateDate(LocalDate.from(review.getReviewCreateDate()));
        reviewDto.setReviewViews(review.getReviewViews());
        reviewDto.setReviewScore(review.getReviewScore());
        return reviewDto;
    }

    private ReviewImgDto convertToReviewImgDto(Review review, List<String> imgUrls) {
        ReviewImgDto reviewImgDto = new ReviewImgDto();
        reviewImgDto.setReviewId(review.getReviewId());
        reviewImgDto.setUserId(review.getUserId());
        reviewImgDto.setProductId(review.getProductId());
        reviewImgDto.setReviewTitle(review.getReviewTitle());
        reviewImgDto.setReviewContent(review.getReviewContent());
        reviewImgDto.setReviewCreateDate(LocalDate.from(review.getReviewCreateDate()));
        reviewImgDto.setReviewViews(review.getReviewViews());
        reviewImgDto.setReviewScore(review.getReviewScore());
        reviewImgDto.setImgUrls(imgUrls);
        return reviewImgDto;
    }
}
