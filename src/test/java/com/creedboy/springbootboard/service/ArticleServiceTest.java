package com.creedboy.springbootboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.UserAccount;
import com.creedboy.springbootboard.domain.constant.SearchType;
import com.creedboy.springbootboard.dto.ArticleDto;
import com.creedboy.springbootboard.dto.ArticleWithCommentsDto;
import com.creedboy.springbootboard.dto.UserAccountDto;
import com.creedboy.springbootboard.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환")
    @Test
    void givenNoSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {

//        // Given
//
//        // When
//        Page<ArticleDto> articles = articleService.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그
//
//        // Then
//        Assertions.assertThat(articles).isNotNull();

        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = articleService.searchArticles(null, null, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {

        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitle(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = articleService.searchArticles(searchType, searchKeyword, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitle(searchKeyword, pageable);
    }

    @DisplayName("게시글을 조회 시 게시글 반환")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {

        // Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // When
//        ArticleDto article = articleService.searchArticle(1L);
        ArticleWithCommentsDto dto = articleService.getArticle(articleId);

        // Then
//        assertThat(article).isNotNull();
        Assertions.assertThat(dto)
            .hasFieldOrPropertyWithValue("title", article.getTitle())
            .hasFieldOrPropertyWithValue("content", article.getContent())
            .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());

        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("없는 게시글을 조회하면 예외를 반환")
    @Test
    void givenNonExistentArticleId_whenSearchingArticle_thenThrowsException() {

        // Given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> articleService.getArticle(articleId));

        // Then
        Assertions.assertThat(t)
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("게시글이 없습니다 - articleId: " + articleId);

        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글 생성")
    @Test
    public void givenArticleInfo_whenSavingArticle_thenSavesArticle() {

        // Given
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "새 해시태그");
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());

        // When
        articleService.saveArticle(dto);

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 수정 정보를 입력하면, 게시글 수정")
    @Test
    public void givenModifiedArticleInfo_whenUpdatingArticle_thenUpdatesArticle() {

        // Given
        Article article = createArticle();
        ArticleDto dto = createArticleDto();
        given(articleRepository.getReferenceById(dto.id())).willReturn(article);

        // When
        articleService.updateArticle(dto);

        // Then
        Assertions.assertThat(article)
            .hasFieldOrPropertyWithValue("title", dto.title())
            .hasFieldOrPropertyWithValue("content", dto.content())
            .hasFieldOrPropertyWithValue("hashtag", dto.hashtag());

        then(articleRepository).should().findById(dto.id());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무것도 하지 않음")
    @Test
    public void givenNonExistentArticleInfo_whenUpdatingArticle_thenLogsWarningAdnDoesNothing() {

        // Given
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "새 해시태그");
        given(articleRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        articleService.updateArticle(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName(" 게시글의 아이디를 입력하면, 게시글 삭제")
    @Test
    public void givenArticleId_whenDeletingArticle_thenDeletesArticle() {

        // Given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).deleteById(articleId);

        // When
        articleService.deleteArticle(1L);

        // Then
        then(articleRepository).should().deleteById(articleId);
    }


    private Article createArticle() {

        return Article.of(
            createUserAccount(),
            "title",
            "content",
            "hashtag"
        );
    }

    private UserAccount createUserAccount() {

        return UserAccount.of(1L,
            "userId",
            "passwords",
            "creed@creed.com",
            "nick",
            "memo");
    }

    private ArticleDto createArticleDto() {

        return createArticleDto("title", "content", "hashtag");

//        return ArticleDto.of(1L,
//            createUserAccountDto(),
//            "title",
//            "content",
//            "hashtag",
//            LocalDateTime.now(),
//            "creed",
//            LocalDateTime.now(),
//            "creed"
//        );
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {

        return ArticleDto.of(1L,
            createUserAccountDto(),
            title,
            content,
            hashtag,
            LocalDateTime.now(),
            "creed",
            LocalDateTime.now(),
            "creed");
    }

    private UserAccountDto createUserAccountDto() {

        return UserAccountDto.of(
            1L,
            "creed",
            "password",
            "creed@creed.com",
            "nickname",
            "memo"
        );
    }
}