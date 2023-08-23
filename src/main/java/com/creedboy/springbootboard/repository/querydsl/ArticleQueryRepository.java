package com.creedboy.springbootboard.repository.querydsl;

import java.util.List;

public interface ArticleQueryRepository {

    List<String> findAllDistinctHashtags();
}
