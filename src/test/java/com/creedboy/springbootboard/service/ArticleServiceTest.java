package com.creedboy.springbootboard.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.constant.SearchType;
import com.creedboy.springbootboard.dto.ArticleDto;
import com.creedboy.springbootboard.repository.ArticleRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색 시 리스트 반환")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {

        // Given

        // When
        Page<ArticleDto> articles = articleService.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그

        // Then
        Assertions.assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회 시 게시글 반환")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {

        // Given

        // When
        ArticleDto article = articleService.searchArticle(1L);

        // Then
        Assertions.assertThat(article).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면 게시글을 생성")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSaveArticle() {

        // Given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(),
            "creed",
            "titleee",
            "contenttt",
            "hashtaggg");

        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        articleService.saveArticle(dto);

        // Then
        BDDMockito.then(articleRepository).should().save(any(Article.class));
    }
}