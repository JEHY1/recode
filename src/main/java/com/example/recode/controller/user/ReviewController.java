package com.example.recode.controller.user;
import com.example.recode.domain.QnA;
import com.example.recode.domain.Review;
import com.example.recode.dto.ReviewWithImagesResponse;
import com.example.recode.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/reviews")
    public String getAllReviews(Model model, Pageable pageable) {
        // 페이지 크기를 설정하는 경우 (10개씩 출력)
        int pageSize = 10;
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageSize, Sort.by("reviewId").ascending());

        Page<ReviewWithImagesResponse> list = reviewService.getAllReviewWithImages(pageRequest);

        int firstPage = list.getNumber() + 1;
        int totalPages = list.getTotalPages();
        int startPage = Math.max(firstPage - 4, 1);
        int lastPage = Math.min(firstPage + 5, totalPages);

        model.addAttribute("reviews", list);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", firstPage);
        model.addAttribute("pageNumber", pageable.getPageNumber());

        return "/board/reviewList";
    }


    @GetMapping("/review/{id}")
    public String getReviewById(@PathVariable Long id, Model model) {
        ReviewWithImagesResponse reviewWithImagesResponse = reviewService.getReviewWithImages(id);
        model.addAttribute("review", reviewWithImagesResponse.getReview());
        model.addAttribute("reviewImgs", reviewWithImagesResponse.getImgUrls());
        return "/board/reviewTxt";
    }

}
