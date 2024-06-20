package com.example.recode.domain;

import com.example.recode.dto.QnaAnswerRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "qna_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id", updatable = false)
    private Long QnAId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "qna_question_title", nullable = false)
    private String QnAQuestionTitle;

    @Column(name = "qna_question_content", nullable = false)
    private String QnAQuestionContent;

    @Column(name = "qna_answer")
    private String QnAAnswer;

    @CreatedDate
    @Column(name = "qna_create_date", nullable = false, updatable = false)
    private LocalDateTime QnACreateDate;

    @Column(name = "qna_views", nullable = false)
    private int QnAViews;

    @Column(name = "qna_answer_date")
    private LocalDateTime QnAAnswerDate;

    @Builder
    public QnA(Long qnAId, long userId, long productId, String qnAQuestionTitle, String qnAQuestionContent, String qnAAnswer, LocalDateTime qnACreateDate, int qnAViews, LocalDateTime qnAAnswerDate) {
        this.QnAId = qnAId;
        this.userId = userId;
        this.productId = productId;
        this.QnAQuestionTitle = qnAQuestionTitle;
        this.QnAQuestionContent = qnAQuestionContent;
        this.QnAAnswer = qnAAnswer;
        this.QnACreateDate = qnACreateDate;
        this.QnAViews = qnAViews;
        this.QnAAnswerDate = qnAAnswerDate;
    }

    public QnA saveAnswer(QnaAnswerRequest dto) {
        this.QnAAnswer = dto.getQnAAnswer();
        this.QnAAnswerDate = this.QnAAnswerDate == null ? LocalDateTime.now() : this.QnAAnswerDate;
        return this;
    }

    public QnA deleteAnswer() {
        this.QnAAnswer = null;
        this.QnAAnswerDate = null;
        return this;
    }
}
