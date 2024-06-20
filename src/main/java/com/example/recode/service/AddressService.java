package com.example.recode.service;

import com.example.recode.domain.Address;
import com.example.recode.dto.AddressEasyViewResponse;
import com.example.recode.dto.AddressNicknameListResponse;
import com.example.recode.dto.ManagementAddressRequest;
import com.example.recode.repository.AddressRepository;
import com.example.recode.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    @Transactional
    public Address managementAddress(ManagementAddressRequest request, Principal principal){

//        findAddressByUserIdAndAddressDefault(userService.getUserId(principal)).updateDefault(); //로그인 구현 후
        if(request.getAddressDefault() == 1 && findAddressByUserIdAndAddressDefault(1L) != null){
            findAddressByUserIdAndAddressDefault(1L).updateDefault();
        }


        if(request.getAddressId() == null){//신규 등록

            return addressRepository.save(Address.builder()
                    .userId(1L) //임시 값 1
//                    .userId(userService.getUserId(principal)) //로그인 구현 후
                    .addressPostalCode(request.getPostalCode())
                    .addressRoadNameAddress(request.getRoadNameAddress())
                    .addressDetailAddress(request.getDetailAddress())
                    .addressRecipientName(request.getRecipientName())
                    .addressRecipientPhone(request.getRecipientPhone())
                    .addressDeliveryRequest(request.getDeliveryRequest())
                    .addressFrontDoorSecret(!request.getFrontDoorSecret().equals("") ? request.getFrontDoorSecret() : null)
                    .addressDeliveryBoxNum(!request.getDeliveryBoxNum().equals("") ? request.getDeliveryBoxNum() : null)
                    .addressNickname(request.getAddressNickname())
                    .addressDefault(request.getAddressDefault())
                    .build());
        }
        else{ //수정
            System.err.println("call address update");
            return findAddressByAddressId(request.getAddressId()).update(request);
        }

    }

    public List<AddressEasyViewResponse> addressListEasyView(Principal principal){
//        List<AddressEasyViewResponse> easyViewList = findAddressByUserId(userService.getUserId(principal)).stream().map(AddressEasyViewResponse::new).collect(Collectors.toList()); 로그인 구현 후
        List<AddressEasyViewResponse> easyViewList = findAddressByUserId(1L).stream().map(AddressEasyViewResponse::new).collect(Collectors.toList());
//        List<AddressEasyViewResponse> easyViewList = findAddressByUserId(1L).stream().map(AddressEasyViewResponse::new).toList(); 그냥 toList() 하면 불변 리스트가 됨

        easyViewList.sort((o1, o2) -> o2.getAddressDefault() - o1.getAddressDefault());

        return easyViewList;
    }

    public List<AddressNicknameListResponse> getAddressNicknameList(Principal principal){


        List<AddressNicknameListResponse> list = findAddressByUserId(1L).stream().map(AddressNicknameListResponse::new).collect(Collectors.toList());
//        List<AddressNicknameListResponse> list = findAddressByUserId(userService.getUserId(principal)).stream().map(AddressNicknameListResponse::new).collect(Collectors.toList()); 로그인 구현 후
        list.sort((address1, address2) -> address2.getAddressDefault() - address1.getAddressDefault());
        return list;
    }

    public Address findAddressByAddressId(long addressId){

        return addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("not found address"));
    }

    public Address findAddressByUserIdAndAddressDefault(long userId){
        return addressRepository.findByUserIdAndAddressDefault(userId, 1)
                .orElse(null);
    }

    public Address findAddressByUserIdAndAddressDefault(Principal principal){
//        return findAddressByUserIdAndAddressDefault(userService.getUserId(principal)); //로그인 구현 후
        return findAddressByUserIdAndAddressDefault(1L);
    }

    public List<Address> findAddressByUserId(long userId){
        return addressRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found address"));
    }

    public void deleteAddressByAddressId(long addressId){
        addressRepository.deleteById(addressId);
    }
}
