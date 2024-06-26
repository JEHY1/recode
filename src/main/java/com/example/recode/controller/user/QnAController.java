package com.example.recode.controller;

import com.example.recode.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnAController {

    @Autowired
    private QnAService qnaService;

    @GetMapping("/qnaList")
    public String listQnAs(Model model) {
        model.addAttribute("qnas", qnaService.findAll());
        return "board/queryList"; // HTML 파일의 이름
    }
}
