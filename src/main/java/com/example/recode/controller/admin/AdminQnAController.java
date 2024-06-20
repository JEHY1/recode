package com.example.recode.controller.admin;

import com.example.recode.domain.Product;
import com.example.recode.domain.QnA;
import com.example.recode.dto.QnaAnswerRequest;
import com.example.recode.service.ProductService;
import com.example.recode.service.QnAService;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminQnAController {

    private final QnAService qnAService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/admin/qna/{QnAId}/insert") // admin_qna_insert 상품문의/답변 등록&수정 페이지
    public String insertAdminQna(@PathVariable Long QnAId, Model model) {
        QnA qnAEntity = qnAService.findById(QnAId);
        model.addAttribute("QnA", qnAEntity);
        String username = userService.getUsername(qnAEntity.getUserId());
        model.addAttribute("username", username);
        Product productEntity = productService.findProductByProductId(qnAEntity.getProductId());
        model.addAttribute("product", productEntity);
        return "admins/admin_qna_insert";
    }

    @PostMapping("/admin/qna/create") // admin_qna_insert 상품문의/답변 등록&수정 post
    public String createAdminQna(QnaAnswerRequest request, RedirectAttributes rttr) {
        String beforeAnswer = qnAService.findById(request.getQnAId()).getQnAAnswer();
        QnA saved = qnAService.saveAnswer(request);
        if(beforeAnswer == null) {
            rttr.addFlashAttribute("msg", "등록 되었습니다.");
        }
        else {
            rttr.addFlashAttribute("msg", "수정 되었습니다.");
        }
        return "redirect:/admin/qna/" + saved.getQnAId() + "/show";
    }
    @GetMapping("/admin/qna/{QnAId}/delete") // 상품문의 삭제
    public String deleteAdminQna(@PathVariable Long QnAId, RedirectAttributes rttr) {
        qnAService.deleteById(QnAId);
        rttr.addFlashAttribute("msg", "삭제 되었습니다.");
        return "redirect:/admin/qna/index";
    }
    @GetMapping("/admin/qna/{QnAId}/answer/delete") // 답변 삭제
    public String deleteAdminQnaAnswer(@PathVariable Long QnAId, RedirectAttributes rttr) {
        qnAService.deleteAnswer(QnAId);
        rttr.addFlashAttribute("msg", "삭제 되었습니다.");
        return "redirect:/admin/qna/" + QnAId + "/insert";
    }

    @GetMapping("/admin/qna/{QnAId}/show") // admin_qna_show 상품문의/답변 보기 페이지
    public String showAdminQna(@PathVariable Long QnAId, Model model) {
        QnA qnAEntity = qnAService.findById(QnAId);
        model.addAttribute("QnA", qnAEntity);
        String username = userService.getUsername(qnAEntity.getUserId());
        model.addAttribute("username", username);
        Product productEntity = productService.findProductByProductId(qnAEntity.getProductId());
        model.addAttribute("product", productEntity);
        return "admins/admin_qna_show";
    }
    @GetMapping("/admin/qna/index") // admin_qna_index 상품문의 목록 페이지
    public String indexAdminQna() {
        return "admins/admin_qna_index";
    }

}
