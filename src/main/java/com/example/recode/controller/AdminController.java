package com.example.recode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/admin/notice/insert") /* admin_notice_insert 공지사항 등록 페이지 */
    public String newAdminNotice() {
        return "admins/admin_notice_insert";
    }
    @GetMapping("/admin/notice/show") /* admin_notice_insert 공지사항 보기 페이지 */
    public String showAdminNotice() {
        return "admins/admin_notice_show";
    }
    @GetMapping("/admin/notice/index") /* admin_notice_insert 공지사항 목록 페이지 */
    public String indexAdminNotice() {
        return "admins/admin_notice_index";
    }
    @GetMapping("/admin/product/insert") /* admin_notice_insert 상품 등록 페이지 */
    public String newAdminProduct() {
        return "admins/admin_product_insert";
    }
    @GetMapping("/admin/product/show") /* admin_notice_insert 상품 보기 페이지 */
    public String showAdminProduct() {
        return "admins/admin_product_show";
    }
    @GetMapping("/admin/product/index") /* admin_notice_insert 상품 목록 페이지 */
    public String indexAdminProduct() {
        return "admins/admin_product_index";
    }
    @GetMapping("/admin/qna/insert") /* admin_qna_insert 상품문의/답변 등록 페이지 */
    public String newAdminQna() {
        return "admins/admin_qna_insert";
    }
    @GetMapping("/admin/qna/show") /* admin_qna_show 상품문의/답변 보기 페이지 */
    public String showAdminQna() {
        return "admins/admin_qna_show";
    }
    @GetMapping("/admin/qna/index") /* admin_qna_index 상품문의 목록 페이지 */
    public String indexAdminQna() {
        return "admins/admin_qna_index";
    }
    @GetMapping("/admin/review/show") /* admin_review_show 리뷰 보기 페이지 */
    public String showAdminReview() {
        return "admins/admin_review_show";
    }
    @GetMapping("/admin/review/index") /* admin_review_index 리뷰 목록 페이지 */
    public String indexAdminReview() {
        return "admins/admin_review_index";
    }
    @GetMapping("/admin/user/show") /* admin_qna_index 회원정보 보기 페이지 */
    public String showAdminUser() {
        return "admins/admin_user_show";
    }
    @GetMapping("/admin/user/edit") /* admin_user_edit 회원정보 수정 페이지 */
    public String editAdminUser() {
        return "admins/admin_user_edit";
    }
    @GetMapping("/admin/user/index") /* admin_user_index 회원 목록 페이지 */
    public String indexAdminUser() {
        return "admins/admin_user_index";
    }
}
