package com.example.recode.dto;

import com.example.recode.domain.QnA;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class QnAViewResponse {

    private Long QnAId;
    private long userId;
    private String username;
    private String productName;
    private String QnAQuestionTitle;
    private String QnAAnswer;
    private LocalDateTime QnACreateDate;
    private int QnAViews;

    public QnAViewResponse(QnA qnA, String username, String productName) {
        this.QnAId = qnA.getQnAId();
        this.userId = qnA.getUserId();
        this.username = username;
        this.productName = productName;
        this.QnAQuestionTitle = qnA.getQnAQuestionTitle();
        this.QnAAnswer = qnA.getQnAAnswer();
        this.QnACreateDate = qnA.getQnACreateDate();
        this.QnAViews = qnA.getQnAViews();
    }
}
