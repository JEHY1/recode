package com.example.recode.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Notice {

    @Id
    @Column(name = "notice_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noticeId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    @CreatedDate
    @Column(name = "notice_create_date", nullable = false)
    private LocalDateTime noticeCreateDate;

    @Column(name = "notice_views", nullable = false)
    private int noticeViews;

    @Builder
    public Notice(long noticeId, long userId, String noticeTitle, String noticeContent, LocalDateTime noticeCreateDate, int noticeViews) {
        this.noticeId = noticeId;
        this.userId = userId;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeCreateDate = noticeCreateDate;
        this.noticeViews = noticeViews;
    }
}
