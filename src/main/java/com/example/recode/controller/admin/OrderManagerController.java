package com.example.recode.controller.admin;

import com.example.recode.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderManagerController {

    private final PaymentService paymentService;

    @GetMapping("/admin/orderManager")
    public String orderManager(@RequestParam(required = false) String productName, @RequestParam(required = false) String username, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false) Integer minPrice, @RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) String paymentStatus, @RequestParam(required = false) String paymentDetailStatus,  Model model){

        model.addAttribute("serverDate", paymentService.getServerDate(null));
        model.addAttribute("orderInfoPage", paymentService.getPaymentInfos(productName, username, startDate, endDate, minPrice, maxPrice, paymentStatus, paymentDetailStatus));

        System.err.println(paymentService.getPaymentInfos(productName, username, startDate, endDate, minPrice, maxPrice, paymentStatus, paymentDetailStatus));

        return "/admins/orderManager";
    }

    @GetMapping("/admin/orderDetailManager/{paymentId}")
    public String orderDetailManager(@PathVariable long paymentId, Model model){
        System.err.println(paymentId);

        model.addAttribute("orderDetailList", paymentService.getOrderDetailInfo(paymentId));


        model.addAttribute("payment", paymentService.findPaymentByPaymentId(paymentId));

        return "/admins/orderDetailManager";
    }
}
