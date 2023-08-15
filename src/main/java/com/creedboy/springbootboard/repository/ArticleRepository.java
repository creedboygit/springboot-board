package com.creedboy.springbootboard.repository;

import com.creedboy.springbootboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}