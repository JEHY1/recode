package com.example.recode.controller.api;

import com.example.recode.domain.Payment;
import com.example.recode.dto.PaymentRequest;
import com.example.recode.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PaymentApiController {

    private final PaymentService paymentService;
    @PostMapping("/user/payment")
    public ResponseEntity<Payment> pay(@RequestBody PaymentRequest request, Principal principal){
        System.err.println(request);
        paymentService.payment(request, principal);


        return null;
    }
}
