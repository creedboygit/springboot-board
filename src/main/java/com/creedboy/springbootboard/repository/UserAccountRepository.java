package com.creedboy.springbootboard.repository;

import com.creedboy.springbootboard.domain.QUserAccount;
import com.creedboy.springbootboard.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserAccountRepository extends
    JpaRepository<UserAccount, Long>,
    QuerydslPredicateExecutor<UserAccount>,
    QuerydslBinderCustomizer<QUserAccount> {

    @Override
    default void customize(QuerydslBindings bindings, QUserAccount root) {

//        bindings.excludeUnlistedProperties(true);
//        bindings.including(root.title, root.hashtag, root.content, root.createdAt, root.createdBy);
//
//        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
//        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
//        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
//        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
//        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
    }
}