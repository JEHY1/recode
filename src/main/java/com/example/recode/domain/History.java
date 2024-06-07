package com.example.recode.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class History {

    @Id
    @Column(name = "history_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Builder
    public History(long historyId, long productId, long userId) {
        this.historyId = historyId;
        this.productId = productId;
        this.userId = userId;
    }
}
