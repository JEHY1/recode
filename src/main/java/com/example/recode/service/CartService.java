package com.example.recode.service;

import com.example.recode.domain.Cart;
import com.example.recode.domain.Product;
import com.example.recode.dto.*;
import com.example.recode.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    public Cart save(AddCartRequest request, Principal principal){

//        long userId = userService.getUserId(principal); 로그인 구현 후
        long userId = 1L;

        Cart cart = cartRepository.findByUserIdAndProductId(userId, request.getProductId())
                .orElse(null);

        if(cart == null){
            return cartRepository.save(Cart.builder()
                    .productId(request.getProductId())
                    .userId(userId)
                    .build());
        }
        else{
            return cart;
        }
    }

    public List<PaymentCartInfoViewResponse> getCartInfoList(PaymentViewRequest request){

        List<PaymentCartInfoViewResponse> list = new ArrayList<>();

        request.getCartId().forEach(cartId -> {
            Product product = findProductByCartId(cartId);
            list.add(PaymentCartInfoViewResponse.builder()
                    .cartId(cartId)
                    .productId(product.getProductId())
                    .productRegularPrice(product.getProductRegularPrice())
                    .productDiscountPrice(product.getProductDiscountPrice())
                    .productRepImgSrc(product.getProductRepresentativeImgSrc())
                    .productName(product.getProductName())
                    .build());
        });

        return list;
    }

    public Cart findCartByCartId(long cartId){

        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("not found cart"));
    }

    public Product findProductByCartId(long cartId){
        return productService.findProductByProductId(findCartByCartId(cartId).getProductId());
    }

    public void delete(DeleteCartRequest request){
        cartRepository.deleteById(request.getCartId());
    }


    public void deleteList(DeleteCartListRequest request){

        request.getCartIds().forEach(cartId -> cartRepository.deleteById(cartId));
    }

    public List<Product> cleanCart(CleanCartRequest request){
        List<Cart> carts = cartRepository.findAllByCartIdIn(request.getCartIds())
                .orElseThrow(() -> new IllegalArgumentException("not found cart"));

        System.err.println(carts);

        List<Long> productIds = carts.stream().mapToLong(cart -> cart.getProductId()).boxed().toList();
        List<Product> products = productService.findProductByProductIdInAndProductSold(productIds);
        System.err.println(products);

        //품절된 상품의 cartId를 추출
        List<Long> willDeleteCartIds = carts.stream().mapToLong(cart -> {
            boolean isSold = false;
            for(Product product : products){
                if(product.getProductId() == cart.getProductId()){
                    isSold = true;
                    break;
                }
            }

            if(isSold){
                return cart.getCartId();
            }
            else{
                return -1;
            }
        })
        .boxed().toList();
        System.err.println(willDeleteCartIds);

        willDeleteCartIds.forEach(cartId -> {
            if(cartId != -1){
                cartRepository.deleteById(cartId);
            }
        });

        return products;
    }

    public List<CartViewResponse> findCartByPrincipal(Principal principal){
        List<Cart> carts = cartRepository.findByUserId(1L)
                .orElseThrow(() -> new IllegalArgumentException("not found cart"));

        List<CartViewResponse> list = new ArrayList<>();
        carts.forEach(cart -> list.add(new CartViewResponse(cart.getCartId(), productService.findProductByProductId(cart.getProductId()))));
        return list;
    }
}
