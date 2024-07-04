package com.example.recode.repository;

import com.example.recode.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findAllByProductIdInAndProductSold(List<Long> productIds, Integer productSold);
    Optional<List<Product>> findAllByProductIdIn(List<Long> productIds);
    @Query(value = "SELECT * FROM product_tb ORDER BY product_registration_date DESC LIMIT 4", nativeQuery = true)
    Optional<List<Product>> newProduct();
}
