package com.example.recode.service;

import com.example.recode.domain.QnA;
import com.example.recode.dto.ProductDetailQnAViewResponse;
import com.example.recode.repository.QnARepository;
import com.example.recode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnARepository;
    private final UserRepository userRepository;

    public List<QnA> findQnAByProductId(long productId){
        return qnARepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found QnA"));
    }

    public int QnATotalSize(long productId){
        return findQnAByProductId(productId).size();
    }

    public List<List<ProductDetailQnAViewResponse>> getQnAByProductIdPaging(long productId, int size){
        List<QnA> QnAs= findQnAByProductId(productId);
        int index = 1;
        List<List<ProductDetailQnAViewResponse>> pagingQnAs = new ArrayList<>();
        List<ProductDetailQnAViewResponse> QnAList = new ArrayList<>();
        for(QnA QnA : QnAs){
            QnAList.add(ProductDetailQnAViewResponse.builder()
                    .viewIndex(index++)
                    .qnAId(QnA.getQnAId())
                    .qnAQuestionTitle(QnA.getQnAQuestionTitle())
                    .qnAQuestionContent(QnA.getQnAQuestionContent())
                    .username(userRepository.findById(QnA.getUserId()).orElseThrow(() -> new IllegalArgumentException("not found user")).getUsername())
                    .qnAAnswer(QnA.getQnAAnswer())
                    .qnAAnswerDate(QnA.getQnAAnswerDate())
                    .qnACreateDate(QnA.getQnACreateDate())
                    .qnAViews(QnA.getQnAViews())
                    .build());
            if((index - 1) % size == 0 && index != 1){
                pagingQnAs.add(QnAList);
                QnAList = new ArrayList<>();
            }
        }
        if(!QnAList.isEmpty()){
            pagingQnAs.add(QnAList);
        }

        return pagingQnAs;
    }

}
