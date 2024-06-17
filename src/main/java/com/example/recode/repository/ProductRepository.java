package com.example.recode.repository;

import com.example.recode.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findAllByProductIdInAndProductSold(List<Long> productIds, Integer productSold);
}
