package com.example.recode.controller.user;

import com.example.recode.domain.QnA;
import com.example.recode.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class QnAController {

    @Autowired
    private QnAService qnaService;

    @GetMapping("/queryList")
    public String listQnAs(Model model) {
        model.addAttribute("qna", qnaService.findAll());
        return "board/queryList"; // HTML 파일의 이름
    }

    @GetMapping("/query/{id}")
    public String getQnaById(@PathVariable Long id, Model model) {
        QnA qna = qnaService.findById(id);
        model.addAttribute("qna", qna);
        return "board/queryTxt";
    }


}
