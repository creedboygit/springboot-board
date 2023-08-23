package com.creedboy.springbootboard.repository.querydsl;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.QArticle;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ArticleQueryRepositoryImpl extends QuerydslRepositorySupport implements ArticleQueryRepository {

    public ArticleQueryRepositoryImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {

        QArticle article = QArticle.article;

        return from(article)
            .distinct()
            .select(article.hashtag)
            .where(article.hashtag.isNotNull())
            .fetch();
    }
}
