package com.example.recode.controller;

import com.example.recode.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;

    @GetMapping("/productDetail/{productId}")
    public String showProductDetails(@PathVariable long productId, Model model){
        return "product/productDetail";
    }
}
