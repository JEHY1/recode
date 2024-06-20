package com.example.recode.service;

import com.example.recode.domain.Notice;
import com.example.recode.domain.User;
import com.example.recode.dto.JoinRequest;
import com.example.recode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long getUserId(Principal principal){
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("not found user"))
                .getUserId();
    }

    public Long save(JoinRequest dto) {
        return userRepository.save(User.builder()
                .username(dto.getUsername())
                .userPassword(bCryptPasswordEncoder.encode(dto.getUserPassword()))
                .userRealName(dto.getUserRealName())
                .userPhone(dto.getUserPhone1() + "-" + dto.getUserPhone2() + "-" + dto.getUserPhone3())
                .userEmail(dto.getUserEmail())
                .userRole("COSTOMER")
                .build()).getUserId();
    }
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }

    public String getUsername(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"))
                .getUsername();
    }

}
