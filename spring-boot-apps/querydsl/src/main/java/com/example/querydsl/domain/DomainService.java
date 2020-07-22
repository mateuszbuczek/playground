package com.example.querydsl.domain;

import com.example.querydsl.infra.QueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class DomainService {

    private final QueryDslRepository repository;

//    @Transactional required for update/delete
    public void call() {
        repository.delete();
        return;
    }
}
