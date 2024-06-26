package com.example.recode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrandController {
    @GetMapping("/etc/brand")
    public String brandInfo(Model model) {
//        model.addAttribute("brandName", "RECODE");
//        model.addAttribute("brandDescription", "This is a description of your brand.");
        return "etc/brandInfo";
    }
}