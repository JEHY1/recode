package com.example.recode.controller.api;

import com.example.recode.dto.*;
import com.example.recode.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final PaymentService paymentService;

    @PostMapping("/admin/getIncludeNameList")
    public ResponseEntity<List<ProductNameForm>> searchProduct(@RequestBody IncludeNameListRequest request){

        return ResponseEntity.ok()
                .body(paymentService.getPaymentDetailProductInProductName(request.getProductName()));
    }

    @PostMapping("/admin/getIncludeUserRealNameList")
    public ResponseEntity<List<UserRealNameForm>> searchUserName(@RequestBody IncludeUserRealNameListRequest request){

        System.err.println(paymentService.getUserNameInfoInUsername(request.getUsername()));

        return ResponseEntity.ok()
                .body(paymentService.getUserNameInfoInUsername(request.getUsername()));
    }

    @PostMapping("/admin/getServerDate")
    public ResponseEntity<DateForm> getServerDate(@RequestBody GetPeriodRequest request){

        System.err.println(request.getUnitPeriod());

        return ResponseEntity.ok()
                .body(paymentService.getServerDate(request.getUnitPeriod()));
    }
}


