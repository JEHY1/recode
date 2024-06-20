package com.example.recode.controller.admin;

import com.example.recode.domain.Product;
import com.example.recode.domain.ProductImg;
import com.example.recode.domain.Review;
import com.example.recode.service.ProductService;
import com.example.recode.service.ReviewService;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        Review reviewEntity = reviewService.findById(reviewId);
        model.addAttribute("review", reviewEntity);
        String username = userService.getUsername(reviewEntity.getUserId());
        model.addAttribute("username", username);
        Product productEntity = productService.findProductByProductId(reviewEntity.getProductId());
        model.addAttribute("product", productEntity);
        List<ProductImg> productImgList = productService.findProductImgByProductId(reviewEntity.getProductId());
        model.addAttribute("productImgs", productImgList);

        return "admins/admin_review_show";
    }

    @GetMapping("/admin/review/{reviewId}/delete") // 리뷰 삭제
    public String deleteAdminReview(@PathVariable Long reviewId, RedirectAttributes rttr) {
        reviewService.deleteById(reviewId);
        rttr.addFlashAttribute("msg", "삭제 되었습니다.");

        return "redirect:/admin/review/index";
    }
    @GetMapping("/admin/review/index") // admin_review_index 리뷰 목록 페이지
    public String indexAdminReview() { // 매핑만 해놓은 것 - 수정해야함

        return "admins/admin_review_index";
    }
}
