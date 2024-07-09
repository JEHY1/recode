package com.example.recode.repository;

import com.example.recode.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

//    Page<Notice> findByNoticeId (Long noticeId, Pageable pageable);
    
    Page<Notice> findByNoticeTitleContaining(String searchKeyword, Pageable pageable); // 제목으로 검색해서 페이징 처리한 Page<Notice>
}
