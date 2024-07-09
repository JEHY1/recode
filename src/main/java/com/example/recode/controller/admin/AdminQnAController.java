package com.example.recode.controller.admin;

import com.example.recode.domain.Product;
import com.example.recode.domain.QnA;
import com.example.recode.dto.NoticeViewResponse;
import com.example.recode.dto.QnAViewResponse;
import com.example.recode.dto.QnaAnswerRequest;
import com.example.recode.service.ProductService;
import com.example.recode.service.QnAService;
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
            rttr.addFlashAttribute("msg", "문의답변이 등록 되었습니다.");
        }
        else {
            rttr.addFlashAttribute("msg", "문의답변이 수정 되었습니다.");
        }
        return "redirect:/admin/qna/" + saved.getQnAId() + "/show";
    }
    @GetMapping("/admin/qna/{QnAId}/delete") // 상품문의 삭제
    public String deleteAdminQna(@PathVariable Long QnAId, RedirectAttributes rttr, Integer page) {
        qnAService.deleteById(QnAId);
        rttr.addFlashAttribute("msg", "상품문의가 삭제 되었습니다.");
        return "redirect:/admin/qna/index?page=" + page;
    }
    @GetMapping("/admin/qna/{QnAId}/answer/delete") // 답변 삭제
    public String deleteAdminQnaAnswer(@PathVariable Long QnAId, RedirectAttributes rttr) {
        qnAService.deleteAnswer(QnAId);
        rttr.addFlashAttribute("msg", "문의답변이 삭제 되었습니다.");
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
    public String indexAdminQna(Model model, @PageableDefault(page = 0, size = 10, sort = "qnAId", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) Integer isDel) {
        Page<QnAViewResponse> qnAList = qnAService.qnAViewList(pageable);
        model.addAttribute("QnAs", qnAList);

        int nowPage = qnAList.getPageable().getPageNumber()+1; // 현재 페이지 (pageable이 갖고 있는 페이지는 0부터이기 때문에 +1)
        int block = (int) Math.ceil(nowPage/5.0); // 페이지 구간 (5페이지 - 1구간)
        int startPage = (block - 1) * 5 + 1; // 블럭에서 보여줄 시작 페이지
        int endPage = Math.min(startPage + 4, qnAList.getTotalPages()); // 블럭에서 보여줄 마지막 페이지
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if(isDel != null && isDel == 1){
            model.addAttribute("msg", "상품문의가 삭제 되었습니다.");
        }

        return "admins/admin_qna_index";
    }

    @PostMapping("/admin/qna/delete")
    @ResponseBody
    public String deleteAdminQnASelect(@RequestParam(required = false) List<Long> qnAIds) {
        if(qnAIds != null) {
            qnAService.deleteByIds(qnAIds);
            return "delete";
        }
        else {
            return "null";
        }
    }

}
