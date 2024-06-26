package com.example.recode.domain;

import com.example.recode.dto.AdminUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_real_name", nullable = false)
    private String userRealName;

    @Column(name = "user_phone", nullable = false, unique = true)
    private String userPhone;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @CreatedDate
    @Column(name = "user_submit_date", nullable = false)
    private LocalDateTime userSubmitDate;

    @Column(name = "user_role", nullable = false)
    private String userRole;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public User(Long userId, String username, String userPassword, String userRealName, String userPhone, String userEmail, LocalDateTime userSubmitDate, String userRole) {
        this.userId = userId;
        this.username = username;
        this.userPassword = userPassword;
        this.userRealName = userRealName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userSubmitDate = userSubmitDate;
        this.userRole = userRole;
    }

    public User updateAdminUser(AdminUserRequest dto) { // 관리자 페이지에서 회원정보 수정
        this.userPassword = dto.getUserPassword();
        this.userRealName = dto.getUserRealName();
        this.userPhone = dto.getUserPhone();
        this.userEmail = dto.getUserEmail();
        this.userRole = dto.getUserRole();
        return this;
    }
}
