package com.example.querydsl.infra;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class PredicateBuilder {
    private final BooleanBuilder booleanBuilder = new BooleanBuilder();

    public PredicateBuilder and(BooleanExpression booleanExpression) {
        if (booleanExpression != null) {
            booleanBuilder.and(booleanExpression);
        }
        return this;
    }

    public PredicateBuilder andIf(BooleanExpression booleanExpression, boolean condition) {
        if (booleanExpression != null && condition) {
            booleanBuilder.and(booleanExpression);
        }
        return this;
    }

    public Predicate build() {
        Predicate predicate = booleanBuilder.getValue();
        return predicate != null ? predicate : Expressions.TRUE.isTrue();
    }
}
