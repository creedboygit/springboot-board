package com.creedboy.springbootboard.domain.constant;

import lombok.Getter;

//@RequiredArgsConstructor
@Getter
public enum SearchType {

//    TITLE("TITLE", "제목"),
//    CONTENT("CONTENT", "본문"),
//    ID("ID", "유저 ID"),
//    NICKNAME("NICKNAME", "닉네임"),
//    HASHTAG("HASHTAG", "해시태그");

    TITLE("제목"),
    CONTENT("본문"),
    ID("유저 ID"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그"),
    ;

    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
