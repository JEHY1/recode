package com.example.recode.controller.product;

import com.example.recode.domain.Product;
import com.example.recode.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String showMain(Model model) {
        List<Product> productList = productService.newProduct();
        model.addAttribute("products", productList);
        return "mains/main";
    }
}
