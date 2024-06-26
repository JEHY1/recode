package com.example.recode.controller.admin;

import com.example.recode.domain.Address;
import com.example.recode.domain.User;
import com.example.recode.dto.AdminUserRequest;
import com.example.recode.service.AddressService;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUserController {

    final private UserService userService;
    final private AddressService addressService;

    @GetMapping("/admin/user/{userId}/show") // admin_user_show 회원정보 보기 페이지
    public String showAdminUser(@PathVariable Long userId, Model model) {
        User userEntity = userService.findById(userId);
        model.addAttribute("user", userEntity);
        List<Address> addressList = addressService.findAddressByUserId(userId);
        model.addAttribute("addresses", addressList);

        return "admins/admin_user_show";
    }
    @GetMapping("/admin/user/{userId}/edit") // admin_user_edit 회원정보 수정 페이지
    public String editAdminUser(@PathVariable Long userId, Model model) {
        User userEntity = userService.findById(userId);
        model.addAttribute("user", userEntity);
        List<Address> addressList = addressService.findAddressByUserId(userId);
        model.addAttribute("addresses", addressList);

        return "admins/admin_user_edit";
    }

    @PostMapping("/admin/user/update") // admin_user_edit 회원정보 수정 post
    public String updateAdminUser(AdminUserRequest request) {
        User updated = userService.updateAdminUser(request);

        return "redirect:/admin/user/index";
    }
    @GetMapping("/admin/user/index") // admin_user_index 회원 목록 페이지
    public String indexAdminUser() {
        return "admins/admin_user_index";
    }
}
