package com.example.recode.service;

import com.example.recode.domain.Notice;
import com.example.recode.domain.QnA;
import com.example.recode.dto.NoticeViewResponse;
import com.example.recode.dto.ProductDetailQnAViewResponse;
import com.example.recode.dto.QnAViewResponse;
import com.example.recode.dto.QnaAnswerRequest;
import com.example.recode.dto.QnaSubmitRequest;
import com.example.recode.repository.QnARepository;
import com.example.recode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnARepository;
    private final UserService userService;
    private final ProductService productService;

    public List<QnA> findQnAByProductId(long productId){
        return qnARepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found QnA"));
    }

    //상품 qna 총 개수
    public int QnATotalSize(long productId){
        return findQnAByProductId(productId).size();
    }

    //상품의 qna 를 size 개수로 나누어 모든 qna 를 리턴
    public List<List<ProductDetailQnAViewResponse>> getQnAByProductIdPaging(long productId, int size){
        //상품의 모든 qna
        List<QnA> QnAs= findQnAByProductId(productId);
        int index = 1;
        //반환할 리스트
        List<List<ProductDetailQnAViewResponse>> pagingQnAs = new ArrayList<>();
        //반환할 리스트 안에 들어갈 리스트
        List<ProductDetailQnAViewResponse> QnAList = new ArrayList<>();
        
        for(QnA QnA : QnAs){
            //각각 qna 를 QnAList 에 담기
            QnAList.add(ProductDetailQnAViewResponse.builder()
                    .viewIndex(index++)
                    .qnAId(QnA.getQnAId())
                    .qnAQuestionTitle(QnA.getQnAQuestionTitle())
                    .qnAQuestionContent(QnA.getQnAQuestionContent())
                    .username(userService.getUsername(QnA.getUserId()))
                    .qnAAnswer(QnA.getQnAAnswer())
                    .qnAAnswerDate(QnA.getQnAAnswerDate())
                    .qnACreateDate(QnA.getQnACreateDate())
                    .qnAViews(QnA.getQnAViews())
                    .build());
            //지정한 수량만큼 담았을 경우 pagingQnAs 에 QnAList 담기, QnAList 에 새로운 리스트 할당
            if((index - 1) % size == 0 && index != 1){
                pagingQnAs.add(QnAList);
                QnAList = new ArrayList<>();
            }
        }

        //마지막에 size 만큼 채워지지 않은 QnAList 추가
        if(!QnAList.isEmpty()){
            pagingQnAs.add(QnAList);
        }

        return pagingQnAs;
    }

    public QnA findById(Long qnAId) { // QnAId로 QnA 가져오기
        return qnARepository.findById(qnAId)
                .orElseThrow(() -> new IllegalArgumentException("not found QnA"));
    }

    @Transactional
    public QnA saveAnswer(QnaAnswerRequest dto) { // 상품문의 답변 등록&수정
        return findById(dto.getQnAId()).saveAnswer(dto);
    }

    @Transactional
    public QnA deleteAnswer(Long qnAId) { // 상품문의 답변 삭제
        return findById(qnAId).deleteAnswer();
    }

    public List<QnA> findAll() { // List<QnA> 가져오기
        return qnARepository.findAll();
    }

    public void deleteById(Long qnAId) { // QnAId로 QnA 삭제
        qnARepository.deleteById(qnAId);
    }

    public List<QnAViewResponse> getAllQnAInfo() { // List<QnAViewResponse> 가져오기

        List<QnAViewResponse> list = new ArrayList<>();

        findAll().forEach(qnA -> list.add(new QnAViewResponse(qnA, userService.getUsername(qnA.getUserId()), productService.findProductByProductId(qnA.getProductId()).getProductName())));

        return list;
    }

    public QnA saveQnA(QnaSubmitRequest request, Principal principal){

        return qnARepository.save(QnA.builder()
                .userId(userService.getUserId(principal))
                .productId(request.getProductId())
                .qnAQuestionTitle(request.getQnaTitle())
                .qnAQuestionContent(request.getQnaContent())
                .qnAViews(0)
                .build()
        );
    }

    public Page<QnAViewResponse> qnAViewList(Pageable pageable) { // 페이징 처리한 Page<QnAViewResponse> 가져옴
        Page<QnA> qnAList = qnARepository.findAll(pageable); // 페이징 처리한 Page<QnA>
        Page<QnAViewResponse> qnAViewList = qnAList.map(qnA -> new QnAViewResponse(qnA, userService.getUsername(qnA.getUserId()), productService.findProductByProductId(qnA.getProductId()).getProductName()));
        return qnAViewList;
    }

    public void deleteByIds(List<Long> qnAIds) { // qnAIds 리스트로 QnA 삭제
        for (Long qnAId : qnAIds) {
            qnARepository.deleteById(qnAId);
        }
    }

    public Page<QnA> getAllQnAs(Pageable pageable) {
        return qnARepository.findAll(pageable);
    }

}
