package com.example.recode.controller.user;

import com.example.recode.domain.Notice;
import com.example.recode.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notices")
    public String getAllNotices(Model model,@PageableDefault(page = 0, size = 10,sort = "noticeId",direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Notice> list = noticeService.getAllNotices(pageable);

        System.err.println(list.getContent());

        int firstPage = list.getPageable().getPageNumber() +1 ;

        int startPage = Math.max(-4, 1);

        int lastPage = Math.min(firstPage +5, list.getTotalPages());


        model.addAttribute("list", list);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("lastPage", lastPage);


        return "/board/noticeList";
    }

    @GetMapping("/notice/{id}")
    public String getNoticeById(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "board/noticeTxt";
    }
}