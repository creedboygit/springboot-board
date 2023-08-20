package com.creedboy.springbootboard.dto;

import com.creedboy.springbootboard.domain.Article;
import java.time.LocalDateTime;

public record ArticleDto(
    Long id,
    UserAccountDto userAccountDto,
    String title,
    String content,
    String hashtag,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime modifiedAt,
    String modifiedBy
) {

//    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
//
//        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
//    }


    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {

        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {

        return new ArticleDto(
            entity.getId(),
            UserAccountDto.from(entity.getUserAccount()),
            entity.getTitle(),
            entity.getContent(),
            entity.getHashtag(),
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getModifiedAt(),
            entity.getModifiedBy()
        );
    }

    public Article toEntity() {

        return Article.of(
            userAccountDto.toEntity(),
            title,
            content,
            hashtag
        );
    }
}
