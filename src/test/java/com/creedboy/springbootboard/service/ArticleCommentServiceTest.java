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
import com.creedboy.springbootboard.dto.UserAccountDto;
import com.creedboy.springbootboard.repository.ArticleCommentRepository;
import com.creedboy.springbootboard.repository.ArticleRepository;
import com.creedboy.springbootboard.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService articleCommentService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Mock
    private ArticleRepository articleRepository;


    @DisplayName("게시글 ID로 댓글 리스트 조회하면 해당 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {

        // given
        Long articleId = 1L;

        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_id(articleId)).willReturn(List.of(expected));

        // when
        List<ArticleCommentDto> actual = articleCommentService.searchArticleComment(articleId);

        // then
        assertThat(actual)
            .hasSize(1)
            .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_id(articleId);
    }

    @DisplayName("댓글 정보를 입력하면 댓글을 생성")
    @Test
    void givenArticleCommentInfo_whenSavingArticle_thenSaveArticleComment() {

        // Given
//        ArticleCommentDto dto = ArticleCommentDto.of(1L, UserAccountDto.of, "content");
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        articleCommentService.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안함")
    @Test
    void givenNonExistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {

        // given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        // when
        articleCommentService.saveArticleComment(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 ID와 수정 정보를 입력하면 댓글을 수정")
    @Test
    void givenArticleCommentIdAndModifiedInfo_whenUpdatingArticleComment_thenUpdateArticleComment() {

//        // Given
//        ArticleUpdateCommentDto dto = ArticleUpdateCommentDto.of("contenttt");
//
//        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
//
//        // When
//        articleCommentService.updateArticleComment(1L, dto);
//
//        // Then
//        then(articleCommentRepository).should().save(any(ArticleComment.class));

        // given
        String oldContent = "content";
        String updatedContent = "댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto dto = createArticleCommentDto(updatedContent);
        given(articleCommentRepository.getReferenceById(dto.id())).willReturn(articleComment);

        // when
        articleCommentService.updateArticleComment(dto);

        // then
        assertThat(articleComment.getContent())
            .isNotEqualTo(oldContent)
            .isEqualTo(updatedContent);
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 댓글 정보를 수정하려고 할 때, 경고 로그를 찍고 아무 것도 안함")
    @Test
    void givenNonExistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() {

        // given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleCommentRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // when
        articleCommentService.updateArticleComment(dto);

        // then
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("댓글 ID를 입력하면 댓글 삭제")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeleteArticleComment() {

        // given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);

        // when
        articleCommentService.deleteArticleComment(articleCommentId);

        // then
        then(articleCommentRepository).should().deleteById(articleCommentId);
    }

    private ArticleComment createArticleComment(String content) {

        return ArticleComment.of(
            Article.of(createUserAccount(), "title", "content", "hashtag"),
            createUserAccount(),
            content
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
            "creed",
            "password",
            "creed@creed.com",
            "creednick",
            "memos"
        );
    }

    private UserAccountDto createUserAccountDto() {

        return UserAccountDto.of(
            "creed",
            "creed",
            "creed@creed.com",
            "creed",
            "memo",
            LocalDateTime.now(),
            "creed",
            LocalDateTime.now(),
            "creed"
        );
    }

    private ArticleCommentDto createArticleCommentDto(String content) {

        return ArticleCommentDto.of(
            1L,
            1L,
            createUserAccountDto(),
            content,
            LocalDateTime.now(),
            "creed",
            LocalDateTime.now(),
            "creed");
    }

    private Article createArticle() {

        return Article.of(
            createUserAccount(),
            "title",
            "content",
            "hashtag"
        );
    }
}