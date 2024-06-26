package com.example.recode.controller.user;

import com.example.recode.domain.Notice;
import com.example.recode.dto.NoticeDto;
import com.example.recode.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/noticeList")
    public String getAllNotices(Model model) {
        List<NoticeDto> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "/board/noticeList";
    }

    @GetMapping("/notice/{id}")
    public String getNoticeById(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "board/noticeTxt";
    }
}