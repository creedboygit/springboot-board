package com.creedboy.springbootboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.ArticleComment;
import com.creedboy.springbootboard.domain.UserAccount;
import com.creedboy.springbootboard.dto.ArticleCommentDto;
import com.creedboy.springbootboard.dto.ArticleUpdateCommentDto;
import com.creedboy.springbootboard.repository.ArticleCommentRepository;
import com.creedboy.springbootboard.repository.ArticleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService articleCommentService;

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Mock
    private ArticleRepository articleRepository;


    @DisplayName("게시글 ID로 댓글 리스트 조회하면 해당 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {

        Long articleId = 1L;

        UserAccount userAccount = UserAccount.of("creedboy", "a123123", "creed@creed.com", "nick", "memos");

        Article article = Article.of(userAccount, "title", "content", "hashtag");

        // Given
        BDDMockito.given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // When
        List<ArticleCommentDto> articleCommentList = articleCommentService.searchArticleComment(articleId);

        // Then
        assertThat(articleCommentList).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 정보를 입력하면 댓글을 생성")
    @Test
    void givenArticleCommentInfo_whenSavingArticle_thenSaveArticle() {

        // Given
        ArticleCommentDto dto = ArticleCommentDto.of(LocalDateTime.now(),
            "titleee",
            "contenttt"
        );

        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        articleCommentService.saveArticleComment(dto);

        // Then
        BDDMockito.then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 ID와 수정 정보를 입력하면 댓글을 수정")
    @Test
    void givenArticleCommentIdAndModifiedInfo_whenUpdatingArticleComment_thenUpdateArticleComment() {

        // Given
        ArticleUpdateCommentDto dto = ArticleUpdateCommentDto.of("contenttt");

        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        articleCommentService.updateArticleComment(1L, dto);

        // Then
        BDDMockito.then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 ID를 입력하면 댓글 삭제")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeleteArticleComment() {

        // Given
        willDoNothing().given(articleCommentRepository).delete(any(ArticleComment.class));

        // When
        articleCommentService.deleteArticleComment(1L);

        // Then
        BDDMockito.then(articleCommentRepository).should().delete(any(ArticleComment.class));
    }
}