package com.creedboy.springbootboard.dto.response;

import com.creedboy.springbootboard.dto.ArticleDto;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDateTime;

public record ArticleResponse(
    Long id,
    String title,
    String content,
//    Set<String> hashtags,
    String hashtag,
    LocalDateTime createdAt,
    String email,
    String nickname
) {

    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {

        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {

        String nickname = dto.userAccountDto().nickname();

        if (nickname == null || StringUtils.isBlank(nickname)) {

            nickname = dto.userAccountDto().userId();
        }

        return new ArticleResponse(
            dto.id(),
            dto.title(),
            dto.content(),
            dto.hashtag(),
            dto.createdAt(),
            dto.userAccountDto().email(),
            nickname
        );
    }
}
