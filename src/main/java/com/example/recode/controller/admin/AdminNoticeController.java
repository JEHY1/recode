package com.example.recode.controller.admin;

import com.example.recode.domain.Notice;
import com.example.recode.dto.NoticeRequest;
import com.example.recode.dto.NoticeViewResponse;
import com.example.recode.service.NoticeService;
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

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminNoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping(value = {"/admin/notice/insert", "/admin/notice/{noticeId}/insert"}) // admin_notice_insert 공지사항 등록&수정 페이지
    public String insertAdminNotice(@PathVariable(required = false) Long noticeId, Model model) {
        if(noticeId != null) { // 공지사항 수정
            Notice noticeEntity = noticeService.findById(noticeId);
            model.addAttribute("notice", noticeEntity);
        }

        return "admins/admin_notice_insert";
    }
    @PostMapping("/admin/notice/create") // admin_notice_insert 공지사항 등록&수정 post
    public String createAdminNotice(NoticeRequest request, Principal principal, RedirectAttributes rttr) {
        Notice saved = noticeService.save(request, principal);
        if(request.getNoticeId() == null) {
            rttr.addFlashAttribute("msg", "공지사항이 등록 되었습니다.");
        }
        else {
            rttr.addFlashAttribute("msg", "공지사항이 수정 되었습니다.");
        }

        return "redirect:/admin/notice/" + saved.getNoticeId() + "/show";
    }
    @GetMapping("/admin/notice/{noticeId}/delete") // 공지사항 삭제
    public String deleteAdminNotice(@PathVariable Long noticeId, RedirectAttributes rttr, Integer page, String searchKeyword) {
        noticeService.deleteById(noticeId);
        rttr.addFlashAttribute("msg", "공지사항이 삭제 되었습니다.");
        System.err.println(searchKeyword);
        return "redirect:/admin/notice/index?page=" + page + "&searchKeyword=" + searchKeyword;
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
    public String indexAdminNotice(Model model, @PageableDefault(page = 0, size = 10, sort = "noticeId", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) Integer isDel, @RequestParam(defaultValue = "") String searchKeyword) {
                                                                // default 페이지, 한 페이지 게시글 수, 정렬기준 컬럼, 정렬순서
        Page<NoticeViewResponse> noticeList = null;
        if(searchKeyword == null) {
            noticeList = noticeService.noticeViewList(pageable);
        }
        else {
            noticeList = noticeService.noticeViewTitleSearchList(searchKeyword, pageable);
        }
        model.addAttribute("notices", noticeList);
        model.addAttribute("searchKeyword", searchKeyword);

        int nowPage = noticeList.getPageable().getPageNumber()+1; // 현재 페이지 (pageable이 갖고 있는 페이지는 0부터이기 때문에 +1)
        int block = (int) Math.ceil(nowPage/5.0); // 페이지 구간 (5페이지 - 1구간)
        int startPage = (block - 1) * 5 + 1; // 블럭에서 보여줄 시작 페이지
        int lastPage = noticeList.getTotalPages() == 0 ? 1 : noticeList.getTotalPages(); // 존재하는 마지막 페이지
        int endPage = Math.min(startPage + 4, lastPage); // 블럭에서 보여줄 마지막 페이지
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if(isDel != null && isDel == 1){
            model.addAttribute("msg", "공지사항이 삭제 되었습니다.");
        }

        return "admins/admin_notice_index";
    }

    @PostMapping("/admin/notice/delete")
    @ResponseBody
    public String deleteAdminNoticeSelect(@RequestParam(required = false) List<Long> noticeIds) {
        if(noticeIds != null) {
            noticeService.deleteByIds(noticeIds);
            return "delete";
        }
        else {
            return "null";
        }
    }

}
