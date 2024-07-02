package com.example.recode.repository;

import com.example.recode.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUserPhone(String userPhone);
    Optional<User> findByUserEmail(String userEmail);

    Optional<List<User>> findByUsernameContaining(String username);
}
