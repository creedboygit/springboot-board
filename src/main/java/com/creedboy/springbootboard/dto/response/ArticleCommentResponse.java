package com.creedboy.springbootboard.dto.response;

import com.creedboy.springbootboard.dto.ArticleCommentDto;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDateTime;

public record ArticleCommentResponse(
    Long id,
    String content,
    LocalDateTime createdAt,
    String email,
    String nickname,
    String userId
) {

    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {

        return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId);
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || StringUtils.isBlank(nickname)) {
            nickname = dto.userAccountDto().userId();
        }

        return ArticleCommentResponse.of(
            dto.id(),
            dto.content(),
            dto.createdAt(),
            dto.userAccountDto().email(),
            dto.userAccountDto().nickname(),
            dto.userAccountDto().userId()
        );
    }
}
