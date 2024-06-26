package com.example.recode.service;

import com.example.recode.domain.Payment;
import com.example.recode.domain.PaymentDetail;
import com.example.recode.domain.Product;
import com.example.recode.dto.MyPageViewResponse;
import com.example.recode.dto.PaymentRequest;
import com.example.recode.repository.CartRepository;
import com.example.recode.repository.PaymentDetailRepository;
import com.example.recode.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    //결제 요청 처리
    public Payment payment(PaymentRequest request, Principal principal){

        //결제 요청 정보로 결제 테이블 db 추가
        Payment payment =  paymentRepository.save(Payment.builder()
                .userId(userService.getUserId(principal))
                .paymentPrice(request.getPaymentPrice())
                .paymentType(request.getPaymentType())
                .paymentBank(request.getPaymentBank().equals("-1") ? null : request.getPaymentBank())
                .paymentAccountNumber(request.getPaymentAccountNumber().isEmpty() ? null : request.getPaymentAccountNumber())
                .paymentDepositor(request.getPaymentDepositor().isEmpty() ? null : request.getPaymentDepositor())
                .paymentCard(request.getPaymentCard().equals("-1") ? null : request.getPaymentCard())
                .paymentCardNumber(request.getPaymentCardNumber().isEmpty() ? null : request.getPaymentCardNumber())
                .paymentInstallment(request.getPaymentInstallment() == -1 ? null : request.getPaymentInstallment())
                .paymentPhoneCompany(request.getPaymentPhoneCompany().equals("-1") ? null : request.getPaymentPhoneCompany())
                .paymentMicropaymentPhone(request.getPaymentMicropaymentPhone().isEmpty() ? null : request.getPaymentMicropaymentPhone())
                .paymentStatus("결제대기")
                .paymentPostalCode(request.getPaymentPostalCode())
                .paymentAddress(request.getPaymentAddress())
                .paymentDeliveryRequest(request.getDeliveryRequest())
                .paymentRecipientName(request.getPaymentRecipientName())
                .paymentRecipientPhone(request.getPaymentRecipientPhone())
                .paymentDeliveryFee(request.getDeliveryFee())
                .paymentFrontDoorSecret(request.getFrontDoorSecret().isEmpty() ? null : request.getFrontDoorSecret())
                .paymentDeliveryBoxNum(request.getDeliveryBoxNum().isEmpty() ? null : request.getDeliveryBoxNum())
                .build());

        //결제에 포함된 각각의 상품에 대해 결제 상세테이블 db 추가
        request.getProductIds().forEach(productId -> {
            Product product = productService.findProductByProductId(productId);
            paymentDetailRepository.save(PaymentDetail.builder()
                    .paymentId(payment.getPaymentId())
                    .productId(productId)
                    .paymentDetailStatus("결제대기중")
                    .paymentDetailPrice(product.getProductDiscountPrice() == null ? product.getProductRegularPrice() : product.getProductDiscountPrice())
                    .build()
            );

            //상품을 판매된 상태로 변경
            product.updateSold(1);
        });

        //구매된 상품에 대한 장바구니 삭제
        if(!request.getCartIds().isEmpty()){
            request.getCartIds().forEach(cartId -> cartRepository.deleteById(cartId));
        }

        return payment;
    }

    //마이페이지에서 보여주는 정보
    public MyPageViewResponse getMyPageInfo(Principal principal){

        //이용자의 모든 결제 내역
        int paymentReadyCount = 0; //결제 대기중인 결제 내역 
        int deliveryReadyCount = 0; //배송준비중인 결제 내역
        int deliveryCount = 0; //배송중인 결제 내역
        int deliveryCompleteCount = 0; //배송완료인 결제 내역
        int cancelCount = 0; //취소된 상품수량
        int exchangeCount = 0; //교환된 상품 수량
        int returnCount = 0; //반품된 상품 수량

        List<Payment> list = findPaymentByPrincipal(principal);

        //결제내역 확인
        for(Payment payment : list){
            if(payment.getPaymentStatus().equals("결제대기")){
                paymentReadyCount++;
            }
            else if(payment.getPaymentStatus().equals("배송준비")){
                deliveryReadyCount++;
            }
            else if(payment.getPaymentStatus().equals("배송중")){
                deliveryCount++;
            }
            else{
                deliveryCompleteCount++;
            }

            for(PaymentDetail paymentDetail : findPaymentDetailByPaymentId(payment.getPaymentId())){
                String paymentDetailStatus = paymentDetail.getPaymentDetailStatus();
                if(paymentDetailStatus.equals("취소") || paymentDetailStatus.equals("취소진행중")){
                    cancelCount++;
                }
                else if(paymentDetailStatus.equals("교환") || paymentDetailStatus.equals("교환진행중")){
                    exchangeCount++;
                }
                else if(paymentDetailStatus.equals("반품") || paymentDetailStatus.equals("반품진행중")){
                    returnCount++;
                }
            }
        }

        return MyPageViewResponse.builder()
                .paymentReadyCount(paymentReadyCount)
                .deliveryReadyCount(deliveryReadyCount)
                .deliveryCount(deliveryCount)
                .deliveryCompleteCount(deliveryCompleteCount)
                .cancelCount(cancelCount)
                .exchangeCount(exchangeCount)
                .returnCount(returnCount)
                .build();
    }

    public List<Payment> findPaymentByPrincipal(Principal principal){
        return paymentRepository.findByUserId(userService.getUserId(principal))
                .orElseThrow(() -> new IllegalArgumentException("not found payment"));
    }

    public List<PaymentDetail> findPaymentDetailByPaymentId(long paymentId){
        return paymentDetailRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("not found paymentDetail"));
    }
}
