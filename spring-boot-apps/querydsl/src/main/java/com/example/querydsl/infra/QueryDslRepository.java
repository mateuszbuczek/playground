package com.example.querydsl.infra;

import com.example.querydsl.domain.QBlogPost;
import com.example.querydsl.domain.QUser;
import com.example.querydsl.domain.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class QueryDslRepository {

    private static final QUser user = QUser.user;
    private static final QBlogPost post = QBlogPost.blogPost;

    @PersistenceContext
    private final EntityManager entityManager;
    private final JPAQueryFactory query;

    public QueryDslRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQueryFactory(entityManager);
    }

    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(
                query.selectFrom(user)
                        .where(user.login.eq("Tom"))
                        .fetchOne()
        );
    }

    public List<User> getUsers() {
        return query.selectFrom(user)
                .orderBy(user.login.asc())
                .fetch();
    }

    List<Tuple> getUserTileCounts() {
        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

        return query.select(post.title, post.id.count().as(count))
                .from(post)
                .groupBy(post.title)
                .orderBy(count.desc())
                .fetch();
    }

    List<User> getUsersWithTitleText(String text) {
        return query.selectFrom(user)
                .innerJoin(user.posts, post)
                .on(post.title.eq(text))
                .fetch();
    }

    List<User> getUsersWithTitleText2(String text) {
        return query.selectFrom(user)
                .where(user.id.in(
                        JPAExpressions.select(post.user.id)
                                .from(post)
                                .where(post.title.eq(text))
                ))
                .fetch();
    }

    public void update() {
        query.update(user)
                .where(user.login.eq("TOM"))
                .set(user.login, "asd")
                .set(user.disabled, true)
                .execute();
    }

    public void delete() {
        query.delete(user)
                .where(user.login.eq("a"))
                .execute();
    }
}
