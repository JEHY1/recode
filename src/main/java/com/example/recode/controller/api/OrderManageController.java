package com.example.recode.controller.api;

import com.example.recode.domain.PaymentDetail;
import com.example.recode.dto.OrderManageRequest;
import com.example.recode.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderManageController {

    private final PaymentService paymentService;

    @PostMapping("/user/orderManage")
    public ResponseEntity<PaymentDetail> orderManageRequest(@RequestBody OrderManageRequest request){
        return ResponseEntity.ok()
                .body(paymentService.paymentDetailManage(request));
    }

}
