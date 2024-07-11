package com.example.recode.repository;

import com.example.recode.domain.Product;
import com.example.recode.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findAllByProductIdInAndProductSold(List<Long> productIds, Integer productSold);
    Optional<List<Product>> findAllByProductIdIn(List<Long> productIds);
    @Query(value = "SELECT * FROM product_tb WHERE product_sold = 0 ORDER BY product_registration_date DESC LIMIT 4", nativeQuery = true)
    Optional<List<Product>> newProduct();

    Optional<Page<Product>> findAllByProductCategoryAndProductSold(String productCategory, int productSold, Pageable pageable);
    Optional<Page<Product>> findAllByProductNameContainingAndProductSold(String searchText, int productSold, Pageable pageable);
    Optional<List<Product>> findByProductNameContaining(String productName);
}
