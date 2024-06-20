package com.example.recode.controller.admin;

import com.example.recode.domain.Notice;
import com.example.recode.dto.NoticeRequest;
import com.example.recode.dto.NoticeViewResponse;
import com.example.recode.service.NoticeService;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminNoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping(value = {"/admin/notice/insert", "/admin/notice/{noticeId}/insert"}) // admin_notice_insert 공지사항 등록&수정 페이지
    public String insertAdminNotice(@PathVariable(required = false) Long noticeId, Model model) {
        if(noticeId != null) {
            Notice noticeEntity = noticeService.findById(noticeId);
            model.addAttribute("notice", noticeEntity);
        }

        return "admins/admin_notice_insert";
    }
    @PostMapping("/admin/notice/create") // admin_notice_insert 공지사항 등록&수정 post
    public String createAdminNotice(NoticeRequest request, Principal principal, RedirectAttributes rttr) {
        Notice saved = noticeService.save(request, principal);
        if(request.getNoticeId() == null) {
            rttr.addFlashAttribute("msg", "등록 되었습니다.");
        }
        else {
            rttr.addFlashAttribute("msg", "수정 되었습니다.");
        }

        return "redirect:/admin/notice/" + saved.getNoticeId() + "/show";
    }
    @GetMapping("/admin/notice/{noticeId}/delete") // 공지사항 삭제
    public String deleteAdminNotice(@PathVariable Long noticeId, RedirectAttributes rttr) {
        noticeService.deleteById(noticeId);
        rttr.addFlashAttribute("msg", "삭제 되었습니다.");

        return "redirect:/admin/notice/index";
    }

    @GetMapping("/admin/notice/{noticeId}/show") // admin_notice_insert 공지사항 보기 페이지
    public String showAdminNotice(@PathVariable Long noticeId, Model model) {
        Notice noticeEntity = noticeService.findById(noticeId);
        model.addAttribute("notice", noticeEntity);
        String username = userService.getUsername(noticeEntity.getUserId());
        model.addAttribute("username", username);
//        noticeService.updateView(noticeId); // 조회수 증가 - 사용자 페이지에서 하기

        return "admins/admin_notice_show";
    }

    @GetMapping("/admin/notice/index") // admin_notice_insert 공지사항 목록 페이지
    public String indexAdminNotice(Model model) {
        List<NoticeViewResponse> noticeList = noticeService.getAllNoticeInfo();
        model.addAttribute("notices", noticeList);

        return "admins/admin_notice_index";
    }

}
