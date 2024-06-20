package com.example.recode.controller;

import com.example.recode.domain.Address;
import com.example.recode.dto.AddressManagementViewRequest;
import com.example.recode.dto.PaymentViewRequest;
import com.example.recode.service.AddressService;
import com.example.recode.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final AddressService addressService;
    private final CartService cartService;

    @GetMapping("/user/address/list")
    public String addressList(Model model, Principal principal){

        addressService.addressListEasyView(principal);
        model.addAttribute("addresses", addressService.addressListEasyView(principal));

        return "users/deliveryAddressList";
    }

//    @GetMapping("/user/address/management2")
//    public String deliveryAddressManagement1(Model model){
//        model.addAttribute("addressId", null);
//        return "users/deliveryAddressManagement";
//    }

    @PostMapping("/user/address/managementView")
    public String deliveryAddressManagement(AddressManagementViewRequest request, Model model){

        if(request.getAddressId() != null){
            Address address = addressService.findAddressByAddressId(request.getAddressId());
            model.addAttribute("address", address);
        }

        return "users/deliveryAddressManagement";
    }

    @GetMapping("/user/cart")
    public String cartList(Model model, Principal principal){

        model.addAttribute("carts", cartService.findCartByPrincipal(principal));

        System.err.println(cartService.findCartByPrincipal(principal));
        return "users/cart";
    }

    @PostMapping("/user/paymentView")
    public String paymentView(Model model, PaymentViewRequest request, Principal principal){

        model.addAttribute("addressNicknameList", addressService.getAddressNicknameList(principal));
        model.addAttribute("cartInfoList", cartService.getCartInfoList(request));
        model.addAttribute("defaultAddressInfo", addressService.findAddressByUserIdAndAddressDefault(principal));

        System.err.println(request);
        System.err.println(cartService.getCartInfoList(request));

        return "users/payment";
    }


}
