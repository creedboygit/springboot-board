package com.creedboy.springbootboard.dto.response;

import com.creedboy.springbootboard.dto.ArticleWithCommentsDto;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsResponse(
    Long id,
    String title,
    String hashtag,
    LocalDateTime createdAt,
    String email,
    String nickname,
    String userId,
    Set<ArticleCommentResponse> articleCommentResponse
) {

    public static ArticleWithCommentsResponse of(Long id, String title, String hashtag, LocalDateTime createdAt, String email, String nickname, String userId, Set<ArticleCommentResponse> articleCommentResponse) {

        return new ArticleWithCommentsResponse(id, title, hashtag, createdAt, email, nickname, userId, articleCommentResponse);
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        return ArticleWithCommentsResponse.of(
            dto.id(),
            dto.title(),
            dto.hashtag(),
            dto.createdAt(),
            dto.userAccountDto().email(),
            dto.userAccountDto().nickname(),
            dto.userAccountDto().userId(),
            dto.articleCommentDtos().stream().map(ArticleCommentResponse::from).collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
