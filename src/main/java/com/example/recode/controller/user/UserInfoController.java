package com.example.recode.controller.user;

import com.example.recode.domain.User;
import com.example.recode.dto.PwCheckRequest;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/user/information/confirm/new") // user_information_confirm 페이지
    public String newUserInformationConfirm(Principal principal, Model model) {
        User userEntity = userService.getUserByPrincipal(principal);
        model.addAttribute("user", userEntity);
        return "users/user_information_confirm";
    }

    @PostMapping("/user/information/confirm/create") // user_information_confirm 페이지 post
    public String createUserInformationConfirm(PwCheckRequest request, Principal principal, RedirectAttributes rttr) {
        Boolean pwCheck = userService.pwCheck(request, principal); // 비밀번호 일치 시 true
        if(pwCheck) {
            return "redirect:/user/information/modify";
        }
        else {
            rttr.addFlashAttribute("pwCheck", "비밀번호를 다시 확인해주세요");
            return "redirect:/user/information/confirm/new";
        }
    }

    @GetMapping("/user/information/modify") // user_information_modify 페이지
    public String showUserInformationModify() {
        return "users/user_information_modify";
    }

    @GetMapping("/user/delete/confirm") // user_delete_confirm 페이지
    public String showUserDeleteConfirm() {
        return "users/user_delete_confirm";
    }

    @GetMapping("/user/delete/finish") // user_delete_finish 페이지
    public String showUserDeleteFinish() {
        return "users/user_delete_finish";
    }

}
