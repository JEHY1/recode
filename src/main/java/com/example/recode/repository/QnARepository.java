package com.example.recode.repository;

import com.example.recode.domain.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface QnARepository extends JpaRepository<QnA, Long> {

    Optional<List<QnA>> findByProductIdOrderByQnACreateDateDesc(long productId);
    Optional<Page<QnA>> findByProductIdIn(List<Long> productIds, Pageable pageable); // productId List �� ����¡ ó���� Page<QnA> ��������
    Optional<Page<QnA>> findByQnAQuestionTitleContaining(String searchKeyword, Pageable pageable); // qnAQuestionTitle �� �˻��ؼ� ����¡ ó���� Page<QnA>
    Optional<Page<QnA>> findByUserIdIn(List<Long> userIds, Pageable pageable); // userId List �� ����¡ ó���� Page<QnA> ��������

}




