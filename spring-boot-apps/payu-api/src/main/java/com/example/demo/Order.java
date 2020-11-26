package com.example.demo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(value = "orders")
public class Order implements Persistable<String> {

    @Id
    private String id;

    private String orderId;

    @CreatedDate
    private Instant createdAt;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    public boolean isNew() {
        return isNew;
    }
}
