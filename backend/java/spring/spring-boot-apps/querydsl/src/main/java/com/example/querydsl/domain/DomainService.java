package com.example.querydsl.domain;

import com.example.querydsl.infra.PredicateBuilder;
import com.example.querydsl.infra.QueryDslRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DomainService {

    private final QueryDslRepository repository;

//    @Transactional required for update/delete
    public void call() {
        repository.delete();
        return;
    }

    public void call1() {
        List<String> logins = Arrays.asList("A", "B");

        Predicate predicate = new PredicateBuilder()
                .and(QUser.user.id.eq(1L))
                .andIf(() -> QUser.user.login.in(logins), !CollectionUtils.isEmpty(logins))
                .build();

        List<User> byPredicate = repository.findByPredicate(predicate);
        return;
    }
}
