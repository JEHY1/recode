package com.example.recode.controller.admin;

import com.example.recode.domain.Notice;
import com.example.recode.domain.Product;
import com.example.recode.domain.ProductImg;
import com.example.recode.dto.UploadProductRequest;
import com.example.recode.service.ProductService;
import com.example.recode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping(value = {"/admin/product/insert", "/admin/product/{productId}/insert"}) // admin_product_insert 상품 등록&수정 페이지
    public String newAdminProduct(@PathVariable(required = false) Long productId, Model model) {
        if(productId != null) { // 상품정보 수정
            Product productEntity = productService.findProductByProductId(productId);
            model.addAttribute("product", productEntity);
        }

        return "admins/admin_product_insert";
    }

    @PostMapping("/admin/product/upload") // 상품 업로드
    public String uploadProduct(UploadProductRequest request, RedirectAttributes rttr){
        Product product = productService.uploadProduct(request);
        if(request.getProductId() == null) {
            rttr.addFlashAttribute("msg", "상품이 등록 되었습니다.");
        }
        else {
            rttr.addFlashAttribute("msg", "상품정보가 수정 되었습니다.");
        }
        return "redirect:/admin/product/" + product.getProductId() + "/show";
    }

//    @GetMapping("/admin/product/{productId}/delete") // 상품 삭제
//    public String deleteAdminNotice(@PathVariable Long productId, RedirectAttributes rttr, Integer page, Integer category, String searchKeyword) throws UnsupportedEncodingException {
//        productService.deleteById(productId);
//        rttr.addFlashAttribute("msg", "상품이 삭제 되었습니다.");
//        String encodeSearchKeyword = URLEncoder.encode(searchKeyword, StandardCharsets.UTF_8); // ASCII 아닌 파라미터 percent encoding
//        return "redirect:/admin/product/index?page=" + page + "&category=" + category + "&searchKeyword=" + encodeSearchKeyword;
//    }

    @GetMapping("/admin/product/{productId}/show") // admin_product_show 상품 보기 페이지
    public String showAdminProduct(@PathVariable Long productId, Model model) {
        Product productEntity = productService.findProductByProductId(productId);
        model.addAttribute("product", productEntity);
        List<ProductImg> productImgList = productService.findProductImgByProductId(productId);
        model.addAttribute("productImgs", productImgList);
        return "admins/admin_product_show";
    }
    @GetMapping("/admin/product/index") // admin_product_index 상품 목록 페이지
    public String indexAdminProduct() {
        return "admins/admin_product_index";
    }

}
