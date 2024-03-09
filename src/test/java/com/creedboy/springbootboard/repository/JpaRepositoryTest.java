package com.creedboy.springbootboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.creedboy.springbootboard.config.JpaConfig;
import com.creedboy.springbootboard.config.P6SpySqlFormatter;
import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.UserAccount;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@ImportAutoConfiguration({DataSourceDecoratorAutoConfiguration.class, P6SpySqlFormatter.class})
@ActiveProfiles("local")
//@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class JpaRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

//    @Disabled("테스트 제외")
//    @DisplayName("findById 기본 테스트 + Spring Data JPA 동작 테스트")
//    @Test
//    void findById() {
//
//        UserAccount userAccount = UserAccount.of("creedboy", "a123123", "creed@creed.com", "nick", "memos");
//
//        Article article = Article.of(userAccount, "타이틀", "내용", "해시태그");
////        log.debug("# article: {}", article.toString());
//        articleRepository.saveAndFlush(article);
//
////        log.debug("# article: {}", article);
//        log.debug("# article: {}", article);
//
//        Article foundArticle = articleRepository.findById(3L).get();
//
//        assertThat(foundArticle).isEqualTo(article);
//    }

    @DisplayName("select 테스트")
    @Test
    void select_test() {

        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
            .isNotNull()
            .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {

        // Given
        long previousCount = articleRepository.count();

//        UserAccount ua = UserAccount.of("creedboy", "a123123", "creed@creed.com", "nick", "memos");
        UserAccount userAccount = userAccountRepository.saveAndFlush(UserAccount.of("creedboy", "a123123", "creed@creed.com", "nick", "memos"));

        Article article = Article.of(userAccount, "new article", "new content", "#spring");

        // When
        Article savedArticle = articleRepository.save(article);
//        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

//    @DisplayName("builder insert 테스트")
//    @Test
//    void save_test() {
//
//        Article article = Article.builder()
//            .content("content1")
//            .title("title1")
////            .createdAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
////            .modifiedAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
////            .createdBy("creed")
////            .modifiedBy("creed")
//            .build();
//
//        articleRepository.save(article);
//    }

    @DisplayName("update 테스트")
    @Test
    public void givenTestData_whenUpdating_thenWorksFine() {

        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#creedboyis";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {

        // Given
        Article article = articleRepository.findById(1L).orElseThrow();

        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }
}
