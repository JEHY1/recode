package com.example.recode.controller;

import com.example.recode.domain.User;
import com.example.recode.dto.JoinRequest;
import com.example.recode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;

    @GetMapping("/join/new") // join 화면
    public String newJoin() {
        return "logins/join";
    }

    @PostMapping("/join/create") // join 폼 만들기
    public String createJoin(JoinRequest request) {
        Long userId = userService.save(request);
        return "redirect:/join/" + userId + "/finish";
    }
    
    @GetMapping("/join/{userId}/finish") // join_finish 화면
    public String showJoinFinish(@PathVariable Long userId, Model model) {
        User userEntity = userService.findById(userId);
        model.addAttribute("join", userEntity);
        return "logins/join_finish";
    }

    @GetMapping("/login") // login 화면
    public String login() {
        return "logins/login";
    }
    @GetMapping("/logout") // logout
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("/idfind") // idfind 화면
    public String showIdfind() {
        return "logins/idfind";
    }
    @GetMapping("/pwfind") // pwfind 화면
    public String showPwfind() {
        return "logins/pwfind";
    }
}
