package com.example.recode.service;

import com.example.recode.domain.Product;
import com.example.recode.domain.ProductImg;
import com.example.recode.dto.ProductDetailViewResponse;
import com.example.recode.repository.ProductImgRepository;
import com.example.recode.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;

    public Product findProductByProductId(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found product"));
    }

    public List<ProductImg> findProductImgByProductId(long productId){
        return productImgRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found imgs"));
    }

    public List<Product> findProductByProductIdInAndProductSold(List<Long> productIds){

        return productRepository.findAllByProductIdInAndProductSold(productIds, 1)
                .orElseThrow(() -> new IllegalArgumentException("not found product"));
    }

    public ProductDetailViewResponse getProductInfoByProductId(long productId){
        List<String> productDetailImgs = findProductImgByProductId(productId).stream().map(productImg -> productImg.getProductImgSrc()).toList();
        Product product = findProductByProductId(productId);

        return ProductDetailViewResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productModel(product.getProductModel())
                .productRepImg(product.getProductRepresentativeImgSrc())
                .type(product.getProductType())
                .color(product.getProductColor())
                .regularPrice(product.getProductRegularPrice())
                .discountPrice(product.getProductDiscountPrice() == null ? null : product.getProductDiscountPrice())
                .productDetailImgs(productDetailImgs)
                .build();
    }
}
