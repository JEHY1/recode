package com.example.recode.service;

import com.example.recode.domain.*;
import com.example.recode.dto.*;
import com.example.recode.repository.ProductRepository;
import com.example.recode.repository.ReviewImgRepository;
import com.example.recode.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Page<ReviewResponse> reviewViewSearchList(Integer category, String searchKeyword, Pageable pageable) { // category 선택 후 검색해서 페이징 처리한 Page<ReviewResponse> 가져옴
        Page<ReviewResponse> reviewViewSearchList = null;
        if(category == 1) {  // category 가 '상품명'일 때
            List<Product> productSearchList = productService.findByProductNameContaining(searchKeyword);
            List<Long> productIds = new ArrayList<>();
            if(productSearchList != null) {
                productSearchList.forEach(product -> productIds.add(product.getProductId()));
            }
            Page<Review> ReviewSearchList = reviewRepository.findByProductIdIn(productIds, pageable).orElse(null); // productId List 로 페이징 처리한 Page<Review> 가져오기
            if(ReviewSearchList != null) {
                reviewViewSearchList = ReviewSearchList.map(review -> new ReviewResponse(review, userService.getUsername(review.getUserId()), productService.findProductByProductId(review.getProductId()).getProductName()));
            }
        }
        else if(category == 2) {  // category 가 '제목'일 때
            Page<Review> ReviewSearchList = reviewRepository.findByReviewTitleContaining(searchKeyword, pageable).orElse(null); // reviewTitle 로 검색해서 페이징 처리한 Page<Review>
            if(ReviewSearchList != null) {
                reviewViewSearchList = ReviewSearchList.map(review -> new ReviewResponse(review, userService.getUsername(review.getUserId()), productService.findProductByProductId(review.getProductId()).getProductName()));
            }
        }
        else if(category == 3) {  // category 가 '작성자'일 때
            List<User> userSearchList = userService.findUserByUsernameContaining(searchKeyword);
            List<Long> userIds = new ArrayList<>();
            if(userSearchList != null) {
                userSearchList.forEach(user -> userIds.add(user.getUserId()));
            }
            Page<Review> ReviewSearchList = reviewRepository.findByUserIdIn(userIds, pageable).orElse(null); // userId List 로 페이징 처리한 Page<Review> 가져오기
            if(ReviewSearchList != null) {
                reviewViewSearchList = ReviewSearchList.map(review -> new ReviewResponse(review, userService.getUsername(review.getUserId()), productService.findProductByProductId(review.getProductId()).getProductName()));
            }
        }
        else if(category == 4) { // category 가 '등록일'일 때
            // searchKeyword 가 포함된 List<ReviewResponse> 만듬
            List<ReviewResponse> keywordList = new ArrayList<>();
            getAllReviewInfo().forEach(review -> {
                if(review.getReviewCreateDate().toString().contains(searchKeyword)){
                    keywordList.add(review);
                }
            });
            keywordList.sort((o1, o2) -> Math.toIntExact(o2.getReviewId() - o1.getReviewId())); // reviewId 기준 내림차순 정렬
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), keywordList.size());
            reviewViewSearchList = new PageImpl<>(keywordList.subList(start, end), pageable, keywordList.size()); // Page<reviewViewSearchList> 객체 만듬
        }
        return reviewViewSearchList;
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

    public void saveReview(ReviewDto reviewDto, List<MultipartFile> files) {
        Review review = Review.builder()
                .userId(reviewDto.getUserId())
                .productId(reviewDto.getProductId())
                .reviewTitle(reviewDto.getReviewTitle())
                .reviewContent(reviewDto.getReviewContent())
                .reviewScore(reviewDto.getReviewScore())
                .reviewCreateDate(LocalDateTime.now())
                .reviewViews(0)
                .build();

        reviewRepository.save(review);

        for (MultipartFile file : files) {
            ReviewImg reviewImg = ReviewImg.builder()
                    .reviewId(review.getReviewId())
                    .reviewImgSrc(file.getOriginalFilename())
                    .build();
            // 실제 파일 저장 로직 필요
            reviewImgRepository.save(reviewImg);
        }
    }
}
