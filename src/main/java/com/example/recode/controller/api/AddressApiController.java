package com.example.recode.controller.api;

import com.example.recode.domain.Address;
import com.example.recode.dto.AddressDeleteRequest;
import com.example.recode.dto.GetAddressInfoRequest;
import com.example.recode.dto.ManagementAddressRequest;
import com.example.recode.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AddressApiController {

    private final AddressService addressService;

    @PostMapping("/user/address/management")
    public ResponseEntity<Address> addressManagement(@RequestBody ManagementAddressRequest request, Principal principal){
        System.err.println(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.managementAddress(request, principal));
    }

    @PostMapping("/user/address/getAddressInfo")
    public ResponseEntity<Address> responseAddressInfo(@RequestBody GetAddressInfoRequest request, Principal principal){
        System.err.println("call getAddressInfo");

        if(principal == null){
            return ResponseEntity.ok()
                    .body(addressService.findAddressByAddressId(request.getAddressId()));
        }
        else{
            return ResponseEntity.ok()
                    .body(addressService.findAddressByAddressId(request.getAddressId()));
        }

    }

    @DeleteMapping("/user/address/delete")
    public ResponseEntity<Void> deleteAddress(@RequestBody AddressDeleteRequest request){

        addressService.deleteAddressByAddressId(request.getAddressId());
        return ResponseEntity.ok()
                .build();
    }
}
