package com.example.recode.controller.admin;

import com.example.recode.domain.User;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AdminUserController {

    final private UserService userService;

    @GetMapping("/admin/user/{userId}/show") // admin_user_show 회원정보 보기 페이지
    public String showAdminUser(@PathVariable Long userId, Model model) {
        User userEntity = userService.findById(userId);
        model.addAttribute("user", userEntity);

        return "admins/admin_user_show";
    }
    @GetMapping("/admin/user/{userId}/edit") // admin_user_edit 회원정보 수정 페이지
    public String editAdminUser(@PathVariable Long userId) {
        return "admins/admin_user_edit";
    }
    @GetMapping("/admin/user/index") // admin_user_index 회원 목록 페이지
    public String indexAdminUser() {
        return "admins/admin_user_index";
    }
}
