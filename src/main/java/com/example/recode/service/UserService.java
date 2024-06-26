package com.example.recode.service;

import com.example.recode.domain.Notice;
import com.example.recode.domain.User;
import com.example.recode.dto.AdminUserRequest;
import com.example.recode.dto.JoinRequest;
import com.example.recode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long getUserId(Principal principal){ // 로그인 된 사용자 UserId 가져오기
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("not found user"))
                .getUserId();
    }

    public Long save(JoinRequest dto) { // 회원가입 - 회원정보 등록
        return userRepository.save(User.builder()
                .username(dto.getUsername())
                .userPassword(bCryptPasswordEncoder.encode(dto.getUserPassword()))
                .userRealName(dto.getUserRealName())
                .userPhone(dto.getUserPhone1() + "-" + dto.getUserPhone2() + "-" + dto.getUserPhone3())
                .userEmail(dto.getUserEmail())
                .userRole("COSTOMER")
                .build()).getUserId();
    }

    public User findById(Long userId) { // userId로 User 가져오기
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }

    public String getUsername(Long userId){ // userId로 Username 가져오기
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"))
                .getUsername();
    }

    public String idCheck(String username) { // 아이디 중복 체크 - join.js에 ajax 연결
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            return "available"; // 아이디 사용가능
        }

        return "duplicated"; // 아이디 중복
    }

    public String phoneCheck(String userPhone) { // 휴대폰번호 중복 체크 - join.js에 ajax 연결
        User user = userRepository.findByUserPhone(userPhone).orElse(null);
        if(user == null) {
            return "available"; // 휴대폰번호 사용가능
        }

        return "duplicated"; // 휴대폰번호 중복
    }

    public String emailCheck(String userEmail) { // 이메일 중복 체크 - join.js에 ajax 연결
        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        if(user == null) {
            return "available"; // 이메일 사용가능
        }

        return "duplicated"; // 이메일 중복
    }
    @Transactional
    public User updateAdminUser(AdminUserRequest dto) { // 관리자 페이지에서 사용자 정보 수정
        return findById(dto.getUserId()).updateAdminUser(dto);
    }


}
