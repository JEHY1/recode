package com.example.recode.controller.admin;

import com.example.recode.dto.UploadProductRequest;
import com.example.recode.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductInsertController {

    private final ProductService productService;

    //상품 업로드
    @PostMapping("/admin/upload")
    public String uploadProduct(UploadProductRequest request){
        productService.uploadProduct(request);
        return null;
    }
}
