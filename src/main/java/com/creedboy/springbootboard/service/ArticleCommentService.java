package com.creedboy.springbootboard.service;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.ArticleComment;
import com.creedboy.springbootboard.domain.UserAccount;
import com.creedboy.springbootboard.dto.ArticleCommentDto;
import com.creedboy.springbootboard.repository.ArticleCommentRepository;
import com.creedboy.springbootboard.repository.ArticleRepository;
import com.creedboy.springbootboard.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class ArticleCommentService {

    private ArticleRepository articleRepository;

    private ArticleCommentRepository articleCommentRepository;

    private UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {

//        return List.of();
        return articleCommentRepository.findByArticle_id(articleId)
            .stream()
            .map(ArticleCommentDto::from)
            .toList();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().id());
            ArticleComment articleComment = dto.toEntity(article, userAccount);

            articleCommentRepository.save(articleComment);
            
        } catch (EntityNotFoundException e) {
            log.debug("======= 댓글 저장 실패. 댓글의 게시글을 찾을 수 없습니다. - dto: {}", dto);
        }
    }

    //    public void updateArticleComment(long articleCommentId, ArticleUpdateCommentDto dto) {
    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            if (dto.content() != null) {
                articleComment.setContent(dto.content());
            }
        } catch (EntityNotFoundException e) {
            log.debug("======= 댓글 업데이트 실패. 댓글을 찾을 수 없습니다. - dto: {}", dto);
        }
    }

    public void deleteArticleComment(long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }
}
