package com.example.recode.controller.user;
import com.example.recode.domain.Review;
import com.example.recode.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public String getReviews(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        Page<Review> reviewPage = reviewService.getReviews(page, size);
        System.err.println(reviewPage.getContent());
        model.addAttribute("reviews", reviewPage.getContent());
        model.addAttribute("totalPages", reviewPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "board/reviewList";
    }


    @GetMapping("/review/{id}")
    public String getReviewById(@PathVariable Long id, Model model) {
        Review review = reviewService.getReviewFindById(id);
        model.addAttribute("review", review);
        return "board/reviewTxt";
    }

    // 특정 사용자가 작성한 사용 후기 목록을 페이징 처리하여 보여줌

}
