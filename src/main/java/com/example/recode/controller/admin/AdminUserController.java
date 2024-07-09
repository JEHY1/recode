package com.example.recode.controller.admin;

import com.example.recode.domain.Address;
import com.example.recode.domain.User;
import com.example.recode.dto.AdminUserRequest;
import com.example.recode.service.AddressService;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String updateAdminUser(AdminUserRequest request, RedirectAttributes rttr) {
        addressService.updateAdminUserAddress(request);
        rttr.addFlashAttribute("msg", "회원정보가 수정 되었습니다.");

        return "redirect:/admin/user/" + request.getUserId() + "/show";
    }

    @GetMapping("/admin/user/{userId}/delete")
    public String deleteAdminUser(@PathVariable Long userId, RedirectAttributes rttr) {
        userService.deleteUser(userId);
        rttr.addFlashAttribute("msg", "회원이 삭제 되었습니다.");

        return "redirect:/admin/user/index";
    }

    @GetMapping("/admin/user/index") // admin_user_index 회원 목록 페이지
    public String indexAdminUser(Model model, @PageableDefault(page = 0, size = 10, sort = "userRealName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> userList = userService.userList(pageable);
        model.addAttribute("users", userList);

        int nowPage = userList.getPageable().getPageNumber()+1; // 현재 페이지 (pageable이 갖고 있는 페이지는 0부터이기 때문에 +1)
        int block = (int) Math.ceil(nowPage/5.0); // 페이지 구간 (5페이지 - 1구간)
        int startPage = (block - 1) * 5 + 1; // 블럭에서 보여줄 시작 페이지
        int endPage = Math.min(startPage + 4, userList.getTotalPages()); // 블럭에서 보여줄 마지막 페이지
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admins/admin_user_index";
    }

}
