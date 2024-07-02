package com.example.recode.controller.admin;

import com.example.recode.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderManagerController {

    private final PaymentService paymentService;

    @GetMapping("/admin/orderManager")
    public String orderManager(@RequestParam(required = false) String productName, @RequestParam(required = false) String username, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false) Integer minPrice, @RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) String paymentStatus, @RequestParam(required = false) String paymentDetailStatus,  Model model){
        System.err.println(productName);
        System.err.println(username);
        System.err.println(startDate);
        System.err.println(endDate);
        System.err.println(minPrice);
        System.err.println(maxPrice);
        System.err.println(paymentStatus);
        System.err.println(paymentDetailStatus);

        paymentService.getPaymentInfos(productName, username, startDate, endDate, minPrice, maxPrice, paymentStatus, paymentDetailStatus);

        model.addAttribute("serverDate", paymentService.getServerDate(null));

        return "/admins/orderManager";
    }
}
