package com.creedboy.springbootboard.service;

import com.creedboy.springbootboard.dto.ArticleCommentDto;
import com.creedboy.springbootboard.dto.ArticleUpdateCommentDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {

        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }

    public void updateArticleComment(long articleCommentId, ArticleUpdateCommentDto dto) {
    }

    public void deleteArticleComment(long articleCommentId) {
    }
}
