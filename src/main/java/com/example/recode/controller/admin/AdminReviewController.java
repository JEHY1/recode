package com.example.recode.controller.admin;

import com.example.recode.domain.Product;
import com.example.recode.domain.Review;
import com.example.recode.domain.ReviewImg;
import com.example.recode.dto.NoticeViewResponse;
import com.example.recode.dto.ReviewResponse;
import com.example.recode.service.ProductService;
import com.example.recode.service.ReviewService;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/admin/review/{reviewId}/show") // admin_review_show 리뷰 보기 페이지
    public String showAdminReview(@PathVariable Long reviewId, Model model) {
        Review reviewEntity = reviewService.getReviewFindById(reviewId);
        model.addAttribute("review", reviewEntity);
        List<ReviewImg> reviewImgList = reviewService.getReviewImgFindByReviewId(reviewEntity.getReviewId());
        model.addAttribute("reviewImgs", reviewImgList);
        String username = userService.getUsername(reviewEntity.getUserId());
        model.addAttribute("username", username);
        Product productEntity = productService.findProductByProductId(reviewEntity.getProductId());
        model.addAttribute("product", productEntity);

        return "admins/admin_review_show";
    }

    @GetMapping("/admin/review/{reviewId}/delete") // 리뷰 삭제
    public String deleteAdminReview(@PathVariable Long reviewId, RedirectAttributes rttr, Integer page) {
        reviewService.deleteById(reviewId);
        rttr.addFlashAttribute("msg", "리뷰가 삭제 되었습니다.");

        return "redirect:/admin/review/index?page=" + page;
    }
    @GetMapping("/admin/review/index") // admin_review_index 리뷰 목록 페이지
    public String indexAdminReview(Model model, @PageableDefault(page = 0, size = 10, sort = "reviewId", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) Integer isDel) {
        Page<ReviewResponse> reviewList = reviewService.reviewViewList(pageable);
        model.addAttribute("reviews", reviewList);

        int nowPage = reviewList.getPageable().getPageNumber()+1; // 현재 페이지 (pageable이 갖고 있는 페이지는 0부터이기 때문에 +1)
        int block = (int) Math.ceil(nowPage/5.0); // 페이지 구간 (5페이지 - 1구간)
        int startPage = (block - 1) * 5 + 1; // 블럭에서 보여줄 시작 페이지
        int endPage = Math.min(startPage + 4, reviewList.getTotalPages()); // 블럭에서 보여줄 마지막 페이지
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if(isDel != null && isDel == 1){
            model.addAttribute("msg", "리뷰가 삭제 되었습니다.");
        }

        return "admins/admin_review_index";
    }

    @PostMapping("/admin/review/delete")
    @ResponseBody
    public String deleteAdminReviewSelect(@RequestParam(required = false) List<Long> reviewIds) {
        if(reviewIds != null) {
            reviewService.deleteByIds(reviewIds);
            return "delete";
        }
        else {
            return "null";
        }
    }
}
