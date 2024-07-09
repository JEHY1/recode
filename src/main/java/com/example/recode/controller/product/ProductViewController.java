package com.example.recode.controller.product;

import com.example.recode.service.ProductService;
import com.example.recode.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;
    private final QnAService qnAService;


    //상품 상세 페이지
    @GetMapping("/product/productDetail/{productId}")
    public String showProductDetails(@PathVariable long productId, Model model){

        model.addAttribute("product", productService.getProductInfoByProductId(productId));
        model.addAttribute("QnAs", qnAService.findQnAByProductId(productId));
        model.addAttribute("QnAPages", qnAService.getQnAByProductIdPaging(productId, 10));
        model.addAttribute("QnATotalSize", qnAService.QnATotalSize(productId));
        return "product/productDetail";
    }

    @GetMapping("/product/productGroup")
    public String showProductGroup(@RequestParam(required = false) String searchText, @RequestParam(required = false) String productCategory, @PageableDefault(page = 0, size = 16) Pageable pageable, Model model){

        model.addAttribute("products", productService.searchProduct(searchText, productCategory, pageable));
        return "product/productGroup";
    }
}
