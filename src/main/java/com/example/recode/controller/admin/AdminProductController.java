package com.example.recode.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AdminProductController {

    @GetMapping("/admin/product/insert") // admin_notice_insert 상품 등록 페이지
    public String newAdminProduct() {
        return "admins/admin_product_insert";
    }
    @GetMapping("/admin/product/{productId}/show") // admin_product_show 상품 보기 페이지
    public String showAdminProduct(@PathVariable Long productId) {
        return "admins/admin_product_show";
    }
    @GetMapping("/admin/product/index") // admin_product_index 상품 목록 페이지
    public String indexAdminProduct() {
        return "admins/admin_product_index";
    }

}
