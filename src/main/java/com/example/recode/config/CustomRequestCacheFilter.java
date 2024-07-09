package com.example.recode.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomRequestCacheFilter extends OncePerRequestFilter {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 로그인 페이지로의 요청은 저장하지 않음
        if (!requestURI.equals("/login") && !requestURI.startsWith("/login/") && !requestURI.equals("/checkLogin") && !requestURI.startsWith("/css") && !requestURI.startsWith("/js") && !requestURI.startsWith("/images")) {
            requestCache.saveRequest(request, response);
        }

        filterChain.doFilter(request, response);
    }
}
