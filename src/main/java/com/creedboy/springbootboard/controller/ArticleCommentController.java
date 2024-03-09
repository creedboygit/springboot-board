package com.creedboy.springbootboard.controller;

import com.creedboy.springbootboard.dto.UserAccountDto;
import com.creedboy.springbootboard.dto.request.ArticleCommentRequest;
import com.creedboy.springbootboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
@Slf4j
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {

        // TODO : 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
            "creed", "1234", "creed@creed.com", "nickname", "memo"
        )));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {

        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
