package com.creedboy.springbootboard.dto;

public record ArticleUpdateCommentDto(String content) {

    public static ArticleUpdateCommentDto of(String content) {

        return new ArticleUpdateCommentDto(content);
    }
}
