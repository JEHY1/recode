package com.example.recode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceInfoController {

    @GetMapping("/servicePolicy")
    public String brandInfo(Model model) {
        return "etc/servicePolicy";
    }
}
