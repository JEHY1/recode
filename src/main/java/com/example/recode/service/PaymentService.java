package com.example.recode.service;

import com.example.recode.domain.Payment;
import com.example.recode.domain.PaymentDetail;
import com.example.recode.domain.Product;
import com.example.recode.dto.PaymentRequest;
import com.example.recode.repository.CartRepository;
import com.example.recode.repository.PaymentDetailRepository;
import com.example.recode.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    public Payment payment(PaymentRequest request, Principal principal){


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

        request.getProductIds().forEach(productId -> {

            Product product = productService.findProductByProductId(productId);
            paymentDetailRepository.save(PaymentDetail.builder()
                    .paymentId(payment.getPaymentId())
                    .productId(productId)
                    .paymentDetailStatus("결제대기중")
                    .paymentDetailPrice(product.getProductDiscountPrice() == null ? product.getProductRegularPrice() : product.getProductDiscountPrice())
                    .build()
            );

            product.updateSold(1);
        });

        request.getCartIds().forEach(cartId -> cartRepository.deleteById(cartId));

        return payment;
    }
}
