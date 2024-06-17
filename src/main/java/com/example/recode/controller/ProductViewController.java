package com.example.recode.controller;

import com.example.recode.service.ProductService;
import com.example.recode.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;
    private final QnAService qnAService;


    @GetMapping("/productDetail/{productId}")
    public String showProductDetails(@PathVariable long productId, Model model){

        model.addAttribute("product", productService.getProductInfoByProductId(productId));
        model.addAttribute("QnAs", qnAService.findQnAByProductId(productId));
        model.addAttribute("QnAPages", qnAService.getQnAByProductIdPaging(productId, 10));
        model.addAttribute("QnATotalSize", qnAService.QnATotalSize(productId));
        return "product/productDetail";
    }
}
