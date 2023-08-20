package com.creedboy.springbootboard.service;

import com.creedboy.springbootboard.dto.ArticleCommentDto;
import com.creedboy.springbootboard.repository.ArticleCommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {

//        return List.of();
        return articleCommentRepository.findByArticle_id(articleId)
            .stream()
            .map(ArticleCommentDto::from)
            .toList();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }

    //    public void updateArticleComment(long articleCommentId, ArticleUpdateCommentDto dto) {
    public void updateArticleComment(ArticleCommentDto dto) {
    }

    public void deleteArticleComment(long articleCommentId) {
    }
}
