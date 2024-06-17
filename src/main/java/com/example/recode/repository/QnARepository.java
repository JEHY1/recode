package com.example.recode.repository;

import com.example.recode.domain.QnA;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface QnARepository extends JpaRepository<QnA, Long> {

    Optional<List<QnA>> findByProductId(long productId);
}




