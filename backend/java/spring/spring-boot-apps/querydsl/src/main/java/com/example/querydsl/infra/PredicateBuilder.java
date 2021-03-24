package com.example.querydsl.infra;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.function.Supplier;

public class PredicateBuilder {
    private final BooleanBuilder booleanBuilder = new BooleanBuilder();

    public PredicateBuilder and(BooleanExpression booleanExpression) {
        if (booleanExpression != null) {
            booleanBuilder.and(booleanExpression);
        }
        return this;
    }

    public PredicateBuilder andIf(Supplier<BooleanExpression> supplier, boolean condition) {
        if (condition && supplier != null && supplier.get() != null) {
            booleanBuilder.and(supplier.get());
        }
        return this;
    }

    public Predicate build() {
        Predicate predicate = booleanBuilder.getValue();
        return predicate != null ? predicate : Expressions.TRUE.isTrue();
    }
}
