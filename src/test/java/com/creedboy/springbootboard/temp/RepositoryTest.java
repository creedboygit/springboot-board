package com.creedboy.springbootboard.temp;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.repository.ArticleCommentRepository;
import com.creedboy.springbootboard.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Disabled("테스트 제외")
//@DataJpaTest
@SpringBootTest
@Transactional
public class RepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Test
    void save() {

        articleRepository.save(
            Article.builder()
                .content("content1")
                .title("title1")
//                .createdAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
//                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
//                .createdBy("creed")
//                .modifiedBy("creed")
                .build()
        );

        articleRepository.save(
            Article.builder()
                .content("content2")
                .title("title2")
//                .createdAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
//                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
//                .createdBy("creed")
//                .modifiedBy("creed")
                .build()
        );
    }

}
